package com.nandan.user.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nandan.user.service.UserService;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;

	 @Bean
	    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	        http
	        	.csrf().disable()
	        	 .authorizeHttpRequests()
	        	 .requestMatchers("api/user/**")
	        	 .permitAll()
	        	 .anyRequest()
	        	 .authenticated().and()
	        	 .formLogin();
	        	
	        return http.build();
	    }
	 
	    protected void configure(AuthenticationManagerBuilder auth) {
	        auth.authenticationProvider(daoAuthenticationProvider());
	    }
	 
	 @Bean
	 public DaoAuthenticationProvider daoAuthenticationProvider() {
		 DaoAuthenticationProvider provider = 
				 new DaoAuthenticationProvider();
		 provider.setPasswordEncoder(encoder);
		 provider.setUserDetailsService(userService);
		 return provider;
	 }
	 
	 
}
