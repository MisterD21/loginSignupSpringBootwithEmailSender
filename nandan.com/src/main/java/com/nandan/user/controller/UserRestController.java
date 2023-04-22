package com.nandan.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nandan.user.dto.RegistrationRequest;
import com.nandan.user.helper.EmailValidator;
import com.nandan.user.service.RegistrationService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "api/user")
@AllArgsConstructor
public class UserRestController {
	
	@Autowired
	private RegistrationService registrationService;

	@PostMapping("/register")
	public String registerUser(@RequestBody RegistrationRequest request) {
		
		return registrationService.register(request);
	}
	
	@GetMapping(path = "/confirm")
	public String confirm(@RequestParam("token") String token) {
		
		return registrationService.confirmToken(token);
		
	}
	
}
