package com.plant.power.PowerPlant.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.plant.power.PowerPlant.dto.indto.LoginInDto;
import com.plant.power.PowerPlant.dto.outdto.BaseOutDto;
import com.plant.power.PowerPlant.service.interfaces.LoginService;

/**
 * Handle end point for login
 *
 */
@RestController
public class LoginController {
	private LoginService loginService;

	@Autowired
	public LoginController(LoginService loginService) {
		super();
		this.loginService = loginService;
	}

	/**
	 * Perform login and return token on success
	 * @param inDto
	 * @return
	 */
	@PostMapping("/login")
	public BaseOutDto createToken(@RequestBody LoginInDto inDto) {
		return loginService.createToken(inDto);
	}
}
