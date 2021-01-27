package com.qa.ims.persistence.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class CustomerTest {
	
	@Before
	public void testSmallConstructor() {
		Customer customer = new Customer("Hugh", "Mann");
		assertEquals("Hugh", customer.getFirstName());
		assertEquals("Mann", customer.getSurname());
	}
	
	@Test
	public void testBigConstructor() {
		Customer customer = new Customer(3L, "Joe", "Biggs");
		assertEquals(Long.valueOf(3), customer.getId());
		assertEquals("Joe", customer.getFirstName());
		assertEquals("Biggs", customer.getSurname());
	}
	
	@Test
	public void testString() {
		Customer customer = new Customer(3L, "Joe", "Biggs");
		assertEquals(
			"id=3, first name=Joe, surname=Biggs",
			customer.toString()
		);
	}

	@Test
	public void testEquals() {
		EqualsVerifier.simple().forClass(Customer.class).verify();
	}

}
