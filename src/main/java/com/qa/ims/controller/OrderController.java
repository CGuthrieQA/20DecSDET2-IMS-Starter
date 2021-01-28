package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.CustomerDao;
import com.qa.ims.persistence.dao.OrderDao;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
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
        String bigBreak = "X==========X========================X";
        String smallBreak = "x----------x------------------------x";
    	LOGGER.info(bigBreak);
        for (Order order : orders) {
            String orderId = String.format("| order id | %s", order.getId());
            String customerName = String.format("| customer | %s %s", order.getCustomer().getFirstName(), order.getCustomer().getSurname());
        	LOGGER.info(orderId);
        	LOGGER.info(smallBreak);
        	LOGGER.info(customerName);
        	LOGGER.info(smallBreak);
        	for (Item item : order.getItems()) {
        		String itemName = String.format("|     item | %s", item.getName());
        		String itemValue = String.format("|    value | £%s", item.getValue());
            	LOGGER.info(itemName);
            	LOGGER.info(smallBreak);
            	LOGGER.info(itemValue);
            	LOGGER.info(smallBreak);
        	}
        	String stringCost = String.format("|    total | £%s", order.getCost());
        	LOGGER.info(stringCost);
        	LOGGER.info(bigBreak);
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
        
        if ( method.equalsIgnoreCase("ADD") ) {
            
        	order = orderDao.updateADD(orderDao.read(id), id, itemsId);
        	LOGGER.info("Item added to order");
            
        } else if ( method.equalsIgnoreCase("REMOVE") ) {
            
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
