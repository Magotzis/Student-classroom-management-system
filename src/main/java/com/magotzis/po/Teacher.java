package com.magotzis.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Teacher extends BasePo implements Serializable {

	private String tno;// 教师号
	private String tname;// 姓名
	private String tsex; // 性别
	private String ttitle;// 职称
	private String state;// 状态

	public String getTno() {
		return tno;
	}

	public void setTno(String tno) {
		this.tno = tno;
	}

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getTtitle() {
		return ttitle;
	}

	public void setTtitle(String ttitle) {
		this.ttitle = ttitle;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getTsex() {
		return tsex;
	}

	public void setTsex(String tsex) {
		this.tsex = tsex;
	}

}