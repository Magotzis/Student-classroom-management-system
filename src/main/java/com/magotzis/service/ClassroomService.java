package com.magotzis.service;

import java.util.List;

import com.magotzis.mybatis.page.Page;
import com.magotzis.po.Classroom;
import com.magotzis.vo.FreeClassroomVo;

public interface ClassroomService extends BaseService<Classroom, String> {

	public Page<Classroom> findClassroomByPage(Classroom classroom);

	public int batchUpdateClassroom(List<Classroom> list);

	public Classroom findClassroomByCno(String cno);

	public Page<Classroom> findFreeClassroomByPage(FreeClassroomVo classroom);

	public List<Classroom> findClassroomByList();

}
