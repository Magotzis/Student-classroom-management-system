package com.magotzis.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Student extends BasePo implements Serializable {

	private String sno;// 学号
	private String sname;// 姓名
	private String ssex;// 性别
	private String sdept;// 系别
	private String state;// 状态

	public String getSno() {
		return sno;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}

	public String getSsex() {
		return ssex;
	}

	public void setSsex(String ssex) {
		this.ssex = ssex;
	}

	public String getSdept() {
		return sdept;
	}

	public void setSdept(String sdept) {
		this.sdept = sdept;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}