package com.plant.power.PowerPlant.dto.indto;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import com.plant.power.PowerPlant.common.Messages;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BatteryInDto {
	@NotEmpty(message = Messages.ERROR_FIELD_MISSING)
	@Valid
	private List<BatteryInfo> batteries;
}
