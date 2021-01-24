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
	
	// READ
	@Override
    public List<Item> readAll() {
        List<Item> items = itemDao.readAll();
        for (Item item : items) {
            LOGGER.info(item);
        }
        return items;
    }

	// UPDATE
	@Override
	public Item update() {
        LOGGER.info("Please enter the id of the item you would like to update");
        Long id = javaUtilities.getLong();
        LOGGER.info("Please enter a name");
        String name = javaUtilities.getString();
        LOGGER.info("Please enter a value");
        double value = javaUtilities.getDouble();
        Item item = itemDao.update(new Item(id, name, value));
        LOGGER.info("Item Updated");
        return item;
    }
	
	// DELETE
	@Override
	public int delete() {
        LOGGER.info("Please enter the id of the item you would like to delete");
        Long id = javaUtilities.getLong();
        return itemDao.delete(id);
    }

}
