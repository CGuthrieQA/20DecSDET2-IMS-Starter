package com.qa.ims.persistence.domain;

import java.util.ArrayList;
import java.util.List;

public class Order {
	
	private Long id;
	private Customer customer;
	private List<Item> ordersItems = new ArrayList<>();
	private double cost;
	
	public Order() {
	}
	
	public Order(Customer customer) {
		this.setCustomer(customer);
	}
	
	public Order(Long id, Customer customer) {
		this.setId(id);
		this.setCustomer(customer);
	}
	
	public Order(Long id, Customer customer, List<Item> ordersItems, double cost) {
		this.setId(id);
		this.setCustomer(customer);
		this.setItems(ordersItems);
		this.setCost(cost);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public List<Item> getItems() {
		return ordersItems;
	}
	
	public void setItems(List<Item> items) {
		this.ordersItems = items;
	}
	
	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	@Override
	public String toString() {
		
		StringBuilder order = new StringBuilder();
		order.append( String.format("id=%s, customer=[%s], items=", id, customer) );
		ordersItems.forEach( item -> order.append( String.format( "%s, ", item.getName() ) ) );
		order.append( String.format("cost=%s", cost) );
		return order.toString();
		
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(cost);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((ordersItems == null) ? 0 : ordersItems.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (Double.doubleToLongBits(cost) != Double.doubleToLongBits(other.cost))
			return false;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (ordersItems == null) {
			if (other.ordersItems != null)
				return false;
		} else if (!ordersItems.equals(other.ordersItems))
			return false;
		return true;
	}
	
}
