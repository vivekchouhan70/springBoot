package com.javatechie.jwt.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.javatechie.jwt.api.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	
	public User findByUserName(String userName);
}
