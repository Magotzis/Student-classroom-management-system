package com.magotzis.service;

import com.magotzis.po.Student;
import com.magotzis.vo.UserLoginVo;

public interface StudentService extends BaseService<Student, String> {

	Student login(UserLoginVo userLoginVo);


}
