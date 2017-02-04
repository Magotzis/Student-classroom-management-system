package com.magotzis.po;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Schooltime extends BasePo implements Serializable {

	private String lno;// 课程号
	private String cno; // 课室号
	private String gtime;// 时间安排
	private String state;// 状态
	private String apply;// 学生申请(S),老师申请(T)
	private String applyId;// 申请人ID
	private String oldId;// 老师申请时候的旧的记录

	public String getLno() {
		return lno;
	}

	public void setLno(String lno) {
		this.lno = lno;
	}

	public String getCno() {
		return cno;
	}

	public void setCno(String cno) {
		this.cno = cno;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getGtime() {
		return gtime;
	}

	public void setGtime(String gtime) {
		this.gtime = gtime;
	}

	public String getApply() {
		return apply;
	}

	public void setApply(String apply) {
		this.apply = apply;
	}

	public String getApplyId() {
		return applyId;
	}

	public void setApplyId(String applyId) {
		this.applyId = applyId;
	}

	public String getOldId() {
		return oldId;
	}

	public void setOldId(String oldId) {
		this.oldId = oldId;
	}

}