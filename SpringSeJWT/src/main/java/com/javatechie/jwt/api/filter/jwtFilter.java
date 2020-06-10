package com.javatechie.jwt.api.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.javatechie.jwt.api.service.CustomUserDetailService;
import com.javatechie.jwt.api.util.jwtUtil;

@Component
public class jwtFilter extends OncePerRequestFilter {

	@Autowired
	private jwtUtil jwtUtil;
	
	@Autowired
	private CustomUserDetailService service;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

	String authorizatinHeader	=request.getHeader("Authorization");
	String token=null;
	String username=null;
	//Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ2aXZlayIsImV4cCI6MTU5MTY0NDQ1MiwiaWF0IjoxNTkxNjI2NDUyfQ.xg791-S_k1SzFcEhfXO8A5DTtAXCp49SuZz03BrgHDY
	
	if(authorizatinHeader !=null && authorizatinHeader.startsWith("Bearer ")) {
		token=authorizatinHeader.substring(7);
		username=jwtUtil.getUsernameFromToken(token);
	}
		
	// Once we get the token validate it.
	if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
	UserDetails userDetails = service.loadUserByUsername(username);
	// if token is valid configure Spring Security to manually set
	// authentication
	if (jwtUtil.validateToken(token, userDetails)) {
	UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
	userDetails, null, userDetails.getAuthorities());
	usernamePasswordAuthenticationToken
	.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	// After setting the Authentication in the context, we specify
	// that the current user is authenticated. So it passes the
	// Spring Security Configurations successfully.
	SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
	}
	}
	filterChain.doFilter(request, response);
	}

	}

