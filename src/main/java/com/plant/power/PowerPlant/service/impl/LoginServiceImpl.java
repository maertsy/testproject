package com.plant.power.PowerPlant.service.impl;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.plant.power.PowerPlant.common.Constant;
import com.plant.power.PowerPlant.common.Messages;
import com.plant.power.PowerPlant.dto.indto.LoginInDto;
import com.plant.power.PowerPlant.dto.outdto.BaseOutDto;
import com.plant.power.PowerPlant.dto.outdto.LoginOutDto;
import com.plant.power.PowerPlant.security.TokenManager;
import com.plant.power.PowerPlant.service.interfaces.LoginService;

/**
 * Handle login process
 *
 */
@Service
public class LoginServiceImpl implements LoginService {
	private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);
	private MessageSource messageSource;

	private JwtUserDetailsService userDetailsService;

	private AuthenticationManager authenticationManager;

	private TokenManager tokenManager;

	@Autowired
	public LoginServiceImpl(MessageSource messageSource, JwtUserDetailsService userDetailsService,
			AuthenticationManager authenticationManager, TokenManager tokenManager) {
		super();
		this.messageSource = messageSource;
		this.userDetailsService = userDetailsService;
		this.authenticationManager = authenticationManager;
		this.tokenManager = tokenManager;
	}

	/**
	 * Validate login credential and return token
	 */
	@Override
	public BaseOutDto createToken(LoginInDto inDto) {
		LoginOutDto outDto = new LoginOutDto();
		try {
			// Validate login credential
			try {
				authenticationManager.authenticate(
						new UsernamePasswordAuthenticationToken(inDto.getUsername(), inDto.getPassword()));
			} catch (Exception e) {
				outDto.setErrorCode(Constant.RETURN_CODE_VALIDATION);
				outDto.setMessages(Arrays.asList(messageSource.getMessage(Messages.ERROR_LOGIN, null, null)));
				return outDto;
			}

			// Create token
			final UserDetails userDetails = userDetailsService.loadUserByUsername(inDto.getUsername());
			final String jwtToken = tokenManager.generateJwtToken(userDetails);

			outDto.setErrorCode(Constant.RETURN_CODE_OK);
			outDto.setToken(jwtToken);
		} catch (Exception e) {
			logger.error(messageSource.getMessage(Messages.ERROR_UNEXPECTED, null, null), e);
			// handle exception
			outDto.setErrorCode(Constant.RETURN_CODE_UNEXPECTED_ERROR);

			outDto.setMessages(Arrays.asList(messageSource.getMessage(Messages.ERROR_UNEXPECTED, null, null)));
		} finally {
			logger.debug("End addBattery");
		}
		return outDto;
	}

}
