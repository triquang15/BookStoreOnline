package com.triquang.dao;

import static org.junit.Assert.*;

import java.util.List;

import javax.persistence.EntityManager;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.triquang.entity.Customer;

public class CustomerDAOTest extends BaseDAOTest {
	private static CustomerDAO customerDAOTest;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BaseDAOTest.setUpBeforeClass();
		customerDAOTest = new CustomerDAO(entityManager);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {

		BaseDAOTest.tearDownAfterClass();
	}

	@Test
	public void testCreateCustomer() {
		Customer customer = new Customer();
		customer.setEmail("Osin@gmail.com");
		customer.setFirstname("Obama");
		customer.setLastname("Brack");
		customer.setCity("New York");
		customer.setCountry("USA");
		customer.setAddressLine1("Juventus");
		customer.setAddressLine2("Chelsea");
		customer.setState("New York");
		customer.setPassword("1234567");
		customer.setPhone("18001060");
		customer.setZipcode("70000");

		Customer createCustomer = customerDAOTest.create(customer);
		assertTrue(createCustomer.getCustomerId() > 0);

	}

	@Test
	public void testGet() {
		Integer customerId = 16;
		Customer customer = customerDAOTest.get(customerId);

		assertNotNull(customer);
	}

	@Test
	public void testUpdateCustomer() {
		Customer customer = customerDAOTest.get(16);
		String firstName = "Hi";
		customer.setFirstname(firstName);

		Customer updateCustomer = customerDAOTest.update(customer);

		assertTrue(updateCustomer.getFirstname().equals(firstName));
	}

	@Test
	public void testDeleteCustomer() {
		Integer customerId = 16;
		customerDAOTest.delete(customerId);
		Customer customer = customerDAOTest.get(customerId);

		assertNull(customer);
	}
	
	@Test
	public void testListAll() {
		List<Customer> listCustomers = customerDAOTest.listAll();
		for (Customer customer : listCustomers) {
			System.out.println(customer.getFirstname());
		}
		
		assertFalse(listCustomers.isEmpty());
	}
	
	@Test
	public void testCount() {
		long totalCustomers = customerDAOTest.count();
		
		assertEquals(10, totalCustomers);
		
	}
	
	@Test
	public void testByEmail() {
		String email = "Triquang@gmail.com";
		Customer customer = customerDAOTest.findByEmail(email);
		
		assertNotNull(customer);
	}
	
	@Test
	public void testCheckLogin() {
		String email = "alice@gmail.com";
		String password = "1234567";
		
		Customer customer = customerDAOTest.checkLogin(email, password);
		
		assertNotNull(customer);
	}

}
