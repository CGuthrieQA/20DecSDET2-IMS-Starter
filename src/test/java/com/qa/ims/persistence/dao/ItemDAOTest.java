package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.DatabaseUtilities;

public class ItemDAOTest {
	
	private final ItemDao DAO = new ItemDao();

    @Before
    public void setup() {
        DatabaseUtilities.connect();
        DatabaseUtilities.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
    }

    @Test
    public void testCreate() {
        final Item created = new Item(7L, "wine", 12.75D);
        assertEquals(created, DAO.create(created));
        assertEquals(null, DAO.create(null));
    }

    @Test
    public void testReadAll() {
        List<Item> expected = new ArrayList<>();
        expected.add(new Item(1L, "bread", 0.58D));
        expected.add(new Item(2L, "milk", 0.40D));
        expected.add(new Item(3L, "cheese", 2.3D));
        expected.add(new Item(4L, "eggs", 1.2D));
        expected.add(new Item(5L, "soda" , 0.50D));
        expected.add(new Item(6L, "apple" , 0.10D));
        assertEquals(expected, DAO.readAll());
    }

    @Test
    public void testReadLatest() {
        assertEquals(new Item(6L, "apple" , 0.10D), DAO.readLatest());
      
    }

    @Test
    public void testRead() {
        final long ID = 1L;
        assertEquals(new Item(ID, "bread", 0.58D), DAO.read(ID));
        assertEquals(null, DAO.read(null));
    }

    @Test
    public void testUpdate() {
        final Item updated = new Item(1L, "butter", 1.20D);
        assertEquals(updated, DAO.update(updated));
        assertEquals(null, DAO.update(null));
    }

    @Test
    public void testDelete() {
        assertEquals(1, DAO.delete(5L));
    }
}
