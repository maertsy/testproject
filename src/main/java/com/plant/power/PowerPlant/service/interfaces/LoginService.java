package com.plant.power.PowerPlant.service.interfaces;

import com.plant.power.PowerPlant.dto.indto.LoginInDto;
import com.plant.power.PowerPlant.dto.outdto.BaseOutDto;

public interface LoginService {
	public BaseOutDto createToken(LoginInDto inDto);
}
