package com.magotzis.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.magotzis.mapper.AdminMapper;
import com.magotzis.po.Admin;
import com.magotzis.service.AdminService;
import com.magotzis.vo.UserLoginVo;

@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin, String>implements AdminService {

	@Autowired
	private AdminMapper adminMapper;

	@Autowired
	public void setBaseMapper() {
		super.setBaseMapper(adminMapper);
	}

	@Override
	public Admin login(UserLoginVo userLoginVo) {
		return adminMapper.login(userLoginVo);
	}

	@Override
	public Admin getUserByUserName(String username) {
		return adminMapper.getUserByUserName(username);
	}

}
