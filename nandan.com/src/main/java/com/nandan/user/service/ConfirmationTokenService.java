package com.nandan.user.service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nandan.user.ConfirmationToken;
import com.nandan.user.User;
import com.nandan.user.repository.ConfirmationTokenRepository;
import com.nandan.user.repository.UserRepository;

@Service
public class ConfirmationTokenService {

	@Autowired
	private ConfirmationTokenRepository confirmationTokenRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	public void saveConfirmationToken(ConfirmationToken confirmationToken) {
		confirmationTokenRepository.save(confirmationToken);
		
	}
	public String confirmToken(String token) {
		ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token);
		
		if(confirmationToken!=null) {
			LocalDateTime date1 = confirmationToken.getExpiredAt();
			LocalDateTime date2 = LocalDateTime.now();
			Duration duration = Duration.between(date1, date2);
			System.out.println(duration.toMinutes() % 60);
			if(duration.toMinutes() % 60>=15) {
				throw new IllegalStateException("token expired");
			}
			confirmationToken.setConfirmedAt(LocalDateTime.now());
			User user = userRepository.findById(confirmationToken.getUser().getId()).get();
			user.setEnabled(true);
			user.setLocked(false);
			userRepository.save(user);
			confirmationTokenRepository.save(confirmationToken);
		}
		
		return "confirmed";
	}

	
	
}
