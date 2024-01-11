package com.ind.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.ind.entity.UserJWT;
import com.ind.repo.UserRepo;

@Service
public class UserServiceImpl implements UserService , UserDetailsService {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Autowired
	private UserRepo usrRepo;

	@Override
	public Integer saveUser(UserJWT user) {

		String pwd = passwordEncoder.encode(user.getPassword());
		user.setPassword(pwd);

		return usrRepo.save(user).getId();
	}

	@Override
	public UserJWT findByUserName(String userName) {

		Optional<UserJWT> opt = usrRepo.findByUserName(userName);

		if (opt.isPresent())
			return opt.get();
		return null;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		UserJWT user = findByUserName(username);
		if(user == null) {
			throw new UsernameNotFoundException(username + "not found");
		}
		
		List<SimpleGrantedAuthority> lists = user.getRoles().stream().map(roles-> new SimpleGrantedAuthority(roles)).collect(Collectors.toList());
		return new User(username, user.getPassword(), lists);
	}

}
