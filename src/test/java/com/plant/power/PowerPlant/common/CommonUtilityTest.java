package com.plant.power.PowerPlant.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CommonUtilityTest {

	@Test
	void testIsNull() {
		String nullStr = null;

		assertEquals(CommonUtility.isNullOrBlank(nullStr), true);
	}

	@Test
	void testIsEmpty() {
		String emptyStr = "";

		assertEquals(CommonUtility.isNullOrBlank(emptyStr), true);
	}

	@Test
	void testIsBlank() {
		String blankStr = " 	";

		assertEquals(CommonUtility.isNullOrBlank(blankStr), true);
	}

	@Test
	void testIsNotBlank() {
		String str = "abc";

		assertEquals(CommonUtility.isNullOrBlank(str), false);
	}

}
