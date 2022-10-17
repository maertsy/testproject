package com.plant.power.PowerPlant.service.impl;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Service to handle login user
 *
 */
@Service
public class JwtUserDetailsService implements UserDetailsService {

	/**
	 * Check the user exist
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Since this is a test application for the sole purpose of the demonstration
		// Spring, I have resorted to a set of our user details, instead of using a
		// database.
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		
		if ("user".equals(username)) {
			return new User("user", encoder.encode("user"), new ArrayList<>());
		} else {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
	}

}
