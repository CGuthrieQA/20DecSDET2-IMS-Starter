package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.DatabaseUtilities;

public class OrderDAOTest {
	
	private final OrderDao DAO = new OrderDao();

    @Before
    public void setup() {
        DatabaseUtilities.connect();
        DatabaseUtilities.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
    }

    @Test
    public void testCreate() {
		final Customer customer = new Customer(1L, "jordan", "harrison");
		final List<Item> itemList = new ArrayList<>();
        final Order created = new Order(2L, customer, itemList, 0.0);
        assertEquals(created, DAO.create(created));
        assertEquals(null, DAO.create(null));
    }

    @Test
    public void testReadAll() {
    	final Customer customer = new Customer(1L, "jordan", "harrison");
		final List<Item> itemList = new ArrayList<>();
		final Item item = new Item(1L, "bread", 0.58D);
		itemList.add(item);
		final double cost = item.getValue();
        List<Order> expected = new ArrayList<>();
        expected.add(new Order(1L, customer, itemList, cost));
        assertEquals(expected, DAO.readAll());
    }

    @Test
    public void testReadLatest() {
    	final Customer customer = new Customer(1L, "jordan", "harrison");
		final List<Item> itemList = new ArrayList<>();
		final Item item = new Item(1L, "bread", 0.58D);
		itemList.add(item);
		final double cost = item.getValue();
        assertEquals(new Order(1L, customer, itemList, cost), DAO.readLatest());
      
    }

    @Test
    public void testRead() {
    	final Customer customer = new Customer(1L, "jordan", "harrison");
		final List<Item> itemList = new ArrayList<>();
		final Item item = new Item(1L, "bread", 0.58D);
		itemList.add(item);
        final long ID = 1L;
        assertEquals(new Order(ID, customer, itemList, 0.58D), DAO.read(ID));
        assertEquals(null, DAO.read(null));
    }

    @Test
    public void testUpdate() {
        assertEquals(null, DAO.update(null));
    }
    
    @Test
    public void testUpdateAdd() {
    	final Customer customer = new Customer(2L, "joe", "biggs");
		final List<Item> itemList = new ArrayList<>();
		final Item item = new Item(1L, "bread", 0.58D);
		itemList.add(item);
        final long ID = 1L;
        final Order order = new Order(ID, customer, itemList, 0.58D);
        assertEquals(order, DAO.updateADD(order, order.getId(), item.getId()));
        assertEquals(null, DAO.read(null));
    }
    
    @Test
    public void testUpdateRemove() {
    	final Customer customer = new Customer(1L, "jordan", "harrison");
		final List<Item> itemList = new ArrayList<>();
		final Item item = new Item(1L, "bread", 0.58D);
		itemList.add(item);
        final long ID = 1L;
        final Order order = new Order(ID, customer, itemList, 0.58D);
        
        final List<Item> emptyList = new ArrayList<>();
        assertEquals(new Order(ID, customer, emptyList, 0.0), DAO.updateREMOVE(order, order.getId(), item.getId()));
        assertEquals(null, DAO.read(null));
    }
   

    @After
    public void testDelete() {
    	assertEquals(1, DAO.deleteOrders_Items(1L));
        assertEquals(1, DAO.delete(1L));
        
    }
}
