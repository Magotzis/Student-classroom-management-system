package com.magotzis.mapper;

import org.springframework.stereotype.Repository;

import com.magotzis.po.Student;
import com.magotzis.vo.UserLoginVo;

@Repository
public interface StudentMapper extends BaseMapper<Student, String> {

	Student login(UserLoginVo userLoginVo);

}
