package com.telusko.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableOAuth2Sso
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private UserDetailsService userdetailService;    // bcz controller talk to service so autowire kiya
	

	/*
	 * @Bean
	 * 
	 * @Override protected UserDetailsService userDetailsService() {
	 * 
	 * List<UserDetails> users = new ArrayList<>();
	 * users.add(User.withDefaultPasswordEncoder().username("jyoti").password(
	 * "jyoti123").roles("user").build()); return new
	 * InMemoryUserDetailsManager(users); }
	 */
	
	
	@Bean
	public AuthenticationProvider authProvider() {
		
		DaoAuthenticationProvider provider =new DaoAuthenticationProvider();    // this class is used for connection to database
		
		provider.setUserDetailsService(userdetailService);
		//provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
		provider.setPasswordEncoder(new BCryptPasswordEncoder());      // jb hme bcrypt use krna h tb ye use krege
		return provider;
		
	}

	/*
	 * @Override protected void configure(HttpSecurity http) throws Exception { //
	 * http. csrf().disable(). //for disabling csrf supporta
	 * authorizeRequests().antMatchers("/login").permitAll().
	 * anyRequest().authenticated(). and() .formLogin().
	 * loginPage("/login").permitAll() // for login page and()
	 * .and().logout().invalidateHttpSession(true).clearAuthentication(true).
	 * logoutRequestMatcher(new AntPathRequestMatcher("/logout")).
	 * logoutSuccessUrl("/logout-success") .permitAll();.k
	 * 
	 * }
	 */	 	 
	@Override 
	protected void configure(HttpSecurity http) throws Exception { //
	  http. csrf().disable(). 									//for disabling csrf support
	  authorizeRequests().antMatchers("/login").permitAll().
	  anyRequest().authenticated();
	 
	} 

	
}
