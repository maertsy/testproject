package com.plant.power.PowerPlant.service.interfaces;

import com.plant.power.PowerPlant.dto.indto.BatteryInDto;
import com.plant.power.PowerPlant.dto.outdto.BaseOutDto;

public interface BatteryService {
	public BaseOutDto addBattery(BatteryInDto inDto);
	public BaseOutDto search(String start, String end);
}
