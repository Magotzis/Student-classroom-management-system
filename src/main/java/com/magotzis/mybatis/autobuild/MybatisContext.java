package com.magotzis.mybatis.autobuild;

public class MybatisContext {

	private static ThreadLocal<String> userId = new ThreadLocal<String>();

	private static ThreadLocal<String> ip = new ThreadLocal<String>();

	private static ThreadLocal<String> url = new ThreadLocal<String>();

	private static ThreadLocal<String> clzName = new ThreadLocal<String>();

	public static void clearContext() {
		userId.remove();
		ip.remove();
		url.remove();
		clzName.remove();
	}

	public static String getClzName() {
		return clzName.get();
	}

	public static void setClzName(String _clzName) {
		clzName.set(_clzName);
	}

	public static void removeClzName() {
		clzName.remove();
	}

	public static String getUserId() {
		return userId.get();
	}

	public static void setUserId(String _userId) {
		userId.set(_userId);
	}

	public static void removeUserId() {
		userId.remove();
	}

	public static String getIp() {
		return ip.get();
	}

	public static void setIp(String _ip) {
		ip.set(_ip);
	}

	public static void removeIp() {
		ip.remove();
	}

	public static String getUrl() {
		return url.get();
	}

	public static void setUrl(String _url) {
		url.set(_url);
	}

	public static void removeUrl() {
		url.remove();
	}
}
