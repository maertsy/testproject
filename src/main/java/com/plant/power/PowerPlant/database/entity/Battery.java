package com.plant.power.PowerPlant.database.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * Table store the battery information
 *
 */
@Entity
@Data
@Table(name = "battery")
public class Battery {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;

	/**
	 * Name of the battery
	 */
	private String name;

	/**
	 * Postcode of the battery
	 */
	private String postcode;

	/**
	 * Watt capacity of the battery
	 */
	private Float capacity;
}
