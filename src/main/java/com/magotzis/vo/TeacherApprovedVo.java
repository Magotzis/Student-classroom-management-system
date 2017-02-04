package com.magotzis.vo;

import com.magotzis.po.Schooltime;

@SuppressWarnings("serial")
public class TeacherApprovedVo extends Schooltime {

	private String tno;
	private String tname;
	private String lname;

	public String getTno() {
		return tno;
	}

	public String getTname() {
		return tname;
	}

	public void setTno(String tno) {
		this.tno = tno;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

}
