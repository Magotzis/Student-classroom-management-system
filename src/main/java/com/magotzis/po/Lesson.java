package com.magotzis.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Lesson extends BasePo implements Serializable {

	private String lno;// 课程号
	private String tno;// 教师号
	private String lname; // 课程名
	private String lcredit;// 学分
	private String state;// 状态

	public String getLno() {
		return lno;
	}

	public void setLno(String lno) {
		this.lno = lno;
	}

	public String getTno() {
		return tno;
	}

	public void setTno(String tno) {
		this.tno = tno;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getLcredit() {
		return lcredit;
	}

	public void setLcredit(String lcredit) {
		this.lcredit = lcredit;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}