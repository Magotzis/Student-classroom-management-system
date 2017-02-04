package com.magotzis.util;

import java.util.HashMap;

import com.magotzis.mybatis.page.Page;

/**
 * 
 * @author jeff he
 *
 */
@SuppressWarnings("serial")
public class ResultMap extends HashMap<String, Object> {

	public ResultMap() {

	}

	public ResultMap success() {
		this.put("success", true);
		return this;
	}

	public ResultMap fail() {
		this.put("success", false);
		return this;
	}

	public ResultMap info(String info) {
		this.put("info", info);
		return this;
	}

	public ResultMap msg(String msg) {
		this.put("message", msg);
		return this;
	}

	public ResultMap data(Object obj) {
		this.put("data", obj);
		return this;
	}

	public ResultMap page(Page<?> page) {
		this.put("recordsFiltered", page.getTotal());//搜索后总数
		this.put("recordsTotal", page.getTotal());//不搜索的总数
		this.put("data", page.getRows());
		this.put("length", page.getTotalPage());
		this.put("start", page.getOffset());
		return this;
	}


}
