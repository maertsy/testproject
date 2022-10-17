package com.plant.power.PowerPlant.service.impl;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.plant.power.PowerPlant.common.CommonUtility;
import com.plant.power.PowerPlant.common.Constant;
import com.plant.power.PowerPlant.common.Messages;
import com.plant.power.PowerPlant.database.entity.Battery;
import com.plant.power.PowerPlant.database.repository.BatteryRepository;
import com.plant.power.PowerPlant.dto.indto.BatteryInDto;
import com.plant.power.PowerPlant.dto.outdto.BaseOutDto;
import com.plant.power.PowerPlant.dto.outdto.BatteryListOutDto;
import com.plant.power.PowerPlant.service.interfaces.BatteryService;

/**
 * Contains logic for the battery service
 *
 */
@Service
public class BatteryServiceImpl implements BatteryService {
	private static final Logger logger = LoggerFactory.getLogger(BatteryServiceImpl.class);

	private MessageSource messageSource;

	private BatteryRepository batteryRepository;

	/**
	 * @param batteryRepository
	 */
	@Autowired
	public BatteryServiceImpl(BatteryRepository batteryRepository, MessageSource messageSource) {
		super();
		this.batteryRepository = batteryRepository;
		this.messageSource = messageSource;
	}

	/**
	 * Add new battery to the system
	 */
	@Override
	public BaseOutDto addBattery(BatteryInDto inDto) {
		logger.debug("Start addBattery");
		BaseOutDto outDto = new BaseOutDto();

		try {
			// We should have something like barcode to identify the battery. In this case I
			// will check duplicate name
			List<String> invalidNameList = inDto.getBatteries().stream()
					.filter(obj -> batteryRepository.findByName(obj.getName()).isPresent()).map(obj -> obj.getName())
					.toList();

			if (invalidNameList != null && !invalidNameList.isEmpty()) {
				// battery with the same name already exist
				outDto.setErrorCode(Constant.RETURN_CODE_VALIDATION);
				outDto.setMessages(Arrays.asList(messageSource.getMessage(Messages.ERROR_NAME_EXIST,
						new String[] { invalidNameList.toString() }, null)));
				return outDto;
			}

			inDto.getBatteries().stream().forEach(inputBattery -> {
				// create new entity
				Battery battery = new Battery();
				battery.setName(inputBattery.getName().trim());
				battery.setPostcode(inputBattery.getPostcode().trim());
				battery.setCapacity(inputBattery.getCapacity());

				// save data
				batteryRepository.save(battery);
			});

			outDto.setErrorCode(Constant.RETURN_CODE_OK);
		} catch (Exception e) {
			logger.error(messageSource.getMessage(Messages.ERROR_UNEXPECTED, null, null), e);
			// handle exception
			outDto.setErrorCode(Constant.RETURN_CODE_UNEXPECTED_ERROR);

			outDto.setMessages(Arrays.asList(messageSource.getMessage(Messages.ERROR_UNEXPECTED, null, null)));
		} finally {
			logger.debug("End addBattery");
		}

		return outDto;
	}

	/**
	 * Receive list of battery base on postcode range
	 */
	@Override
	public BaseOutDto search(String start, String end) {
		BatteryListOutDto outDto = new BatteryListOutDto();
		try {
			// check post code
			boolean isStartBlank = CommonUtility.isNullOrBlank(start);
			boolean isEndBlank = CommonUtility.isNullOrBlank(end);

			// if we have a lot of data, we should do pagination
			// Pageable page = PageRequest.of(pageNumber, itemPerPage);
			List<Battery> batteryList = null;
			if (isStartBlank && isEndBlank) {
				batteryList = batteryRepository.findAll(Sort.by("name"));
			} else if (isStartBlank) {
				batteryList = batteryRepository.findAllByPostcodeLessThanEqualOrderByNameAsc(end);
			} else if (isEndBlank) {
				batteryList = batteryRepository.findAllByPostcodeGreaterThanEqualOrderByNameAsc(start);
			} else {
				batteryList = batteryRepository.findAllByPostcodeGreaterThanEqualAndPostcodeLessThanEqualOrderByNameAsc(
						start, end);
			}

			if (batteryList == null || batteryList.isEmpty()) {
				outDto.setErrorCode(Constant.RETURN_CODE_NO_DATA);
				outDto.setMessages(Arrays.asList(messageSource.getMessage(Messages.ERROR_NO_DATA, null, null)));
				return outDto;
			}

			// get battery list by post code
			List<String> batteryNameList = null;
			float totalCapacity = 0f;
			float averageCapacity = 0f;

			if (batteryList != null && !batteryList.isEmpty()) {
				// calculate total capacity
				totalCapacity = batteryList.stream().map(batteryObj -> batteryObj.getCapacity()).reduce(0f,
						(a, b) -> a + b);

				// calculate average capacity. Because we already check battery list is not
				// empty, so we can safely divide by list size
				averageCapacity = totalCapacity / batteryList.size();
				batteryNameList = batteryList.stream().map(entity -> entity.getName()).toList();
			}

			outDto.setBatteryList(batteryNameList);
			outDto.setAverageCapacity(averageCapacity);
			outDto.setTotalCapacity(totalCapacity);
			outDto.setErrorCode(Constant.RETURN_CODE_OK);
		} catch (Exception e) {
			logger.error(messageSource.getMessage(Messages.ERROR_UNEXPECTED, null, null), e);
			// handle exception
			outDto.setErrorCode(Constant.RETURN_CODE_UNEXPECTED_ERROR);

			outDto.setMessages(Arrays.asList(messageSource.getMessage(Messages.ERROR_UNEXPECTED, null, null)));
		} finally {
			logger.debug("End addBattery");
		}
		return outDto;

	}

}
