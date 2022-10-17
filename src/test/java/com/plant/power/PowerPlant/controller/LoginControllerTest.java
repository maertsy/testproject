package com.plant.power.PowerPlant.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Iterator;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.plant.power.PowerPlant.common.Messages;
import com.plant.power.PowerPlant.dto.indto.LoginInDto;
import com.plant.power.PowerPlant.service.interfaces.LoginService;

class LoginControllerTest {
	private LoginService loginService = Mockito.mock(LoginService.class);
	private LoginController controller = new LoginController(loginService);

	private Validator validator;

	@BeforeEach
	public void setUpBeforeClass() {

		// setup
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		validator = validatorFactory.getValidator();
	}

	@Test
	void testCreateToken() {
		LoginInDto inDto = new LoginInDto();
		inDto.setUsername("user");
		inDto.setPassword("user");
		controller.createToken(inDto);

		verify(loginService, times(1)).createToken(inDto);
	}

	@Test
	void testCreateTokenEmptyUser() {
		LoginInDto inDto = new LoginInDto();
		inDto.setUsername("");
		inDto.setPassword("user");

		// run the test
		Set<ConstraintViolation<LoginInDto>> violations = validator.validate(inDto);

		for (Iterator<ConstraintViolation<LoginInDto>> it = violations.iterator(); it.hasNext();) {
			ConstraintViolation<LoginInDto> violation = it.next();
			assertEquals(violation.getMessage(), Messages.ERROR_FIELD_MISSING);
			assertEquals(violation.getPropertyPath().toString(), "username");
		}
	}

	@Test
	void testCreateTokenNullUser() {
		LoginInDto inDto = new LoginInDto();
		inDto.setUsername(null);
		inDto.setPassword("user");

		// run the test
		Set<ConstraintViolation<LoginInDto>> violations = validator.validate(inDto);

		for (Iterator<ConstraintViolation<LoginInDto>> it = violations.iterator(); it.hasNext();) {
			ConstraintViolation<LoginInDto> violation = it.next();
			assertEquals(violation.getMessage(), Messages.ERROR_FIELD_MISSING);
			assertEquals(violation.getPropertyPath().toString(), "username");
		}
	}

	@Test
	void testCreateTokenEmptyPassword() {
		LoginInDto inDto = new LoginInDto();
		inDto.setUsername("user");
		inDto.setPassword("");

		// run the test
		Set<ConstraintViolation<LoginInDto>> violations = validator.validate(inDto);

		for (Iterator<ConstraintViolation<LoginInDto>> it = violations.iterator(); it.hasNext();) {
			ConstraintViolation<LoginInDto> violation = it.next();
			assertEquals(violation.getMessage(), Messages.ERROR_FIELD_MISSING);
			assertEquals(violation.getPropertyPath().toString(), "password");
		}
	}

	@Test
	void testCreateTokenNullPassword() {
		LoginInDto inDto = new LoginInDto();
		inDto.setUsername("user");
		inDto.setPassword(null);

		// run the test
		Set<ConstraintViolation<LoginInDto>> violations = validator.validate(inDto);

		for (Iterator<ConstraintViolation<LoginInDto>> it = violations.iterator(); it.hasNext();) {
			ConstraintViolation<LoginInDto> violation = it.next();
			assertEquals(violation.getMessage(), Messages.ERROR_FIELD_MISSING);
			assertEquals(violation.getPropertyPath().toString(), "password");
		}
	}

}
