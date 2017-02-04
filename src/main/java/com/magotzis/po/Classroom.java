package com.magotzis.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Classroom extends BasePo implements Serializable {

	private String cno;// 课室号
	private int cnumber;// 课室座位数
	private String cdevice;// 课室设备
	private String cplace;// 课室地点
	private String ctime;// 课室空闲时间
	private String state;// 课室状态

	public String getCno() {
		return cno;
	}

	public void setCno(String cno) {
		this.cno = cno;
	}

	public int getCnumber() {
		return cnumber;
	}

	public void setCnumber(int cnumber) {
		this.cnumber = cnumber;
	}

	public String getCdevice() {
		return cdevice;
	}

	public void setCdevice(String cdevice) {
		this.cdevice = cdevice;
	}

	public String getCplace() {
		return cplace;
	}

	public void setCplace(String cplace) {
		this.cplace = cplace;
	}

	public String getCtime() {
		return ctime;
	}

	public void setCtime(String ctime) {
		this.ctime = ctime;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
