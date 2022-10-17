package com.plant.power.PowerPlant.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;

import com.plant.power.PowerPlant.common.Constant;
import com.plant.power.PowerPlant.database.entity.Battery;
import com.plant.power.PowerPlant.database.repository.BatteryRepository;
import com.plant.power.PowerPlant.dto.indto.BatteryInDto;
import com.plant.power.PowerPlant.dto.indto.BatteryInfo;
import com.plant.power.PowerPlant.dto.outdto.BaseOutDto;
import com.plant.power.PowerPlant.dto.outdto.BatteryListOutDto;

class BatteryServiceImplTest {

	private MessageSource messageSource = Mockito.mock(MessageSource.class);

	private BatteryRepository batteryRepository = Mockito.mock(BatteryRepository.class);

	public BatteryServiceImpl batteryServiceImpl = new BatteryServiceImpl(batteryRepository, messageSource);

	@Test
	void testAddBattery() {
		BatteryInfo batteryInfo = new BatteryInfo();
		batteryInfo.setCapacity(1.0f);
		batteryInfo.setName("testBattery");
		batteryInfo.setPostcode("6000");

		List<BatteryInfo> batteryInfos = new ArrayList<>();
		batteryInfos.add(batteryInfo);

		BatteryInDto indto = new BatteryInDto();
		indto.setBatteries(batteryInfos);

		given(batteryRepository.findByName(anyString())).willReturn(Optional.empty());

		BaseOutDto outDto = batteryServiceImpl.addBattery(indto);
		verify(batteryRepository, times(1)).save(any());
		assertEquals(outDto.getErrorCode(), Constant.RETURN_CODE_OK);
	}

	@Test
	void testAddBatteryExisted() {
		BatteryInfo batteryInfo = new BatteryInfo();
		batteryInfo.setCapacity(1.0f);
		batteryInfo.setName("testBattery");
		batteryInfo.setPostcode("6000");

		List<BatteryInfo> batteryInfos = new ArrayList<>();
		batteryInfos.add(batteryInfo);

		BatteryInDto indto = new BatteryInDto();
		indto.setBatteries(batteryInfos);

		Battery battery = new Battery();
		battery.setName("testBattery");

		given(batteryRepository.findByName(anyString())).willReturn(Optional.of(battery));

		BaseOutDto outDto = batteryServiceImpl.addBattery(indto);
		verify(batteryRepository, times(0)).save(any());
		assertEquals(outDto.getErrorCode(), Constant.RETURN_CODE_VALIDATION);
	}

	@Test
	void testAddBatteryException() {
		BatteryInfo batteryInfo = new BatteryInfo();
		batteryInfo.setCapacity(1.0f);
		batteryInfo.setName("testBattery");
		batteryInfo.setPostcode("6000");

		List<BatteryInfo> batteryInfos = new ArrayList<>();
		batteryInfos.add(batteryInfo);

		BatteryInDto indto = new BatteryInDto();
		indto.setBatteries(batteryInfos);

		Battery battery = new Battery();
		battery.setName("testBattery");

		given(batteryRepository.findByName(anyString())).willThrow(RuntimeException.class);

		BaseOutDto outDto = batteryServiceImpl.addBattery(indto);
		verify(batteryRepository, times(0)).save(any());
		assertEquals(outDto.getErrorCode(), Constant.RETURN_CODE_UNEXPECTED_ERROR);
	}

	@Test
	void testSearchAll() {
		batteryServiceImpl.search("", "");

		verify(batteryRepository, times(1)).findAll(Sort.by("name"));
		verify(batteryRepository, times(0)).findAllByPostcodeLessThanEqualOrderByNameAsc(any());
		verify(batteryRepository, times(0)).findAllByPostcodeGreaterThanEqualOrderByNameAsc(any());
		verify(batteryRepository, times(0))
				.findAllByPostcodeGreaterThanEqualAndPostcodeLessThanEqualOrderByNameAsc(any(), any());
	}

	@Test
	void testSearchByStart() {
		batteryServiceImpl.search("5000", "");

		verify(batteryRepository, times(0)).findAll(Sort.by("name"));
		verify(batteryRepository, times(0)).findAllByPostcodeLessThanEqualOrderByNameAsc(any());
		verify(batteryRepository, times(1)).findAllByPostcodeGreaterThanEqualOrderByNameAsc(any());
		verify(batteryRepository, times(0))
				.findAllByPostcodeGreaterThanEqualAndPostcodeLessThanEqualOrderByNameAsc(any(), any());
	}

	@Test
	void testSearchByEnd() {
		batteryServiceImpl.search("", "5000");

		verify(batteryRepository, times(0)).findAll(Sort.by("name"));
		verify(batteryRepository, times(1)).findAllByPostcodeLessThanEqualOrderByNameAsc(any());
		verify(batteryRepository, times(0)).findAllByPostcodeGreaterThanEqualOrderByNameAsc(any());
		verify(batteryRepository, times(0))
				.findAllByPostcodeGreaterThanEqualAndPostcodeLessThanEqualOrderByNameAsc(any(), any());
	}

	@Test
	void testSearchByRange() {
		batteryServiceImpl.search("5000", "6000");

		verify(batteryRepository, times(0)).findAll(Sort.by("name"));
		verify(batteryRepository, times(0)).findAllByPostcodeLessThanEqualOrderByNameAsc(any());
		verify(batteryRepository, times(0)).findAllByPostcodeGreaterThanEqualOrderByNameAsc(any());
		verify(batteryRepository, times(1))
				.findAllByPostcodeGreaterThanEqualAndPostcodeLessThanEqualOrderByNameAsc(any(), any());
	}

	@Test
	void testSearchNoData() {
		BatteryListOutDto outDto = (BatteryListOutDto) batteryServiceImpl.search("", "");

		assertEquals(outDto.getErrorCode(), Constant.RETURN_CODE_NO_DATA);
	}

	@Test
	void testSearchHaveData() {
		given(batteryRepository.findByName(anyString())).willThrow(RuntimeException.class);

		Battery battery = new Battery();
		battery.setCapacity(1.0f);
		battery.setName("testBattery");
		battery.setPostcode("6000");

		List<Battery> batteries = new ArrayList<>();
		batteries.add(battery);
		given(batteryRepository.findAll(Sort.by("name"))).willReturn(batteries);
		BatteryListOutDto outDto = (BatteryListOutDto) batteryServiceImpl.search("", "");
		assertEquals(outDto.getErrorCode(), Constant.RETURN_CODE_OK);
	}

}
