package com.magotzis.mybatis.autobuild;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.UUID;

import org.apache.ibatis.session.SqlSessionException;

public class SqlBulider {

	private static SimpleDateFormat sdf = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss");

	// 重写插入语句不含log
	public static String buildRawInsertSql(Object obj)
			throws IllegalArgumentException, IllegalAccessException {
		Class<?> clz = obj.getClass();
		String clzName = clz.getSimpleName();
		String _clzName = NameConverter.conver(clzName);
		// 通用的插入sql语句
		StringBuilder rawInsertSql = new StringBuilder("insert into `"
				+ _clzName + "` ");
		StringBuilder columnsStr = new StringBuilder("(");
		StringBuilder valuesStr = new StringBuilder("(");
		// 获取本身的属性
		Field[] localFields = clz.getDeclaredFields();
		// 获取继承的属性（必须为public的）
		Field[] inheritFields = clz.getFields();
		String _name;
		String fName;
		Object value;
		for (Field field : inheritFields) {
			/**
			 * 抑制Java的访问控制检查 如果不加上上面这句，将会Error: TestPrivate can not access a
			 * member of class PrivateClass with modifiers "private"
			 */
			field.setAccessible(true);
			fName = field.getName();
			_name = NameConverter.conver(fName);
			value = field.get(obj);
			// 如果id为""就替换掉，easyui的form表单提交会默认提交个""进来
			if (fName.equals("id") && value.equals(""))
				value = UUID.randomUUID().toString();
			columnsStr.append(_name + ",");
			// 跳过null
			if (value != null) {
				if (field.getType() == String.class && value != null)
					valuesStr.append("'" + value + "',");
				else if (field.getType() == java.util.Date.class)
					valuesStr.append("'" + sdf.format((java.util.Date) value)
							+ "'");
				else
					valuesStr.append(value + ",");
			}
		}
		for (Field field : localFields) {
			/**
			 * 抑制Java的访问控制检查 如果不加上上面这句，将会Error: TestPrivate can not access a
			 * member of class PrivateClass with modifiers "private"
			 */
			field.setAccessible(true);
			fName = field.getName();
			_name = NameConverter.conver(fName);
			value = field.get(obj);
			columnsStr.append(_name);
			// String类型
			if (field.getType() == String.class && value != null)
				valuesStr.append("'" + value + "'");
			// 日期类型
			else if (field.getType() == java.util.Date.class && value != null)
				valuesStr
						.append("'" + sdf.format((java.util.Date) value) + "'");
			else
				valuesStr.append(value);
			if (field == localFields[localFields.length - 1]) {
				columnsStr.append(")");
				valuesStr.append(")");
			} else {
				columnsStr.append(",");
				valuesStr.append(",");
			}
		}
		rawInsertSql.append(columnsStr + " values " + valuesStr);
		return rawInsertSql.toString();
	}

	// 构建原生更新语句
	public static String buildRawUpdateSql(Object obj)
			throws IllegalArgumentException, IllegalAccessException,
			NoSuchFieldException, SecurityException {
		Class<?> clz = obj.getClass();
		String clzName = clz.getSimpleName();
		String _clzName = NameConverter.conver(clzName);
		// 通用update的sql语句
		StringBuilder rawUpdateSql = new StringBuilder("update  `" + _clzName
				+ "` set");
		// 获取本身的属性
		Field[] fields = clz.getDeclaredFields();
		// 获取继承的属性（必须为public的）
		Field[] inheritFields = clz.getFields();
		String _name;
		String fName;
		Object value;
		for (Field field : inheritFields) {
			// 跳过主键
			if (field.getName().equals("id"))
				continue;
			/**
			 * 抑制Java的访问控制检查 如果不加上上面这句，将会Error: TestPrivate can not access a
			 * member of class PrivateClass with modifiers "private"
			 */
			field.setAccessible(true);
			fName = field.getName();
			_name = NameConverter.conver(fName);
			value = field.get(obj);
			// String类型
			if (field.getType() == String.class && value != null)
				rawUpdateSql.append(" " + _name + " = '" + value + "',");
			// 日期类型
			else if (field.getType() == java.util.Date.class && value != null)
				rawUpdateSql.append(" " + _name + " = '"
						+ sdf.format((java.util.Date) value) + "',");
			else if (value != null)
				rawUpdateSql.append(" " + _name + " = " + value + ",");
		}
		for (Field field : fields) {
			// 跳过主键
			if (field.getName().equals("id"))
				continue;
			/**
			 * 抑制Java的访问控制检查 如果不加上上面这句，将会Error: TestPrivate can not access a
			 * member of class PrivateClass with modifiers "private"
			 */
			field.setAccessible(true);
			fName = field.getName();
			_name = NameConverter.conver(fName);
			value = field.get(obj);
			// 跳过null
			if (value != null) {
				// String类型
				if (field.getType() == String.class)
					rawUpdateSql.append(" " + _name + " = '" + value + "',");
				// 日期类型
				else if (field.getType() == java.util.Date.class)
					rawUpdateSql.append(" " + _name + " = '"
							+ sdf.format((java.util.Date) value) + "',");
				else
					rawUpdateSql.append(" " + _name + " = " + value + ",");
			}
		}
		// 删除最后一个多余的逗号
		rawUpdateSql.deleteCharAt(rawUpdateSql.length() - 1);
		// 获取主键
		Field idField = clz.getField("id");
		Object id = idField.get(obj);
		if (idField.getType() == String.class)
			rawUpdateSql.append(" where id = '" + id + "'");
		else
			rawUpdateSql.append(" where id = " + id);
		return rawUpdateSql.toString();
	}

