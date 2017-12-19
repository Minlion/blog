package net.limingliang.ioc.user.service;

import net.limingliang.ioc.user.dto.User;

public interface UserService {
	
	User selectByPrimaryKey(Long id);

	User checkUser(User user);

}
