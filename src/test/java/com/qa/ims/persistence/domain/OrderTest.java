package com.qa.ims.persistence.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;

public class OrderTest {
	
	@Before
	public void testEmptyConstructor() {
		Order order = new Order();
		assertNotNull(order);
	}
	
	@Test
	public void testSmallConstructor() {
		Customer customer = new Customer("Hugh", "Mann");
		Order order = new Order(customer);
		assertEquals(customer, order.getCustomer());
		}
	
	@Test
	public void testBigConstructor() {
		Customer customer = new Customer("Hugh", "Mann");
		Order order = new Order(3L, customer);
		assertEquals(Long.valueOf(3), order.getId());
		assertEquals(customer, order.getCustomer());
		}
	
	@Test
	public void testBiggerConstructor() {
		Customer customer = new Customer("Hugh", "Mann");
		Item item1 = new Item("Milk", 0.84);
		Item item2 = new Item(3L, "Eggs", 1.45);
		List<Item> itemList = new ArrayList<>();
		itemList.add(item1);
		itemList.add(item2);
		double cost = item1.getValue() + item2.getValue();
		Order order = new Order(3L, customer, itemList, cost);
		
		assertEquals(Long.valueOf(3), order.getId());
		assertEquals(customer, order.getCustomer());
		assertEquals(itemList, order.getItems());
		assertEquals(cost, order.getCost(), 0.01);
	}
	
	@Test
	public void testString() {
		Customer customer = new Customer(3L, "Hugh", "Mann");
		Item item1 = new Item("Milk", 0.84);
		Item item2 = new Item(3L, "Eggs", 1.45);
		List<Item> itemList = new ArrayList<>();
		itemList.add(item1);
		itemList.add(item2);
		double cost = item1.getValue() + item2.getValue();
		Order order = new Order(3L, customer, itemList, cost);
		
		assertEquals(
			"id=3, customer=[id=3, first name=Hugh, surname=Mann], items=Milk, Eggs, cost=2.29",
			order.toString()
		);
	}
	
	
	@Test
	public void testEquals() {
		EqualsVerifier.simple().forClass(Order.class).verify();
	}
	
}
