package com.ind.service;

import com.ind.entity.UserJWT;

public interface UserService {

	public Integer saveUser(UserJWT user);

	public UserJWT findByUserName(String userName);

}
