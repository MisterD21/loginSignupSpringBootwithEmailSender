package com.nandan.user.helper;

import org.springframework.stereotype.Service;

@Service
public class EmailValidator implements java.util.function.Predicate<String> {

	@Override
	public boolean test(String t) {
		// TODO regex to validate email
		return true;
	}

}
