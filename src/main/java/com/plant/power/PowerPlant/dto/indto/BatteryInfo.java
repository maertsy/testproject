package com.plant.power.PowerPlant.dto.indto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.plant.power.PowerPlant.common.Messages;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BatteryInfo {
	@NotBlank(message = Messages.ERROR_FIELD_MISSING)
	@Size(max = 255, message = Messages.ERROR_FIELD_INVALID)
	private String name;

	// We should check the format of the postcode, but it gets complicated
	// because we don't have a list of supported countries
//	@Pattern(regexp = "^([A-Z][A-HJ-Y]?\\d[A-Z\\d]? ?\\d[A-Z]{2}|GIR ?0A{2})$")
	@NotBlank(message = Messages.ERROR_FIELD_MISSING)
	@Size(max = 16, message = Messages.ERROR_FIELD_INVALID)
	private String postcode;

	@NotNull(message = Messages.ERROR_FIELD_MISSING)
	@Min(message = Messages.ERROR_FIELD_INVALID, value = 0)
	private Float capacity;
}