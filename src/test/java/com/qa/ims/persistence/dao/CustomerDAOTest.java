package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.utils.DatabaseUtilities;

public class CustomerDAOTest {

    private final CustomerDao DAO = new CustomerDao();
    //private final OrderDao oDAO = new OrderDao();

    @Before
    public void setup() {
        DatabaseUtilities.connect();
        DatabaseUtilities.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
    }

    @Test
    public void testCreate() {
        final Customer created = new Customer(5L, "nick", "johnson");
        assertEquals(created, DAO.create(created));
        assertEquals(null, DAO.create(null));
    }

    @Test
    public void testReadAll() {
        List<Customer> expected = new ArrayList<>();
        expected.add(new Customer(1L, "jordan", "harrison"));
        expected.add(new Customer(2L, "hugh", "mann"));
        expected.add(new Customer(3L, "firstname", "lastname"));
        expected.add(new Customer(4L, "Peter", "Staker"));
        assertEquals(expected, DAO.readAll());
        
    }

    @Test
    public void testReadLatest() {
        assertEquals(new Customer(4L, "Peter", "Staker"), DAO.readLatest());
      
    }

    @Test
    public void testRead() {
        final long ID = 1L;
        assertEquals(new Customer(ID, "jordan", "harrison"), DAO.read(ID));
        assertEquals(null, DAO.read(null));
    }

    @Test
    public void testUpdate() {
        final Customer updated = new Customer(2L, "nick", "johnson");
        assertEquals(updated, DAO.update(updated));
        assertEquals(null, DAO.update(null));
    }

    @Test
    public void testDelete() {
        assertEquals(1, DAO.delete(3L));
        
    }

}
