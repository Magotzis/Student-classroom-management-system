package com.magotzis.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.magotzis.po.Schooltime;
import com.magotzis.vo.StudentApprovedVo;
import com.magotzis.vo.TeacherApprovedVo;

@Repository
public interface SchooltimeMapper extends BaseMapper<Schooltime, String> {

	List<Schooltime> findApplyByPage(String id);

	List<StudentApprovedVo> findStudentApprovedByPage(String apply);

	List<TeacherApprovedVo> findTeacherApprovedByPage(String apply);

}
