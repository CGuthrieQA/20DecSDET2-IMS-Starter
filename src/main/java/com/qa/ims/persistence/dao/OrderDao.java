package com.qa.ims.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.DatabaseUtilities;

public class OrderDao implements IDomainDao<Order> {

	// setup logger
	public static final Logger LOGGER = LogManager.getLogger();
	
	public static final ItemDao itemDao = new ItemDao();

	//CRUD

	//CREATE
	@Override
	public Order create(Order order) {
		try (Connection connection = DatabaseUtilities.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("INSERT INTO orders(fk_customers_id) VALUES (?)");) {
			statement.setLong(1, order.getCustomer().getId());
			statement.executeUpdate();
			return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	// READ
	public Order read(Long id) {
		try (Connection connection = DatabaseUtilities.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement("SELECT * FROM orders WHERE id = ?");) {
			statement.setLong(1, id);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			return modelFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	// READ all
	@Override
	public List<Order> readAll() {
		try (Connection connection = DatabaseUtilities.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM orders");) {
			List<Order> orders = new ArrayList<>();
			while (resultSet.next()) {
				orders.add(modelFromResultSet(resultSet));
			}
			return orders;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}

	// READ latest
	public Order readLatest() {
		try (Connection connection = DatabaseUtilities.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery("SELECT * FROM orders ORDER BY id DESC LIMIT 1");) {
			resultSet.next();
			return modelFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	// UPDATE
	@Override
	public Order update(Order order) {
     try (Connection connection = DatabaseUtilities.getInstance().getConnection();
                PreparedStatement statement = connection
                        .prepareStatement("UPDATE orders SET fk_customers_id = ? WHERE id = ?");) {
            statement.setLong(1, order.getCustomer().getId());
            statement.setLong(2, order.getId());
            statement.executeUpdate();
            return read(order.getId());
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
	}

	// UPDATE ADD
	public List<Item> updateADD(Order order, long orders_id, Long items_id) {
		
		try(Connection connection = DatabaseUtilities.getInstance().getConnection();
				PreparedStatement statement = connection
						.prepareStatement("INSERT INTO orders_items(fk_orders_id, fk_items_id) VALUES (?, ?)");) {
			statement.setLong(1, orders_id);
			statement.setLong(2,  items_id);
			statement.executeUpdate();	
			
			List<Item> itemlist = order.getItems();
			itemlist.add( itemDao.read(items_id) );
			
			return itemlist;
			
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}

		return null;
	}
	
	// UPDATE REMOVE
	public List<Item> updateREMOVE(Order order, Long orders_id, Long items_id) {
		
		try(Connection connection = DatabaseUtilities.getInstance().getConnection();
				Statement statement = connection.createStatement();) {
			statement.executeUpdate("DELETE FROM orders_items WHERE fk_items_id = " + items_id + " AND fk_orders_id = " + orders_id);
			
			List<Item> itemlist = order.getItems();
			itemlist.remove( itemDao.read(items_id) );
			
			return itemlist;
		
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}

		return null;
	}


	// DELETE
	@Override
	public int delete(long id) {
		try (Connection connection = DatabaseUtilities.getInstance().getConnection();
				Statement statement = connection.createStatement();) {
			return statement.executeUpdate("delete from orders where id = " + id);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return 0;
	}

	@Override
	public Order modelFromResultSet(ResultSet resultSet) throws SQLException {
		Long id = resultSet.getLong("id");
		Long fk_customers_id = resultSet.getLong("fk_customers_id");
		
		CustomerDao customerDao = new CustomerDao();
        
        Customer customer = customerDao.read(fk_customers_id);
        
		return new Order(id, customer);
	}

}
