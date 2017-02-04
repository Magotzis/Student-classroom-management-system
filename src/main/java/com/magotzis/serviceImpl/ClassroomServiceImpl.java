package com.magotzis.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magotzis.mapper.ClassroomMapper;
import com.magotzis.mybatis.page.Page;
import com.magotzis.po.Classroom;
import com.magotzis.service.ClassroomService;
import com.magotzis.vo.FreeClassroomVo;

@Service
public class ClassroomServiceImpl extends BaseServiceImpl<Classroom, String>implements ClassroomService {

	@Autowired
	private ClassroomMapper classroomMapper;

	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(classroomMapper);
	}

	@Override
	public Page<Classroom> findClassroomByPage(Classroom classroom) {
		return buildPage(classroomMapper.findClassroomByPage(classroom));
	}

	@Override
	public int batchUpdateClassroom(List<Classroom> list) {
		return classroomMapper.batchUpdateClassroom(list);
	}

	@Override
	public Classroom findClassroomByCno(String cno) {
		return classroomMapper.findClassroomByCno(cno);
	}

	@Override
	public Page<Classroom> findFreeClassroomByPage(
			FreeClassroomVo classroom) {
		return buildPage(classroomMapper.findFreeClassroomByPage(classroom));
	}

	@Override
	public List<Classroom> findClassroomByList() {
		return classroomMapper.findClassroomByList();
	}

}
