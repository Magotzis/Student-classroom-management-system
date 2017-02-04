package com.magotzis.service;

import java.util.List;

import com.magotzis.mybatis.page.Page;
import com.magotzis.po.Lesson;
import com.magotzis.vo.ArrangementVo;
import com.magotzis.vo.LessonVo;

public interface LessonService extends BaseService<Lesson, String> {

	public Page<LessonVo> findLessonByPage(Lesson lesson);

	public int batchUpdateLesson(List<Lesson> list);

	public Lesson findLessonByLno(String Lno);

	public LessonVo findLessonById(String id);

	public List<Lesson> findLessonByList(String tno);

	public Page<ArrangementVo> findArrangementByPage(String lno);

	public List<LessonVo> findLessonVoByList();

}
