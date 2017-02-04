package com.magotzis.vo;

import java.io.Serializable;

import com.magotzis.po.Lesson;

@SuppressWarnings("serial")
public class LessonVo extends Lesson implements Serializable{
	
	private String tname;
	private String tsex;
	private String ttitle;

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public String getTsex() {
		return tsex;
	}

	public void setTsex(String tsex) {
		this.tsex = tsex;
	}

	public String getTtitle() {
		return ttitle;
	}

	public void setTtitle(String ttitle) {
		this.ttitle = ttitle;
	}
	
}
