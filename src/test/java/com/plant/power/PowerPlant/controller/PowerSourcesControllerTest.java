package com.plant.power.PowerPlant.controller;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.plant.power.PowerPlant.dto.indto.BatteryInDto;
import com.plant.power.PowerPlant.service.interfaces.BatteryService;

class PowerSourcesControllerTest {
	private BatteryService batteryService = Mockito.mock(BatteryService.class);
	private PowerSourcesController controller = new PowerSourcesController(batteryService);

	@Test
	void testAddBattery() {
		BatteryInDto inDto = new BatteryInDto();
		controller.addBattery(inDto);
		verify(batteryService, times(1)).addBattery(inDto);
	}

	@Test
	void testSearchBattery() {
		controller.searchBattery("", "");
		verify(batteryService, times(1)).search("", "");
	}
}