	// 构建删除语句
	public static String buildDeleteByIdSql(Object id) {
		String clzName = MybatisContext.getClzName();
		String _clzName = NameConverter.conver(clzName);
		// 通用delete的sql语句
		StringBuilder deleteByIdSql = new StringBuilder("delete from `"
				+ _clzName + "` where id = ");
		// String类型
		if (id.getClass() == String.class && id != null)
			deleteByIdSql.append("'" + id + "'");
		else if (id != null)
			deleteByIdSql.append(id);
		return deleteByIdSql.toString();
	}

	// 构建getById语句
	public static String buildGetByIdSql(Object id) {
		String clzName = MybatisContext.getClzName();
		String _clzName = NameConverter.conver(clzName);
		StringBuilder getByIdSql = new StringBuilder("select * from `"
				+ _clzName + "` where id = ");
		if (id == null)
			throw new SqlSessionException("Id 不能为 null");
		else if (id.getClass() == String.class)
			getByIdSql.append("'" + id + "'");
		else
			getByIdSql.append(id);
		return getByIdSql.toString();
	}

	// 构建默认query语句
	public static String buildQuerySql(Object obj)
			throws IllegalArgumentException, IllegalAccessException {
		Class<?> clz = obj.getClass();
		String clzName = clz.getSimpleName();
		String _clzName = NameConverter.conver(clzName);
		StringBuilder querySql = new StringBuilder("select * from `" + _clzName
				+ "`  where id is not null");
		// 获取本身的属性
		Field[] fields = clz.getDeclaredFields();
		// 获取继承的属性（必须为public的）
		Field[] inheritFields = clz.getFields();
		String _name;
		String fName;
		Object value;
		for (Field field : inheritFields) {
			/**
			 * 抑制Java的访问控制检查 如果不加上上面这句，将会Error: TestPrivate can not access a
			 * member of class PrivateClass with modifiers "private"
			 */
			field.setAccessible(true);
			fName = field.getName();
			_name = NameConverter.conver(fName);
			value = field.get(obj);
			if (field.getType() == String.class && value != null)
				querySql.append(" and " + _name + " = '" + value + "'");
			else if (value != null)
				querySql.append(" and " + _name + " = " + value);
		}
		for (Field field : fields) {
			field.setAccessible(true);
			fName = field.getName();
			_name = NameConverter.conver(fName);
			value = field.get(obj);
			if (field.getType() == String.class && value != null)
				querySql.append(" and " + _name + " = '" + value + "'");
			else if (value != null)
				querySql.append(" and " + _name + " = " + value);
		}
		return querySql.toString();
	}

	// 重写插入语句
	public static String buildLogInsertSql(String sql) {
		StringBuilder insertSql = new StringBuilder(sql);
		/*
		 * String createId = MybatisContext.getUserId();
		 * insertSql.insert(insertSql.indexOf(")"), ",create_dt,create_id");
		 * insertSql.insert(insertSql.lastIndexOf(")"), ",now(),'" + createId +
		 * "'");
		 */
		return insertSql.toString();
	}

	// 重写更新语句
	public static String buildLogUpdateSql(String sql) {
		StringBuilder updateSql = new StringBuilder(sql);
		/*
		 * String updateId = MybatisContext.getUserId(); if (updateId != null)
		 * updateSql.insert(updateSql.indexOf("where"),
		 * ",update_dt=now(),update_id='" + updateId + "' "); else
		 * updateSql.insert(updateSql.indexOf("where"),
		 * ",update_dt=now(),update_id=" + updateId + " ");
		 */
		return updateSql.toString();
	}

	// 为批量更新加入log字段
	public static String buildBatchUpdateSql(String sql) {
		sql = sql.toLowerCase();
		StringBuilder updateSql = new StringBuilder(sql);
		/*
		 * String updateId = MybatisContext.getUserId(); String addStr; if
		 * (updateId != null) addStr = ",update_dt=now(),update_id='" + updateId
		 * + "' "; else addStr = ",update_dt=now(),update_id= null "; int
		 * fromIndex = 0; int index = 0; while (fromIndex < updateSql.length()
		 * && (index = updateSql.indexOf("where", fromIndex)) != -1) {
		 * updateSql.insert(index, addStr); fromIndex = index + addStr.length()
		 * + 1; }
		 */
		return updateSql.toString();
	}

	// 为批量插入加入log字段
	public static String buildBatchInsertSql(String sql) {
		sql = sql.toLowerCase().replaceAll("uuid\\(\\)", "uuid");
		StringBuilder insertSql = new StringBuilder(sql);
		/*
		 * String createId = MybatisContext.getUserId(); int fromIndex = 0; int
		 * index2 = 0; int index1 = 0; String addStr1 = ",create_dt,create_id";
		 * String addStr2 = ",now(),'" + createId + "'"; if (createId != null)
		 * addStr2 = ",now(),'" + createId + "'"; else addStr2 = ",now(),null";
		 * index1 = insertSql.indexOf(")", fromIndex); insertSql.insert(index1,
		 * addStr1); fromIndex = index1 + addStr1.length() + 1; while (fromIndex
		 * < insertSql.length() && (index2 = insertSql.indexOf(")", fromIndex))
		 * != -1) { insertSql.insert(index2, addStr2); fromIndex = index2 +
		 * addStr2.length() + 1; }
		 */
		return insertSql.toString().replaceAll("uuid", "uuid()");
	}
}
