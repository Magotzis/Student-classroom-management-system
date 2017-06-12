package com.magotzis.service;

import com.magotzis.po.Admin;
import com.magotzis.vo.UserLoginVo;

public interface AdminService extends BaseService<Admin, String> {

	Admin login(UserLoginVo userLoginVo);

	Admin getUserByUserName(String username);


}
