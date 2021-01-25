package com.qa.ims.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.OrderDao;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.JavaUtilities;

public class OrderController implements ICrudController<Order> {
	
	// setup logger
	public static final Logger LOGGER = LogManager.getLogger();
	 
	// declare variables
    private OrderDao orderDao;
    private JavaUtilities javaUtilities;
    
    // constructor
    
    public OrderController(OrderDao orderDao, JavaUtilities javaUtilities) {
        super();
        this.orderDao = orderDao;
        this.javaUtilities = javaUtilities;
    }

    // CRUD

	// CREATE
	@Override
	public Order create() {
        LOGGER.info("Please enter id of customer ordering");
        Long fk_customers_id = javaUtilities.getLong();
        Order order = orderDao.create(new Order(fk_customers_id));
        LOGGER.info("Order created");
        return order;
	}
	
	// READ
	@Override
    public List<Order> readAll() {
        List<Order> orders = orderDao.readAll();
        for (Order order : orders) {
            LOGGER.info(order);
        }
        return orders;
    }

	@Override
	public Order update() {
		
		// variable
		List<Item> orders_items = new ArrayList<>();
		
		LOGGER.info("Please enter the id of the order you would like to update");
        Long id = javaUtilities.getLong();
        LOGGER.info("Would you like to ADD or REMOVE and item?");
        String method = javaUtilities.getString();
    	
        LOGGER.info("Please enter an item id");
        Long items_id = javaUtilities.getLong();
        
        // this seems odd
        if ( method.equals("ADD") ) {
            
        	orders_items = orderDao.updateADD(orderDao.read(id), id, items_id);
        	LOGGER.info("Item added to order");
            
        } else if ( method.equals("REMOVE") ) {
            
        	orders_items = orderDao.updateREMOVE(orderDao.read(id), id, items_id);
        	LOGGER.info("Item removed from order");
            
        }
        
        Order order = orderDao.update(new Order(id, orderDao.read(id).getFk_customers_id(), orders_items));
        LOGGER.info("Order updated");
		return order;
        
	}
	
	// DELETE
	@Override
    public int delete() {
        LOGGER.info("Please enter the id of the order you would like to delete");
        Long id = javaUtilities.getLong();
        return orderDao.delete(id);
    }

}
