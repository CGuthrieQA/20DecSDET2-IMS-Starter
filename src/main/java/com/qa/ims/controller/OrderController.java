package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.CustomerDao;
import com.qa.ims.persistence.dao.OrderDao;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.JavaUtilities;

public class OrderController implements ICrudController<Order> {
	
	public static final Logger LOGGER = LogManager.getLogger();
	 
    private OrderDao orderDao;
    private JavaUtilities javaUtilities;
    
    public OrderController(OrderDao orderDao, JavaUtilities javaUtilities) {
        super();
        this.orderDao = orderDao;
        this.javaUtilities = javaUtilities;
    }

	@Override
	public Order create() {
        LOGGER.info("Please enter id of customer ordering");
        
        Long fkCustomersId = javaUtilities.getLong();
        
        CustomerDao customerDao = new CustomerDao();  
        Customer customer = customerDao.read(fkCustomersId);
        
        Order order = orderDao.create(new Order(customer));
        
        LOGGER.info("Order created");
        return order;
	}
	
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
		
		Order order = new Order();
		
		LOGGER.info("Please enter the id of the order you would like to update");
        Long id = javaUtilities.getLong();
        
        LOGGER.info("Would you like to ADD or REMOVE and item?");
        String method = javaUtilities.getString();
    	
        LOGGER.info("Please enter an item id");
        Long itemsId = javaUtilities.getLong();
        
        if ( method.toUpperCase().equals("ADD") ) {
            
        	order = orderDao.updateADD(orderDao.read(id), id, itemsId);
        	LOGGER.info("Item added to order");
            
        } else if ( method.toUpperCase().equals("REMOVE") ) {
            
        	order = orderDao.updateREMOVE(orderDao.read(id), id, itemsId);
        	LOGGER.info("Item removed from order");
            
        }
        
        LOGGER.info("Order updated");
		return order;
        
	}
	
	@Override
    public int delete() {
        LOGGER.info("Please enter the id of the order you would like to delete");
        Long id = javaUtilities.getLong();
        return orderDao.delete(id);
    }

}
