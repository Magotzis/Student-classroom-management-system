package com.magotzis.vo;

import com.magotzis.po.Schooltime;

@SuppressWarnings("serial")
public class StudentApprovedVo extends Schooltime {

	private String sno;
	private String sname;

	public String getSno() {
		return sno;
	}

	public String getSname() {
		return sname;
	}

	public void setSno(String sno) {
		this.sno = sno;
	}

	public void setSname(String sname) {
		this.sname = sname;
	}
}
