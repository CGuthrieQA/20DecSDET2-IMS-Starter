package com.qa.ims.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.persistence.dao.OrderDao;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.JavaUtilities;

@RunWith(MockitoJUnitRunner.class)
public class OrderControllerTest {

	@Mock
	private JavaUtilities javaUtilities;

	@Mock
	private OrderDao dao;

	@InjectMocks
	private OrderController controller;

	@Test
	public void testCreate() {
		
		final Long CUSTOMER_ID = 1L;
		
		final Customer CUSTOMER = new Customer(1L, "jordan", "harrison");
		
		final Order created = new Order(CUSTOMER);

		Mockito.when(javaUtilities.getLong()).thenReturn(CUSTOMER_ID);
		
		Mockito.when(dao.create(created)).thenReturn(created);

		assertEquals(created, controller.create());

		Mockito.verify(javaUtilities, Mockito.times(1)).getLong();
		Mockito.verify(dao, Mockito.times(1)).create(created);
	}

	@Test
	public void testReadAll() {
		List<Order> orders = new ArrayList<>();
		
		final Customer CUSTOMER = new Customer(1L, "jordan", "harrison");
		final List<Item> ITEM_LIST = new ArrayList<>();
		final Item ITEM_1 = new Item(1L, "spork", 5.60D);
		ITEM_LIST.add(ITEM_1);
		orders.add(new Order(1L, CUSTOMER, ITEM_LIST,5.60D));

		Mockito.when(dao.readAll()).thenReturn(orders);

		assertEquals(orders, controller.readAll());

		Mockito.verify(dao, Mockito.times(1)).readAll();
	}

//	@Test
//	public void testUpdateAdd() {
//		
//		final Customer CUSTOMER = new Customer(1L, "jordan", "harrison");
//		
//		final List<Item> ITEM_LIST = new ArrayList<>();
//		
//		ITEM_LIST.add(new Item(2L, "milk", 0.4));
//		double COST = 0.4;
//		
//		Order updated = new Order(2L, CUSTOMER, ITEM_LIST, COST);
//		
//		String INPUT_ADD = "ADD";
//
//		Mockito.when(this.javaUtilities.getLong()).thenReturn(2L);
//		Mockito.when(this.javaUtilities.getString()).thenReturn(INPUT_ADD);
//		
//		Mockito.when(this.dao.update(updated)).thenReturn(updated);
//		
//		assertEquals(updated, this.controller.update());
//
//		Mockito.verify(this.javaUtilities, Mockito.times(2)).getLong();
//		Mockito.verify(this.javaUtilities, Mockito.times(1)).getString();
//		Mockito.verify(this.dao, Mockito.times(1)).update(updated);
//
//		//Mockito.when(this.javaUtilities.getString()).thenReturn(INPUT_ADD);
////		Mockito.verify(this.javaUtilities, Mockito.times(1)).getLong();
////		Mockito.verify(this.javaUtilities, Mockito.times(2)).getString();
////		Mockito.verify(this.javaUtilities, Mockito.times(2)).getLong();
////		ITEM_LIST.remove(new Item(1L, "bread", 0.58));
////		Mockito.verify(this.dao, Mockito.times(1)).update(updated);
//		
//	}
	
	// , INPUT_REMOVE = "REMOVE"

	@Test
	public void testDelete() {
		final long ID = 1L;

		Mockito.when(javaUtilities.getLong()).thenReturn(ID);
		Mockito.when(dao.delete(ID)).thenReturn(1);

		assertEquals(1L, this.controller.delete());

		Mockito.verify(javaUtilities, Mockito.times(1)).getLong();
		Mockito.verify(dao, Mockito.times(1)).delete(ID);
	}

}