package com.qa.ims.controller;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.qa.ims.persistence.dao.ItemDao;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.utils.JavaUtilities;

@RunWith(MockitoJUnitRunner.class)
public class ItemControllerTest {

	@Mock
	private JavaUtilities javaUtilities;

	@Mock
	private ItemDao dao;

	@InjectMocks
	private ItemController controller;

	@Test
	public void testCreate() {
		final String NAME = "lard";
		final double VAL = 3.2D;
		final Item created = new Item(NAME, VAL);

		Mockito.when(javaUtilities.getString()).thenReturn(NAME);
		Mockito.when(javaUtilities.getDouble()).thenReturn(VAL);
		Mockito.when(dao.create(created)).thenReturn(created);

		assertEquals(created, controller.create());

		Mockito.verify(javaUtilities, Mockito.times(1)).getString();
		Mockito.verify(javaUtilities, Mockito.times(1)).getDouble();
		Mockito.verify(dao, Mockito.times(1)).create(created);
	}

	@Test
	public void testReadAll() {
		List<Item> items = new ArrayList<>();
		items.add(new Item(1L, "spam", 0.70D));

		Mockito.when(dao.readAll()).thenReturn(items);

		assertEquals(items, controller.readAll());

		Mockito.verify(dao, Mockito.times(1)).readAll();
	}

	@Test
	public void testUpdate() {
		Item updated = new Item(1L, "carrot", 0.12D);

		Mockito.when(this.javaUtilities.getLong()).thenReturn(1L);
		Mockito.when(this.javaUtilities.getString()).thenReturn(updated.getName());
		Mockito.when(this.javaUtilities.getDouble()).thenReturn(updated.getValue());
		Mockito.when(this.dao.update(updated)).thenReturn(updated);

		assertEquals(updated, this.controller.update());

		Mockito.verify(this.javaUtilities, Mockito.times(1)).getLong();
		Mockito.verify(this.javaUtilities, Mockito.times(1)).getString();
		Mockito.verify(this.javaUtilities, Mockito.times(1)).getDouble();
		Mockito.verify(this.dao, Mockito.times(1)).update(updated);
	}

	@Test
	public void testDelete() {
		final long ID = 1L;

		Mockito.when(javaUtilities.getLong()).thenReturn(ID);
		Mockito.when(dao.delete(ID)).thenReturn(1);

		assertEquals(1L, this.controller.delete());

		Mockito.verify(javaUtilities, Mockito.times(1)).getLong();
		Mockito.verify(dao, Mockito.times(1)).delete(ID);
	}

}