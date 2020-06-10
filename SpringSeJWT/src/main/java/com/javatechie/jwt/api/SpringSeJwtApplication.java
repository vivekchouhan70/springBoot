package com.javatechie.jwt.api;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.javatechie.jwt.api.entity.User;
import com.javatechie.jwt.api.repository.UserRepository;

@SpringBootApplication
public class SpringSeJwtApplication {

	@Autowired
	private UserRepository repo;
	
	@PostConstruct
	public void initUsers() {
		List<User> users=Stream.of(
				new User(101,"vivek","chouhan","vivek@gmail.com"),
				new User(102,"vivek1","chouhan1","vive1k@gmail.com"),
				new User(103,"vivek2","chouhan2","vivek2@gmail.com"),
				new User(104,"vivek3","chouhan3","vivek3@gmail.com")
				).collect(Collectors.toList());
		repo.saveAll(users);
		
	}
	public static void main(String[] args) {
		SpringApplication.run(SpringSeJwtApplication.class, args);
	}

}
