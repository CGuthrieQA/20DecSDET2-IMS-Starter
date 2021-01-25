package com.qa.ims.persistence.domain;

import java.util.ArrayList;
import java.util.List;

public class Order {
	
	private Long id;
	private Customer customer;
	//private Long fk_customers_id = customer.getId();
	
	// for orders_items
	private List<Item> orders_items = new ArrayList<>();
	
	public Order(Customer customer) {
		this.customer = customer;
	}
	
	public Order(Long id, Customer customer) {
		this.id = id;
		this.customer = customer;
	}
	
	public Order(Long id, Customer customer, List<Item> orders_items) {
		this.id = id;
		this.customer = customer;
		this.orders_items = orders_items;
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
		return orders_items;
	}
	
	public void setItems(List<Item> items) {
		this.orders_items = items;
	}

	@Override
	public String toString() {
		return 
				"id: " + this.id + 
				" fk_customers_id: " + this.customer.getId() +
				" orders_items: " + this.orders_items;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((orders_items == null) ? 0 : orders_items.hashCode());
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
		if (orders_items == null) {
			if (other.orders_items != null)
				return false;
		} else if (!orders_items.equals(other.orders_items))
			return false;
		return true;
	}
	
}
