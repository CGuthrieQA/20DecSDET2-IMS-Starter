package com.qa.ims.persistence.domain;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class ItemTest {
	
	@Before
	public void testSmallConstructor() {
		Item item = new Item("Milk", 0.84);
		assertEquals("Milk", item.getName());
		assertEquals(Double.valueOf(0.84), item.getValue(), 0.01);
	}
	
	@Test
	public void testBigConstructor() {
		Item item = new Item(3L, "Eggs", 1.45);
		assertEquals(Long.valueOf(3), item.getId());
		assertEquals("Eggs", item.getName());
		assertEquals(Double.valueOf(1.45), item.getValue(), 0.01);
	}
	
	@Test
	public void testString() {
		Item item = new Item(3L, "Eggs", 1.45);
		assertEquals(
			"id:3 name:Eggs value:1.45",
			item.toString()
		);
	}
	
	
	@Test
	public void testEquals() {
		EqualsVerifier.simple().forClass(Item.class).verify();
	}
	
}
