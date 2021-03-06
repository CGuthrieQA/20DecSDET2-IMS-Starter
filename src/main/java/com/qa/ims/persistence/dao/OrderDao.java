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

	public static final Logger LOGGER = LogManager.getLogger();
	
	public static final ItemDao itemDao = new ItemDao();

	@Override
	public Order create(Order order) {
		
		String query = "INSERT INTO orders(fk_customers_id, cost) VALUES (?, 0.0)";
		
		try (
				Connection connection = DatabaseUtilities.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(query);
			) {
			statement.setLong(1, order.getCustomer().getId());
			statement.executeUpdate();
			return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	public Order read(Long id) {
		
		String query = "SELECT * FROM orders WHERE id = ?";
		
		try (
				Connection connection = DatabaseUtilities.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(query);
			) {
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

	@Override
	public List<Order> readAll() {
		
		String query = "SELECT * FROM orders";
		
		try (
				Connection connection = DatabaseUtilities.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query);
			) {
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

	public Order readLatest() {
		
		String query = "SELECT * FROM orders ORDER BY id DESC LIMIT 1";
		
		try (
				Connection connection = DatabaseUtilities.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query);
			) {
			resultSet.next();
			return modelFromResultSet(resultSet);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	@Override
	public Order update(Order order) {
        return null;
	}

	public Order updateADD(Order order, long ordersId, Long itemsId) {
		
		String query = "INSERT INTO orders_items(fk_orders_id, fk_items_id) VALUES (?, ?)";
		
		try(
				Connection connection = DatabaseUtilities.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(query);
			) {
			statement.setLong(1, ordersId);
			statement.setLong(2,  itemsId);
			statement.executeUpdate();	
			
			List<Item> itemlist = order.getItems();
			itemlist.add( itemDao.read(itemsId) );
			
			Order newOrder = order;
			newOrder.setItems(itemlist);
			
			newOrder.setCost(newOrder.getCost() + itemDao.read(itemsId).getValue());
			
			return newOrder;
			
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}

		return null;
	}
	
	public Order updateREMOVE(Order order, Long ordersId, Long itemsId) {
		
		String query = String.format("DELETE FROM orders_items WHERE fk_items_id = %s AND fk_orders_id = %s" , itemsId, ordersId);
		
		try(
				Connection connection = DatabaseUtilities.getInstance().getConnection();
				Statement statement = connection.createStatement();
			) {
			
			statement.executeUpdate(query);
			
			List<Item> itemlist = order.getItems();
			itemlist.remove( itemDao.read(itemsId) );
			
			Order newOrder = order;
			newOrder.setItems(itemlist);
			
			newOrder.setCost(newOrder.getCost() - itemDao.read(itemsId).getValue());
			
			return newOrder;
		
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}

		return null;
	}

	@Override
	public int delete(long id) {
		
		String query = String.format("DELETE FROM orders WHERE id = %s", id);
		
		try (
				Connection connection = DatabaseUtilities.getInstance().getConnection();
				Statement statement = connection.createStatement();
			) {
			return statement.executeUpdate(query);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return 0;
	}
	
	public int deleteOrdersItems(long id) {
		
		String query = String.format("DELETE FROM orders_items WHERE fk_orders_id = %s", id);
		
		try (
				Connection connection = DatabaseUtilities.getInstance().getConnection();
				Statement statement = connection.createStatement();
			) {
			return statement.executeUpdate(query);
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return 0;
	}

	@Override
	public Order modelFromResultSet(ResultSet resultSet) throws SQLException {
		
		Long id = resultSet.getLong("id");
		Long fkCustomersId = resultSet.getLong("fk_customers_id");
	
		OrderDao orderDao = new OrderDao();
		List<Item> itemList = orderDao.itemList(id);
		double cost = orderDao.calcCost(id);
	
		CustomerDao customerDao = new CustomerDao();
        Customer customer = customerDao.read(fkCustomersId);
        
		return new Order(id, customer, itemList, cost);
		
	}
	
	public List<Item> itemList(Long id){
		
		String query = "SELECT * FROM orders_items WHERE fk_orders_id = ?";
		
		List<Item> itemList = new ArrayList<>();
		
		try(
				Connection connection = DatabaseUtilities.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(query);
			) {
			
			statement.setLong(1, id);
			
			ResultSet resultSet = statement.executeQuery();
			
			while( resultSet.next() ) {
				
				itemList.add( itemDao.read( resultSet.getLong("fk_items_id") ) );
				
			}
			
			return itemList;
			
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return itemList;
	}
	
	public double calcCost(Long id) {
		
		String query = "SELECT * FROM orders_items WHERE fk_orders_id = ?";
		
		try(
				Connection connection = DatabaseUtilities.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(query);
			) {
			statement.setLong(1, id);
			
			ResultSet resultSet = statement.executeQuery();
			
			double cost = 0;
			
			while( resultSet.next() ) {
				
				cost += itemDao.read( resultSet.getLong("fk_items_id")  ).getValue();
				
			}
			
			return cost;
			
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return 0.0;
	}

}
