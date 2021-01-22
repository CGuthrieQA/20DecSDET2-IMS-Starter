package com.qa.ims.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.ItemDao;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.JavaUtilities;

public class ItemController implements ICrudController<Item>{
	
	// setup logger
	public static final Logger LOGGER = LogManager.getLogger();
	 
	// declare variables
	private ItemDao itemDao;
   	private JavaUtilities javaUtilities;
   	
   	// constructor
   	public ItemController(ItemDao itemDao, JavaUtilities javaUtilities) {
        super();
        this.itemDao = itemDao;
        this.javaUtilities = javaUtilities;
    }
   	
   	// CRUD
   	
   	// CREATE
	@Override
	public Item create() {
		LOGGER.info("Please enter the name of the item");
        String name = javaUtilities.getString();
		LOGGER.info("Please enter the value of the item");
        double value = javaUtilities.getDouble();
        
        Item item = itemDao.create(new Item(name, value));
        LOGGER.info("Item created");
        return item;
	}
	
	@Override
    public List<Item> readAll() {
        List<Item> items = itemDao.readAll();
        for (Item item : items) {
            LOGGER.info(item);
        }
        return items;
    }


	@Override
	public Item update() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete() {
		// TODO Auto-generated method stub
		return 0;
	}

}
