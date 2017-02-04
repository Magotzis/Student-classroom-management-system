package com.magotzis.mybatis.autobuild;

import java.sql.Connection;
import java.util.Properties;

import org.apache.ibatis.executor.statement.StatementHandler;
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

/**
 * 通过拦截<code>StatementHandler</code>的<code>prepare</code>方法，重写sql语句实现物理分页。
 * 签名里要拦截的类型只能是接口。
 * 
 * @author jeff he
 * 
 */
@Intercepts({ @Signature(type = StatementHandler.class, method = "prepare", args = { Connection.class }) })
public class AutoBuildInterceptor implements Interceptor {
	private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
	private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
	private static final String UPDATE_MATCHER = "^.*update.*";
	private static final String INSERT_MATCHER = "^.*insert.*";
	private static final String BATCH_UPDATE_MATCHER = "^.*batchUpdate.*";
	private static final String BATCH_INSERT_MATCHER = "^.*batchInsert.*";
	private static final String DELETEBYID_MATCHER = "^.*deleteById.*";
	private static final String GETBYID_MATCHER = "^.*getById.*";
	private static final String LIST_MATCHER = "^.*list.*";
	private static final String FINDBYPAGE_MATCHER = "^.*findByPage";

	public static void main(String[] args) {
		System.out.println("batchUpdatexx".matches(BATCH_UPDATE_MATCHER));
	}

	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		// System.out.println("auto");
		StatementHandler statementHandler = (StatementHandler) invocation
				.getTarget();
		MetaObject metaStatementHandler = MetaObject.forObject(
				statementHandler, DEFAULT_OBJECT_FACTORY,
				DEFAULT_OBJECT_WRAPPER_FACTORY);
		// 分离代理对象链(由于目标类可能被多个拦截器拦截，从而形成多次代理，通过下面的两次循环可以分离出最原始的的目标类)
		while (metaStatementHandler.hasGetter("h")) {
			Object object = metaStatementHandler.getValue("h");
			metaStatementHandler = MetaObject.forObject(object,
					DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
		}
		// 分离最后一个代理对象的目标类
		while (metaStatementHandler.hasGetter("target")) {
			Object object = metaStatementHandler.getValue("target");
			metaStatementHandler = MetaObject.forObject(object,
					DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
		}
		/**
		 * 上面的代码基本上是固定的
		 */
		MappedStatement mappedStatement = (MappedStatement) metaStatementHandler
				.getValue("delegate.mappedStatement");
		String sqlId = mappedStatement.getId();

		BoundSql boundSql = (BoundSql) metaStatementHandler
				.getValue("delegate.boundSql");
		// 获得参数
		Object parameterObject = boundSql.getParameterObject();
		String sql = boundSql.getSql();
		// 重写sql
		if (sqlId.matches(INSERT_MATCHER))
			sql = SqlBulider.buildLogInsertSql(SqlBulider
					.buildRawInsertSql(parameterObject));
		else if (sqlId.matches(UPDATE_MATCHER))
			sql = SqlBulider.buildLogUpdateSql(SqlBulider
					.buildRawUpdateSql(parameterObject));
		else if (sqlId.matches(GETBYID_MATCHER))
			sql = SqlBulider.buildGetByIdSql(parameterObject);
		else if (sqlId.matches(DELETEBYID_MATCHER))
			sql = SqlBulider.buildDeleteByIdSql(parameterObject);
		else if (sqlId.matches(LIST_MATCHER)
				|| sqlId.matches(FINDBYPAGE_MATCHER))
			sql = SqlBulider.buildQuerySql(parameterObject);
		else if (sqlId.matches(BATCH_INSERT_MATCHER))
			sql = SqlBulider.buildBatchInsertSql(sql);
		else if (sqlId.matches(BATCH_UPDATE_MATCHER))
			sql = SqlBulider.buildBatchUpdateSql(sql);
		metaStatementHandler.setValue("delegate.boundSql.sql", sql);
		// 将执行权交给下一个拦截器
		return invocation.proceed();
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
