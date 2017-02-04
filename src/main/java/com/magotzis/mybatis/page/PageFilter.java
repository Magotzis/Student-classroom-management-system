package com.magotzis.mybatis.page;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 将分页的pager数据嵌入localthread中
 * 
 * @author jeff he
 *
 */
public class PageFilter implements Filter {
	/**
	 * 分页大小
	 */
	public void destroy() {

	}

	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		Integer pageSize = 10;
		Integer pageOffset = 0;
		String order = null;
		String sort = null;
		try {
			// 尝试获取分页大小并强制转为integer
			pageSize = Integer.parseInt(req.getParameter("length"));
			// easyui特定参数名
			// pageSize = Integer.parseInt(req.getParameter("rows"));
		} catch (NumberFormatException e) {
			// 捕获异常，设置为默认分页10
			pageSize = 10;
		}
		try {
			// 尝试获取起始页大小并强制转为integer
			pageOffset = Integer.parseInt(req.getParameter("start"));
		
			// easyui特定参数名
			// pageOffset = Integer.parseInt(req.getParameter("page"));
		} catch (NumberFormatException e) {
			// 捕获异常，设置为默认起始页为1
			pageOffset = 0;
		}
		// 尝试获取sort
		if (req.getParameter("sidx") != null && !req.getParameter("sidx").equals("")) {
			sort = req.getParameter("sidx");
		}
		// 尝试获取order
		if (req.getParameter("sord") != null && !req.getParameter("sord").equals("")) {
			order = req.getParameter("sord");
		}
		try {
			// 将分页数据放入当前线程
			PageContext.setOrder(order);
			PageContext.setSort(sort);
			PageContext.setPageOffset(pageOffset);
			PageContext.setPageSize(pageSize);
			// pass the request
			chain.doFilter(req, resp);
		} finally {
			// 清除分页数据
			PageContext.removeOrder();
			PageContext.removeSort();
			PageContext.removePageOffset();
			PageContext.removePageSize();
			PageContext.removeTotalCount();
			PageContext.removeTotalPage();
		}
	}

	public void init(FilterConfig cfg) throws ServletException {

	}

}
