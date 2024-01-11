package com.ind.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ind.entity.UserJWT;

public interface UserRepo extends JpaRepository<UserJWT, Integer> {

	Optional<UserJWT> findByUserName(String userName);

}
