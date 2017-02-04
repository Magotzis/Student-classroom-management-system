package com.magotzis.mybatis.page;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.RowBounds;

import com.magotzis.mybatis.autobuild.NameConverter;

/**
 * 通过拦截<code>StatementHandler</code>的<code>prepare</code>方法，重写sql语句实现物理分页。
 * 签名里要拦截的类型只能是接口。
 * 
 * @author jeff he
 * 
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class PageInterceptor implements Interceptor {
	private static final Log logger = LogFactory.getLog(PageInterceptor.class);
	private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
	private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
	private static String dialect = "mysql"; // 数据库类型(默认为mysql)
	private static String pageSqlId = "^.*Page$"; // 需要拦截的ID(正则匹配)

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		// System.out.println("page");
		StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
		MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY,
				DEFAULT_OBJECT_WRAPPER_FACTORY);
		// 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环可以分离出最原始的的目标类)
		while (metaStatementHandler.hasGetter("h")) {
			Object object = metaStatementHandler.getValue("h");
			metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
		}
		// 分离最后一个代理对象的目标类
		while (metaStatementHandler.hasGetter("target")) {
			Object object = metaStatementHandler.getValue("target");
			metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
		}
		MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
		// 只重写需要分页的sql语句。通过MappedStatement的ID匹配，默认重写以Page结尾的MappedStatement的sql
		if (mappedStatement.getId().matches(pageSqlId)) {
			BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
			Object parameterObject = boundSql.getParameterObject();
			if (parameterObject == null) {
				logger.warn("parameterObject is null!");
			}
			String sql = boundSql.getSql();
			// 重写sql
			String orderSql = buildOrderSql(sql);
			String pageSql = buildPageSql(orderSql);
			metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);
			// 采用物理分页后，就不需要mybatis的内存分页了，所以重置下面的两个参数
			metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
			metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
			Connection connection = (Connection) invocation.getArgs()[0];
			// 重设分页参数里的总页数等
			setPageParameter(sql, connection, mappedStatement, boundSql);
		}
		// 将执行权交给下一个拦截器
		return invocation.proceed();
	}

	/**
	 * 获取总记录
	 * 
	 * @param sql
	 * @param connection
	 * @param mappedStatement
	 * @param boundSql
	 */
	private void setPageParameter(String sql, Connection connection, MappedStatement mappedStatement,
			BoundSql boundSql) {
		// 记录总记录数
		String countSql = "select count(0) from (" + sql + ") as total";
		PreparedStatement countStmt = null;
		ResultSet rs = null;
		try {
			countStmt = connection.prepareStatement(countSql);
			BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), countSql,
					boundSql.getParameterMappings(), boundSql.getParameterObject());
			setParameters(countStmt, mappedStatement, countBS, boundSql.getParameterObject());
			rs = countStmt.executeQuery();
			long totalCount = 0;
			if (rs.next()) {
				totalCount = rs.getInt(1);
			}
			// page.setTotalCount(totalCount);
			PageContext.setTotalCount(totalCount);
			long totalPage = totalCount / PageContext.getPageSize()
					+ ((totalCount % PageContext.getPageSize() == 0) ? 0 : 1);
			PageContext.setTotalPage(totalPage);
		} catch (SQLException e) {
			logger.error("Ignore this exception", e);
		} finally {
			try {
				rs.close();
			} catch (SQLException e) {
				logger.error("Ignore this exception", e);
			}
			try {
				countStmt.close();
			} catch (SQLException e) {
				logger.error("Ignore this exception", e);
			}
		}

	}

	/**
	 * 对SQL参数(?)设值
	 * 
	 * @param ps
	 * @param mappedStatement
	 * @param boundSql
	 * @param parameterObject
	 * @throws SQLException
	 */
	private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
			Object parameterObject) throws SQLException {
		ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
		parameterHandler.setParameters(ps);
	}

	/**
	 * 生成排序
	 * 
	 * @param sql
	 * @return
	 * @throws Exception
	 */
	private String buildOrderSql(String sql) throws Exception {
		if (PageContext.getSort() != null && PageContext.getOrder() != null) {
			String[] sorts = PageContext.getSort().split(",");
			String[] orders = PageContext.getOrder().split(",");
			if (sorts.length != orders.length) {
				throw new Exception("sorts.length!=orders.length");
			}else{
				StringBuilder orderSql = new StringBuilder(sql);
				orderSql.append(" order by ");
				for (int i = 0; i < sorts.length; i++) {
					if (i != 0) {
						orderSql.append(",");
					}
					//驼峰命名转下划线
					orderSql.append(NameConverter.conver(sorts[i]) + " " + orders[i]);
				}
				//System.out.println(orderSql.toString());
				return orderSql.toString();
			}
		} else
			return sql;
	}

	/**
	 * 根据数据库类型，生成特定的分页sql
	 * 
	 * @param sql
	 * @param page
	 * @return
	 */
	private String buildPageSql(String sql) {
		if (PageContext.getPageOffset() != null && PageContext.getPageSize() != null) {
			StringBuilder pageSql = new StringBuilder();
			if ("mysql".equals(dialect)) {
				pageSql = buildPageSqlForMysql(sql);
			} else if ("oracle".equals(dialect)) {
				pageSql = buildPageSqlForOracle(sql);
			} else {
				return sql;
			}
			return pageSql.toString();
		} else {
			return sql;
		}
	}

	/**
	 * mysql的分页语句
	 * 
	 * @param sql
	 * @param page
	 * @return String
	 */
	public StringBuilder buildPageSqlForMysql(String sql) {
		StringBuilder pageSql = new StringBuilder(100);
//		String beginrow = String.valueOf((PageContext.getPageOffset() - 1) * PageContext.getPageSize());
		String beginrow = String.valueOf(PageContext.getPageOffset());
		pageSql.append(sql);
		int endRow=PageContext.getPageOffset()+ PageContext.getPageSize();
		pageSql.append(" limit " + beginrow + "," +endRow);
		return pageSql;
	}

	/**
	 * 参考hibernate的实现完成oracle的分页
	 * 
	 * @param sql
	 * @param page
	 * @return String
	 */
	public StringBuilder buildPageSqlForOracle(String sql) {
		StringBuilder pageSql = new StringBuilder(100);
		String beginrow = String.valueOf((PageContext.getPageOffset() - 1) * PageContext.getPageSize());
		String endrow = String.valueOf(PageContext.getPageOffset() * PageContext.getPageSize());
		pageSql.append("select * from ( select temp.*, rownum row_id from ( ");
		pageSql.append(sql);
		pageSql.append(" ) temp where rownum <= ").append(endrow);
		pageSql.append(") where row_id > ").append(beginrow);
		return pageSql;
	}

	@Override
	public Object plugin(Object target) {
		// 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的次数
		if (target instanceof StatementHandler) {
			return Plugin.wrap(target, this);
		} else {
			return target;
		}
	}

	@Override
	public void setProperties(Properties properties) {
	}

}
