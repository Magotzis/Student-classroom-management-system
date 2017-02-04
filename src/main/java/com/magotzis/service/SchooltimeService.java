package com.magotzis.service;

import com.magotzis.mybatis.page.Page;
import com.magotzis.po.Schooltime;
import com.magotzis.vo.StudentApprovedVo;
import com.magotzis.vo.TeacherApprovedVo;

public interface SchooltimeService extends BaseService<Schooltime, String> {

	Page<Schooltime> findApplyByPage(String id);

	Page<StudentApprovedVo> findStudentApprovedByPage(String apply);

	Page<TeacherApprovedVo> findTeacherApprovedByPage(String apply);

}
