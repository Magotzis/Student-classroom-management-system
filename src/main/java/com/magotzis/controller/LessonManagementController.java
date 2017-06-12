package com.magotzis.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.magotzis.util.ExcelFile;
import com.magotzis.common.BaseInfo;
import com.magotzis.po.Lesson;
import com.magotzis.service.LessonService;
import com.magotzis.util.ResultMap;
import com.magotzis.vo.LessonVo;

@Controller
@RequestMapping("lesson")
public class LessonManagementController {

	@Autowired
	private LessonService lessonService;

	@RequestMapping("findLessonByPage")
	@ResponseBody
	public ResultMap findLessonByPage(Lesson lesson) {
		ResultMap resultMap = new ResultMap();
		return resultMap.page(lessonService.findLessonByPage(lesson));
	}

	@RequestMapping("findLessonById")
	@ResponseBody
	public ResultMap findLessonById(String id) {
		ResultMap resultMap = new ResultMap();
		LessonVo lessonVo = lessonService.findLessonById(id);
		if (lessonVo == null) {
			return resultMap.info("该数据不存在");
		}
		return resultMap.data(lessonVo);
	}

	/**
	 * 找所有课程列表
	 * 
	 * @return
	 */
	@RequestMapping("findLessonByList")
	@ResponseBody
	public ResultMap findLessonByList() {
		ResultMap resultMap = new ResultMap();
		List<Lesson> list = lessonService.findLessonByList(null);
		return resultMap.data(list);
	}

	/**
	 * 找老师的课程列表
	 * 
	 * @return
	 */
	@RequestMapping("findLessonListByTeacher")
	@ResponseBody
	public ResultMap findLessonListByTeacher(HttpServletRequest request) {
		ResultMap resultMap = new ResultMap();
		String tno = BaseInfo.GetUserName(request);
		List<Lesson> list = lessonService.findLessonByList(tno);
		return resultMap.data(list);
	}

	/**
	 * 找课程的课程安排
	 * 
	 * @param lno
	 *            课程号
	 * @return
	 */
	@RequestMapping("findArrangementByPage")
	@ResponseBody
	public ResultMap findArrangementByPage(String lno) {
		ResultMap resultMap = new ResultMap();
		return resultMap.page(lessonService.findArrangementByPage(lno));
	}

	/**
	 * 删除课程
	 * 
	 * @param ids
	 *            需要删除的id串
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteLesson")
	public ResultMap deleteLesson(String ids) {
		ResultMap resultMap = new ResultMap();
		try {
			String arrId[] = ids.split(",");
			List<Lesson> list = new ArrayList<Lesson>();
			// 循环操作
			for (String id : arrId) {
				Lesson bean = lessonService.getById(id);
				bean.setState("F");
				list.add(bean);
			}
			// 批量更新
			lessonService.batchUpdateLesson(list);
			resultMap.success().info("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.fail().info("删除失败");
		}
		return resultMap;
	}

	/**
	 * 增加或编辑课程
	 * 
	 * @param Lesson
	 *            课程信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addOrUpdateLesson")
	public ResultMap addOrUpdateLesson(Lesson lesson) {
		ResultMap resultMap = new ResultMap();
		try {
			Lesson bean = lessonService.getById(lesson.getId());
			if (bean != null) {
				// 修改
				lessonService.update(lesson);
				resultMap.info("修改成功！");
			} else {
				// 新增
				Lesson add = lessonService.findLessonByLno(lesson.getLno());
				if (add != null) {
					resultMap.info("该课程已存在！");
				} else {
					// 插入
					lesson.setState("T");
					lessonService.add(lesson);
					resultMap.info("添加成功！");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.fail().info("修改失败");
		}
		return resultMap;
	}

	/*
	 * 导出课程信息
	 */
	@ResponseBody
	@RequestMapping("exportLessonExcel")
	public ResultMap exportLessonExcel(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		ResultMap resultMap = new ResultMap();
		try {
			String title = new String("sheet 1");
			String excelName = new String("课程一览表");
			// 创建表头信息
			List<String> header = new ArrayList<String>();
			header.add("课程号");
			header.add("课程名");
			header.add("学分");
			header.add("授课老师");
			header.add("授课老师性别");
			header.add("授课老师职称");
			/**
			 * 创建具体信息
			 */
			List<List<String>> data = new ArrayList<List<String>>();
			// 只查询有效用户的密码
			List<LessonVo> lessonList = lessonService.findLessonVoByList();
			if (lessonList.size() <= 0) {
				return resultMap.fail().info("数据库中未存在有效课程数据。");
			}
			// 循环遍历插入data数据列中
			for (LessonVo lessonVo : lessonList) {
				List<String> row = new ArrayList<String>();
				row.add(lessonVo.getLno());
				row.add(lessonVo.getLname());
				row.add(lessonVo.getLcredit());
				row.add(lessonVo.getTname());
				row.add(lessonVo.getTsex());
				row.add(lessonVo.getTtitle());
				data.add(row);
			}
			// 输出excel
			ExcelFile excelFile = new ExcelFile(title, header, data);
			excelFile.save(request, response, excelName);
			resultMap.success().info("导出成功");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.fail().info("导出失败");
		}
		return resultMap;
	}
}
