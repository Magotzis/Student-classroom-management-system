package com.magotzis.mapper;

import org.springframework.stereotype.Repository;

import com.magotzis.po.Admin;
import com.magotzis.vo.UserLoginVo;

@Repository
public interface AdminMapper extends BaseMapper<Admin, String> {

	Admin login(UserLoginVo userLoginVo);

	Admin getUserByUserName(String username);

}
