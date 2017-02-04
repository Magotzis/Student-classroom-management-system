package com.magotzis.common;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Administrator
 * 
 */
public class BaseInfo {
	
	// 用户id
	public static String GetUserId(HttpServletRequest request) {
		String userId = "";
		if (request.getSession().getAttribute("userId") != null) {
			userId = request.getSession().getAttribute("userId").toString();
		}
		return userId;
	}

	// 用户id
	public static void setUserId(HttpServletRequest request, String id) {
		request.getSession().setAttribute("userId", id);
	}

	// 登录名
	public static String GetUserName(HttpServletRequest request) {
		String username = "";
		if (request.getSession().getAttribute("username") != null) {
			username = request.getSession().getAttribute("username").toString();
		}
		return username;
	}

	// 登录名
	public static void SetUserName(HttpServletRequest request, String username) {
		request.getSession().setAttribute("username", username);
	}

	// 用户名
	public static String GetName(HttpServletRequest request) {
		String username = "";
		if (request.getSession().getAttribute("name") != null) {
			username = request.getSession().getAttribute("name").toString();
		}
		return username;
	}

	// 用户名
	public static void SetName(HttpServletRequest request, String username) {
		request.getSession().setAttribute("name", username);
	}


	// 用户角色
	public static String GetUserGrade(HttpServletRequest request) {
		String usergrade = "";
		if (request.getSession().getAttribute("usergrade") != null) {
			usergrade = request.getSession().getAttribute("usergrade").toString();
		}
		return usergrade;
	}

	// 用户角色
	public static void SetUserGrade(HttpServletRequest request, String usergrade) {
		request.getSession().setAttribute("usergrade", usergrade);
	}


}
