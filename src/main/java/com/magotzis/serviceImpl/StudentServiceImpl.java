package com.magotzis.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magotzis.mapper.StudentMapper;
import com.magotzis.po.Student;
import com.magotzis.service.StudentService;
import com.magotzis.vo.UserLoginVo;

@Service
public class StudentServiceImpl extends BaseServiceImpl<Student, String>implements StudentService {

	@Autowired
	private StudentMapper studentMapper;

	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(studentMapper);
	}

	@Override
	public Student login(UserLoginVo userLoginVo) {
		return studentMapper.login(userLoginVo);
	}

	@Override
	public Student getByUserName(String sno) {
		return studentMapper.getByUserName(sno);
	}

}
