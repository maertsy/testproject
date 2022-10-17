package com.plant.power.PowerPlant.dto.outdto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BaseOutDto {
	private int errorCode;
	private List<String> messages;
}
