package com.magotzis.vo;

import java.io.Serializable;

import com.magotzis.util.StringUtil;

@SuppressWarnings("serial")
public class UserLoginVo implements Serializable {

	private String username;
	private String password;
	private String grade;
	
	public void trim() {
		if (!StringUtil.isEmpty(username)) {
			this.username = this.username.trim();
		}
		if (!StringUtil.isEmpty(password)) {
			this.password = this.password.trim();
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

}
