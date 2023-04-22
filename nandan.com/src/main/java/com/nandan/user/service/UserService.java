package com.nandan.user.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nandan.user.ConfirmationToken;
import com.nandan.user.User;
import com.nandan.user.repository.ConfirmationTokenRepository;
import com.nandan.user.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private ConfirmationTokenService confirmationTokenService;
	
	private final static String USER_NOT_FOUND="user with email %s not found";
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findByEmail(username)
				.orElseThrow(()->new UsernameNotFoundException(String.format(USER_NOT_FOUND, username)));
	}
	
	public String signUpUser(User user) {
		boolean userExists = userRepository.findByEmail(user.getEmail()).isPresent();
		if(userExists) {
			throw new IllegalStateException("user allready exists");
		}
		
		String encodedPassword = bCryptPasswordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		userRepository.save(user);
		
		String token = UUID.randomUUID().toString();
		ConfirmationToken confirmationToken = new ConfirmationToken(token,
				LocalDateTime.now(), 
				LocalDateTime.now().plusMinutes(15), 
				user);
		
		confirmationTokenService.saveConfirmationToken(confirmationToken);
		
		return confirmationToken.getToken();
	}

}
