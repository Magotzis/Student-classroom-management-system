package com.magotzis.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.magotzis.po.Lesson;
import com.magotzis.vo.ArrangementVo;
import com.magotzis.vo.LessonVo;

@Repository
public interface LessonMapper extends BaseMapper<Lesson, String> {

	List<LessonVo> findLessonByPage(Lesson lesson);

	int batchUpdateLesson(List<Lesson> list);

	Lesson findLessonByLno(String lno);

	LessonVo findLessonById(String id);

	List<Lesson> findLessonByList(String tno);

	List<ArrangementVo> findArrangementByPage(String lno);

	List<LessonVo> findLessonVoByList();

}
