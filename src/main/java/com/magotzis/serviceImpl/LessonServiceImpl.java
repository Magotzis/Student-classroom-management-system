package com.magotzis.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magotzis.mapper.LessonMapper;
import com.magotzis.mybatis.page.Page;
import com.magotzis.po.Lesson;
import com.magotzis.service.LessonService;
import com.magotzis.vo.ArrangementVo;
import com.magotzis.vo.LessonVo;

@Service
public class LessonServiceImpl extends BaseServiceImpl<Lesson, String>implements LessonService {

	@Autowired
	private LessonMapper lessonMapper;

	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(lessonMapper);
	}

	@Override
	public Page<LessonVo> findLessonByPage(Lesson lesson) {
		return buildVoPage(lessonMapper.findLessonByPage(lesson));
	}

	@Override
	public int batchUpdateLesson(List<Lesson> list) {
		return lessonMapper.batchUpdateLesson(list);
	}

	@Override
	public Lesson findLessonByLno(String lno) {
		return lessonMapper.findLessonByLno(lno);
	}

	@Override
	public LessonVo findLessonById(String id) {
		return lessonMapper.findLessonById(id);
	}

	@Override
	public List<Lesson> findLessonByList(String tno) {
		return lessonMapper.findLessonByList(tno);
	}

	@Override
	public Page<ArrangementVo> findArrangementByPage(String lno) {
		return buildVoPage(lessonMapper.findArrangementByPage(lno));
	}

	@Override
	public List<LessonVo> findLessonVoByList() {
		return lessonMapper.findLessonVoByList();
	}

}
