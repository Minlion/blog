package net.limingliang.ioc.user.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import net.limingliang.ioc.user.dao.UserDao;
import net.limingliang.ioc.user.dto.User;
import net.limingliang.ioc.user.service.UserService;

@Service("userService")
public class UserServiceImpl implements UserService{
	
	@Resource
	private UserDao userDao;

	@Override
	public User selectByPrimaryKey(Long id) {
		return userDao.selectByPrimaryKey(id);
	}

	@Override
	public User checkUser(User user) {
		return userDao.checkUser(user);
	}

}
