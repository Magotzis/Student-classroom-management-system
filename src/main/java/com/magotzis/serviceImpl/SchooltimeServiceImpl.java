package com.magotzis.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magotzis.mapper.SchooltimeMapper;
import com.magotzis.mybatis.page.Page;
import com.magotzis.po.Schooltime;
import com.magotzis.service.SchooltimeService;
import com.magotzis.vo.StudentApprovedVo;
import com.magotzis.vo.TeacherApprovedVo;

@Service
public class SchooltimeServiceImpl extends BaseServiceImpl<Schooltime, String>implements SchooltimeService {

	@Autowired
	private SchooltimeMapper schooltimeMapper;

	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(schooltimeMapper);
	}

	@Override
	public Page<Schooltime> findApplyByPage(String id) {
		return buildPage(schooltimeMapper.findApplyByPage(id));
	}

	@Override
	public Page<StudentApprovedVo> findStudentApprovedByPage(String apply) {
		return buildVoPage(schooltimeMapper.findStudentApprovedByPage(apply));
	}

	@Override
	public Page<TeacherApprovedVo> findTeacherApprovedByPage(String apply) {
		return buildVoPage(schooltimeMapper.findTeacherApprovedByPage(apply));
	}

}
