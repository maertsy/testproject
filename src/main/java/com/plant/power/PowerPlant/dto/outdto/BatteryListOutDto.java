package com.plant.power.PowerPlant.dto.outdto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatteryListOutDto extends BaseOutDto {
	private List<String> batteryList;
	private float totalCapacity;
	private float averageCapacity;
}
