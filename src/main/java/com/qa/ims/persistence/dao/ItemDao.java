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

import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.DatabaseUtilities;

public class ItemDao implements IDomainDao<Item> {
	
	public static final Logger LOGGER = LogManager.getLogger();

	@Override
	public Item create(Item item) {
		
		String query = "INSERT INTO items(name, value) VALUES (?, ?)";
		
		try (
				Connection connection = DatabaseUtilities.getInstance().getConnection();
				PreparedStatement statement = connection.prepareStatement(query);
			) {
			statement.setString(1, item.getName());
			statement.setDouble(2, item.getValue());
			statement.executeUpdate();
			return readLatest();
		} catch (Exception e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return null;
	}

	public Item read(Long id) {
		
		String query = "SELECT * FROM items WHERE id = ?";
		
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
	public List<Item> readAll() {
		
		String query = "SELECT * FROM items";
		
		try (
				Connection connection = DatabaseUtilities.getInstance().getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(query);
			) {
			List<Item> items = new ArrayList<>();
			while (resultSet.next()) {
				items.add(modelFromResultSet(resultSet));
			}
			return items;
		} catch (SQLException e) {
			LOGGER.debug(e);
			LOGGER.error(e.getMessage());
		}
		return new ArrayList<>();
	}

	public Item readLatest() {
		
		String query = "SELECT * FROM items ORDER BY id DESC LIMIT 1";
		
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
	public Item update(Item item) {
		
		String query = "UPDATE items SET name = ?, value = ? WHERE id = ?";
		
        try (
        		Connection connection = DatabaseUtilities.getInstance().getConnection();
                PreparedStatement statement = connection.prepareStatement(query);
        	) {
            statement.setString(1, item.getName());
            statement.setDouble(2, item.getValue());
            statement.setLong(3, item.getId());
            statement.executeUpdate();
            return read(item.getId());
        } catch (Exception e) {
            LOGGER.debug(e);
            LOGGER.error(e.getMessage());
        }
        return null;
    }

	@Override
	public int delete(long id) {
		
		String query = String.format("delete from items where id = %s", id);
		
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
	public Item modelFromResultSet(ResultSet resultSet) throws SQLException {
		Long id = resultSet.getLong("id");
        String name = resultSet.getString("name");
        double value = resultSet.getDouble("value");
        return new Item(id, name, value);
	}

}
