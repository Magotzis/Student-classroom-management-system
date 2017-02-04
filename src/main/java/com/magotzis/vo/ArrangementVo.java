package com.magotzis.vo;

import java.io.Serializable;

@SuppressWarnings("serial")
public class ArrangementVo implements Serializable {

	private String lno;
	private String lname;
	private String lcredit;
	private String gtime;
	private String id;

	public String getLno() {
		return lno;
	}

	public void setLno(String lno) {
		this.lno = lno;
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

	public String getGtime() {
		return gtime;
	}

	public void setGtime(String gtime) {
		this.gtime = gtime;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
