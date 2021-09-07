package com.triquang.dao;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.triquang.entity.Book;
import com.triquang.entity.BookOrder;
import com.triquang.entity.Customer;
import com.triquang.entity.OrderDetail;
import com.triquang.entity.OrderDetailId;

public class OrderDAOTest extends BaseDAOTest {
	private static OrderDAO OrderDAOTest;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BaseDAOTest.setUpBeforeClass();
		OrderDAOTest = new OrderDAO(entityManager);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		BaseDAOTest.tearDownAfterClass();
	}

	@Test
	public void testCreateBookOrder() {
		BookOrder order = new BookOrder();
		Customer customer = new Customer();
		customer.setCustomerId(5);
		
		order.setCustomer(customer);
		order.setFirstname("Hoai");
		order.setLastname("An");
		order.setPhone("123456789");
		order.setAddressLine1("123 South Street, New York, USA");
		order.setAddressLine2("Viet Nam");
		order.setCity("Ha Noi");
		order.setState("Ha Noi");
		order.setCountry("Viet Nam");
		order.setPaymentMethod("Paypal");
		order.setZipcode("124");
		
		
		Set<OrderDetail> orderDetails = new HashSet<>();
		OrderDetail orderDetail = new OrderDetail();
		
		Book book = new Book(12);
		orderDetail.setBook(book);
		orderDetail.setQuantity(3);
		orderDetail.setSubtotal(375.0f);
		orderDetail.setBookOrder(order);
		
		orderDetails.add(orderDetail);
		
		order.setOrderDetails(orderDetails);
		order.setTax(2.0f);
		order.setShippingFee(1.0f);
		orderDetail.setSubtotal(375.0f);
		order.setTotal(378.0f);
		
		OrderDAOTest.create(order);
		
		assertTrue(order.getOrderId() > 0);
		;
	}

	@Test
	public void testUpdateBookOrder() {
		Integer orderId = 5;
		BookOrder order = OrderDAOTest.get(orderId);
		order.setAddressLine1("New Shipping Address");
		
		OrderDAOTest.update(order);
		
		BookOrder updateOrder = OrderDAOTest.get(orderId);
		
		assertEquals(order.getAddressLine1(), updateOrder.getAddressLine1());
	}

	@Test
	public void testGet() {
		Integer orderId = 5;
		BookOrder order = OrderDAOTest.get(orderId);
		System.out.println(order.getFirstname());
		
		assertEquals(1, order.getOrderDetails().size());
	}

	@Test
	public void testDeleteObject() {
		int orderId = 6;	
		OrderDAOTest.delete(orderId);		
		BookOrder order =  OrderDAOTest.get(orderId);
		
		assertNull(order);
	}

	@Test
	public void testListAll() {
		 List<BookOrder> listOrders = OrderDAOTest.listAll();
		 
		 for (BookOrder bookOrder : listOrders) {
			System.out.println(bookOrder.getOrderId() + "-" + bookOrder.getCustomer().getFirstname());
		}
		 assertTrue(listOrders.size() > 0);
	}

	@Test
	public void testCount() {
		long totalOrder = OrderDAOTest.count();
		assertEquals(6, totalOrder);
	}
	
	@Test
	public void testListByCustomerNoOrder() {
		Integer customerId = 99;
		List<BookOrder> listOrder = OrderDAOTest.listByCustomer(customerId);
		
		assertTrue(listOrder.isEmpty());
	}
	
	@Test
	public void testGetByIdCustomrNull() {
		Integer orderId = 10;
		Integer customerId = 99;
		
		BookOrder order = OrderDAOTest.get(orderId, customerId);
		
		assertNull(order);
		
	}
	
	@Test
	public void testMostRecentSales() {
		List<BookOrder> recentOrders = OrderDAOTest.listMostRecentSales();
		
		assertEquals(3, recentOrders.size());
	}

}
