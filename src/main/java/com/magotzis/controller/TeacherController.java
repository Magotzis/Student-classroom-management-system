package com.magotzis.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.magotzis.po.Teacher;
import com.magotzis.service.TeacherService;
import com.magotzis.util.ResultMap;

@Controller
@RequestMapping("teacher")
public class TeacherController {

	@Autowired
	private TeacherService teacherService;
	
	@ResponseBody
	@RequestMapping("getTeacherByList")
	public ResultMap getTeacherByList() {
		ResultMap resultMap = new ResultMap();
		List<Teacher> list = teacherService.getTeacherByList();
		return resultMap.data(list);
	}
	
}
