package com.triquang.controller.frontend.shoppingcart;

import static org.junit.Assert.*;

import java.util.Map;


import org.junit.BeforeClass;
import org.junit.Test;
import com.triquang.entity.Book;

public class ShoppingCartTest  {
	
	private static ShoppingCart cart;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		cart = new ShoppingCart();
		
		Book book = new Book(1);

		cart.addItem(book);
		cart.addItem(book);
		
	}

	@Test
	public void testAddItem() {

		Map<Book, Integer> items = cart.getItems();
		int quantity = items.get(new Book(1));
		
		assertEquals(1, quantity);
	}
	
	@Test
	public void testRemoveItem() {
		cart.removeItem(new Book(1));
		
		assertTrue(cart.getItems().isEmpty());
	}
	
	@Test
	public void testGetTotalQuantity() {
		Book book = new Book(3);

		cart.addItem(book);
		cart.addItem(book);
		cart.addItem(book);
		
		assertEquals(2, cart.getTotalQuantity());
	}
	
	@Test
	public void testGetTotalAmount() {
		ShoppingCart cart = new ShoppingCart();
		
		assertEquals(0.0f, cart.getTotalAmount(), 0.0f);
	}

	@Test
	public void testClear() {
		cart.clear();
		
		assertEquals(0, cart.getTotalQuantity());
	}
	
	@Test
	public void testUpdateCart() {
		ShoppingCart cart = new ShoppingCart();
		Book book1 = new Book(1);
		Book book2 = new Book(2);
		
		cart.addItem(book1);
		cart.addItem(book2);
		
		int[] bookId = {1, 2};
		int[] quantity = {3, 4};
		
		cart.updateCart(bookId, quantity);
		
		assertEquals(7, cart.getTotalQuantity());
	}
}
