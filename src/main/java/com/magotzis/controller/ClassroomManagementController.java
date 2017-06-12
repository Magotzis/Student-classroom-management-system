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

import com.magotzis.po.Classroom;
import com.magotzis.service.ClassroomService;
import com.magotzis.util.ExcelFile;
import com.magotzis.util.ResultMap;
import com.magotzis.vo.FreeClassroomVo;

@Controller
@RequestMapping("classroom")
public class ClassroomManagementController {

	@Autowired
	private ClassroomService classroomService;

	/**
	 * 分页查找教室
	 * @param classroom
	 * @return
	 */
	@RequestMapping("findClassroomByPage")
	@ResponseBody
	public ResultMap findClassroomByPage(Classroom classroom) {
		ResultMap resultMap = new ResultMap();
		return resultMap.page(classroomService.findClassroomByPage(classroom));
	}
	
	/**
	 * 查找空闲教室
	 * @param classroom
	 * @return
	 */
	@RequestMapping("findFreeClassroomByPage")
	@ResponseBody
	public ResultMap findFreeClassroomByPage(FreeClassroomVo classroom) {
		ResultMap resultMap = new ResultMap();
		return resultMap.page(classroomService.findFreeClassroomByPage(classroom));
	}
	
	/**
	 * 通过id找教室
	 * @param id
	 * @return
	 */
	@RequestMapping("findClassroomById")
	@ResponseBody
	public ResultMap findClassroomById(String id) {
		ResultMap resultMap = new ResultMap();
		Classroom classroom = classroomService.getById(id);
		if (classroom == null) {
			return resultMap.info("该数据不存在");
		}
		return resultMap.data(classroom);
	}


	/**
	 * 删除课室
	 * 
	 * @param ids
	 *            需要删除的id串
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/deleteClassroom")
	public ResultMap deleteClassroom(String ids) {
		ResultMap resultMap = new ResultMap();
		try {
			String arrId[] = ids.split(",");
			List<Classroom> list = new ArrayList<Classroom>();
			// 循环操作
			for (String id : arrId) {
				Classroom bean = classroomService.getById(id);
				bean.setState("F");
				list.add(bean);
			}
			// 批量更新
			classroomService.batchUpdateClassroom(list);
			resultMap.success().info("删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.fail().info("删除失败");
		}
		return resultMap;
	}

	
	/**
	 * 增加或编辑课室
	 * 
	 * @param classroom
	 *            课室信息
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/addOrUpdateClassroom")
	public ResultMap addOrUpdateClassroom(Classroom classroom) {
		ResultMap resultMap = new ResultMap();
		try {
			Classroom bean = classroomService.getById(classroom.getId());
			if (bean != null) {
				// 修改
				classroomService.update(classroom);
				resultMap.info("修改成功！");
			} else {
				//新增
				Classroom add = classroomService.findClassroomByCno(classroom
						.getCno());
				if (add != null) {
					resultMap.info("该课室已存在！");
				} else {
					// 插入
					classroom.setState("T");
					classroomService.add(classroom);
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
	 * 导出教室信息
	 */
	@ResponseBody
	@RequestMapping("exportClassroomExcel")
	public ResultMap exportClassroomExcel(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		ResultMap resultMap = new ResultMap();
		try {
			String title = new String("sheet 1");
			String excelName = new String("教室一览表");
			// 创建表头信息
			List<String> header = new ArrayList<String>();
			header.add("教室名称");
			header.add("座位数");
			header.add("教室设备");
			header.add("教室地址");
			/**
			 * 创建具体信息
			 */
			List<List<String>> data = new ArrayList<List<String>>();
			// 只查询有效用户的密码
			List<Classroom> classroomList = classroomService.findClassroomByList();
			if (classroomList.size() <= 0) {
				return resultMap.fail().info("数据库中未存在有效课程数据。");
			}
			// 循环遍历插入data数据列中
			for (Classroom classroom : classroomList) {
				List<String> row = new ArrayList<String>();
				row.add(classroom.getCno());
				row.add(String.valueOf(classroom.getCnumber()));
				row.add(classroom.getCdevice());
				row.add(classroom.getCplace());
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
