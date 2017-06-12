package com.magotzis.controller;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.magotzis.vo.UserLoginVo;
import com.magotzis.common.BaseInfo;
import com.magotzis.po.Admin;
import com.magotzis.po.Student;
import com.magotzis.po.Teacher;
import com.magotzis.service.AdminService;
import com.magotzis.service.StudentService;
import com.magotzis.service.TeacherService;
import com.magotzis.util.ResultMap;
import com.magotzis.util.StringUtil;

@Controller
@RequestMapping("common")
public class CommonController {

	@Autowired
	private StudentService studentService;

	@Autowired
	private TeacherService teacherService;

	@Autowired
	private AdminService adminService;

	@ResponseBody
	@RequestMapping("login")
	public ResultMap login(UserLoginVo userLoginVo, HttpServletRequest request) {
		ResultMap resultMap = new ResultMap();
		try {
			userLoginVo.trim();// 去除账号和密码的空格问题
			String grade = userLoginVo.getGrade();
			if (StringUtil.isEmpty(grade)) {
				return resultMap.fail().info("请先选择身份！");
			} else if (grade.equals("S")) {
				// 学生登陆
				Student student;
				student = studentService.login(userLoginVo); // 检查登陆情况
				if (student != null) {
					// 保存session
					request.getSession().invalidate();
					BaseInfo.setUserId(request, student.getId());
					BaseInfo.SetUserName(request, student.getSno());
					BaseInfo.SetName(request, student.getSname());
					BaseInfo.SetUserGrade(request, "S");
					resultMap.success().info("success");
				}else {
					return resultMap.fail().info("账号密码不正确,请检查后重试");
				}
			} else if (grade.equals("T")) {
				// 老师登陆
				Teacher teacher;
				teacher = teacherService.login(userLoginVo); // 检查登陆情况
				if (teacher != null) {
					// 保存session
					request.getSession().invalidate();
					BaseInfo.setUserId(request, teacher.getId());
					BaseInfo.SetUserName(request, teacher.getTno());
					BaseInfo.SetName(request, teacher.getTname());
					BaseInfo.SetUserGrade(request, "T");
					resultMap.success().info("success");
				}else {
					return resultMap.fail().info("账号密码不正确,请检查后重试");
				}
			} else if (grade.equals("A")) {
				Admin admin;
				admin = adminService.login(userLoginVo); // 检查登陆情况
				if (admin != null) {
					// 保存session
					request.getSession().invalidate();
					BaseInfo.setUserId(request, admin.getId());
					BaseInfo.SetUserName(request, admin.getLoginName());
					BaseInfo.SetName(request, admin.getLoginName());
					BaseInfo.SetUserGrade(request, "A");
					resultMap.success().info("success");
				}else {
					return resultMap.fail().info("账号密码不正确,请检查后重试");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.fail().info("登录失败");
		}
		return resultMap;
	}
	
	@RequestMapping("logout") // all
	@ResponseBody
	public ResultMap logout(HttpServletRequest request,HttpServletResponse response) {
		ResultMap resultMap = new ResultMap();
		try {
			request.getSession().invalidate();
			response.sendRedirect("../index.jsp");
			resultMap.success().info("success");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.fail().info("fail");
		}
		return resultMap;
	}
	
	@RequestMapping("studentRegister") 
	@ResponseBody
	public ResultMap studentRegister(Student student){
		ResultMap resultMap = new ResultMap();
		try {
			Student bean =studentService.getByUserName(student.getSno());
			if(bean != null){
				resultMap.fail().info("当前用户名已存在！");
			}else{
				student.setState("T");
				studentService.add(student);
				resultMap.success().info("注册成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.fail().info("fail");
		}
		return resultMap;
	}
	
	@RequestMapping("teacherRegister") 
	@ResponseBody
	public ResultMap teacherRegister(Teacher teacher){
		ResultMap resultMap = new ResultMap();
		try {
			Teacher bean =teacherService.getByUserName(teacher.getTno());
			if(bean != null){
				resultMap.fail().info("当前用户名已存在！");
			}else{
				teacher.setState("T");
				teacherService.add(teacher);
				resultMap.success().info("注册成功");
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.fail().info("fail");
		}
		return resultMap;
	}

}
