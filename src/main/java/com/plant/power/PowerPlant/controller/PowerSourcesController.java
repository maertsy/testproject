package com.plant.power.PowerPlant.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.plant.power.PowerPlant.dto.indto.BatteryInDto;
import com.plant.power.PowerPlant.dto.outdto.BaseOutDto;
import com.plant.power.PowerPlant.service.interfaces.BatteryService;

/**
 * Handle end point for power sources service
 *
 */
@RestController
public class PowerSourcesController {
	private BatteryService batteryService;

	@Autowired
	/**
	 * @param batteryService
	 */
	public PowerSourcesController(BatteryService batteryService) {
		this.batteryService = batteryService;
	}

	/**
	 * Add new batteries
	 * 
	 * @param inDto
	 * @return
	 */
	@PostMapping("/addBattery")
	public BaseOutDto addBattery(@Valid @RequestBody BatteryInDto inDto) {
		return batteryService.addBattery(inDto);
	}

	/**
	 * Search battery base on postcode range
	 * 
	 * @param inDto
	 * @return
	 */
	@GetMapping("/search")
	public BaseOutDto searchBattery(@RequestParam(required = false) String start,
			@RequestParam(required = false) String end) {
		return batteryService.search(start, end);
	}
}
