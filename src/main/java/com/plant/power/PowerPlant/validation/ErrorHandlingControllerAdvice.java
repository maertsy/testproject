package com.plant.power.PowerPlant.validation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.plant.power.PowerPlant.common.Constant;
import com.plant.power.PowerPlant.common.Messages;
import com.plant.power.PowerPlant.dto.outdto.BaseOutDto;

/**
 * Handle validation exception
 *
 */
@ControllerAdvice
public class ErrorHandlingControllerAdvice {
	@Autowired
	private MessageSource messageSource;

	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public BaseOutDto onHttpMessageNotReadableException(HttpMessageNotReadableException ex) {
		BaseOutDto retDto = new BaseOutDto();
		List<String> errorList = new ArrayList<>();
		retDto.setMessages(errorList);
		retDto.setErrorCode(Constant.RETURN_CODE_UNEXPECTED_ERROR);
		errorList.add(messageSource.getMessage(Messages.ERROR_BODY_INVALID, null, null));

		return retDto;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public BaseOutDto onMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		BaseOutDto retDto = new BaseOutDto();
		List<String> errorList = new ArrayList<>();
		retDto.setMessages(errorList);
		retDto.setErrorCode(Constant.RETURN_CODE_VALIDATION);

		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String fieldName = ((FieldError) error).getField();
			String errorMessage = error.getDefaultMessage();
			errorList.add(messageSource.getMessage(errorMessage, new String[] { fieldName }, null));
		});

		return retDto;
	}
}
