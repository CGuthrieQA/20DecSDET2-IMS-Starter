package com.qa.ims.persistence.domain;

import java.util.ArrayList;
import java.util.List;

public class Order {
	
	private Long id;
	private Long fk_customers_id;
	
	// for orders_items
	private List<Item> orders_items = new ArrayList<>();
	
	public Order(Long fk_customers_id) {
		this.fk_customers_id = fk_customers_id;
	}
	
	public Order(Long id, Long fk_customers_id) {
		this.id = id;
		this.fk_customers_id = fk_customers_id;
	}
	
	public Order(Long id, Long fk_customers_id, List<Item> orders_items) {
		this.id = id;
		this.fk_customers_id = fk_customers_id;
		this.orders_items = orders_items;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getFk_customers_id() {
		return fk_customers_id;
	}

	public void setFk_customers_id(Long fk_customers_id) {
		this.fk_customers_id = fk_customers_id;
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
				" fk_customers_id: " + this.fk_customers_id +
				" orders_items: " + this.orders_items;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fk_customers_id == null) ? 0 : fk_customers_id.hashCode());
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
		if (fk_customers_id == null) {
			if (other.fk_customers_id != null)
				return false;
		} else if (!fk_customers_id.equals(other.fk_customers_id))
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
