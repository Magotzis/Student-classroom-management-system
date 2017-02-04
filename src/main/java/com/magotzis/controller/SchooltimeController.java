package com.magotzis.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.magotzis.common.BaseInfo;
import com.magotzis.service.SchooltimeService;
import com.magotzis.po.Schooltime;
import com.magotzis.util.ResultMap;

@Controller
@RequestMapping("schooltime")
public class SchooltimeController {

	@Autowired
	private SchooltimeService schooltimeService;

	/**
	 * 添加申请记录
	 * 
	 * @param schooltime
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("addSchoolTime")
	public ResultMap addSchoolTime(Schooltime schooltime,
			HttpServletRequest request) {
		ResultMap resultMap = new ResultMap();
		try {
			schooltime.setApplyId(BaseInfo.GetUserId(request));
			schooltimeService.add(schooltime);
			resultMap.success().info("安排成功");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.fail().info("添加失败");
		}
		return resultMap;
	}

	/**
	 * 查找申请记录
	 */
	@ResponseBody
	@RequestMapping("findApplyByPage")
	public ResultMap findApplyByPage(HttpServletRequest request) {
		ResultMap resultMap = new ResultMap();
		try {
			String id = BaseInfo.GetUserId(request);
			resultMap.success().page(schooltimeService.findApplyByPage(id));
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.fail().info("查询失败");
		}
		return resultMap;
	}
	
	/*
	 * 查找学生待审批记录
	 */
	@ResponseBody
	@RequestMapping("findStudentApprovedByPage")
	public ResultMap findStudentApprovedByPage(String apply) {
		ResultMap resultMap = new ResultMap();
		try {
			resultMap.success().page(schooltimeService.findStudentApprovedByPage(apply));
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.fail().info("查询失败");
		}
		return resultMap;
	}
	
	/*
	 * 查找老师待审批记录
	 */
	@ResponseBody
	@RequestMapping("findTeacherApprovedByPage")
	public ResultMap findTeacherApprovedByPage(String apply) {
		ResultMap resultMap = new ResultMap();
		try {
			resultMap.success().page(schooltimeService.findTeacherApprovedByPage(apply));
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.fail().info("查询失败");
		}
		return resultMap;
	}
	
	/*
	 * 审批申请
	 */
	@ResponseBody
	@RequestMapping("approvedSchoolTime")
	public ResultMap approvedSchoolTime(Schooltime schooltime) {
		ResultMap resultMap = new ResultMap();
		try {
			String state = schooltime.getState();
			if (state.equals("T")) {
				Schooltime bean = schooltimeService.getById(schooltime.getId());
				// 如果是教师的,还要把旧记录的去掉
				if (schooltime.getApply().equals("T")) {
					Schooltime old = schooltimeService.getById(schooltime.getOldId());
					old.setState("F");
					schooltimeService.update(old);
				}
				bean.setState("T");
				schooltimeService.update(bean);
				resultMap.success().info("审批成功");
			}else if (state.equals("P")) {
				//审批不通过
				schooltimeService.update(schooltime);
				resultMap.success().info("审批成功");
			}else {
				resultMap.fail().info("请先选择审批通过与否");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.fail().info("审批失败");
		}
		return resultMap;
	}
}
