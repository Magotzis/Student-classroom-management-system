package com.magotzis.po;

import java.io.Serializable;
import java.util.UUID;

@SuppressWarnings("serial")
// 基础字段
public class BasePo implements Serializable {

	/**
	 * id要为public的，如果是private的话，其它子类po无法访问这个id属性
	 */
	public String id;// uuid

	public BasePo() {
		super();
		UUID uuid = UUID.randomUUID();
		this.id = uuid.toString();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
