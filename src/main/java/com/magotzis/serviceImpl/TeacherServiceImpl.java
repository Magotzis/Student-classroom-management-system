package com.magotzis.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magotzis.mapper.TeacherMapper;
import com.magotzis.po.Teacher;
import com.magotzis.service.TeacherService;
import com.magotzis.vo.UserLoginVo;

@Service
public class TeacherServiceImpl extends BaseServiceImpl<Teacher, String>implements TeacherService {

	@Autowired
	private TeacherMapper teacherMapper;

	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(teacherMapper);
	}

	@Override
	public List<Teacher> getTeacherByList() {
		return teacherMapper.getTeacherByList();
	}

	@Override
	public Teacher login(UserLoginVo userLoginVo) {
		return teacherMapper.login(userLoginVo);
	}

	@Override
	public Teacher getByUserName(String tno) {
		return teacherMapper.getByUserName(tno);
	}

}
