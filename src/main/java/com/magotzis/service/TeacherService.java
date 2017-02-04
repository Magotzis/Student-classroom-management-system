package com.magotzis.service;

import java.util.List;

import com.magotzis.po.Teacher;
import com.magotzis.vo.UserLoginVo;

public interface TeacherService extends BaseService<Teacher, String> {

	List<Teacher> getTeacherByList();

	Teacher login(UserLoginVo userLoginVo);


}
