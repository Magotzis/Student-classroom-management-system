package com.magotzis.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.magotzis.po.Teacher;
import com.magotzis.vo.UserLoginVo;

@Repository
public interface TeacherMapper extends BaseMapper<Teacher, String> {

	List<Teacher> getTeacherByList();

	Teacher login(UserLoginVo userLoginVo);

}
