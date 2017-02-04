package com.magotzis.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.magotzis.po.Classroom;
import com.magotzis.vo.FreeClassroomVo;

@Repository
public interface ClassroomMapper extends BaseMapper<Classroom, String> {

	List<Classroom> findClassroomByPage(Classroom classroom);

	int batchUpdateClassroom(List<Classroom> list);

	Classroom findClassroomByCno(String cno);

	List<Classroom> findFreeClassroomByPage(FreeClassroomVo classroom);

	List<Classroom> findClassroomByList();

}
