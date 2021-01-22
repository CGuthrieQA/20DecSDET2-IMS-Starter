package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.OrderDao;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete() {
		// TODO Auto-generated method stub
		return 0;
	}

}
