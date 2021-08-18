package com.triquang.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.triquang.entity.Users;

public class UserDAOTest extends BaseDAOTest {

	private static UserDAO userDAO;

	@BeforeClass
	public static void setUpClass() throws Exception {

		BaseDAOTest.setUpBeforeClass();
		userDAO = new UserDAO(entityManager);

	}

	@org.junit.Test
	public void testCreateUsers() {
		Users users = new Users();
		users.setEmail("Iloveyou@gmail.com");
		users.setFullName("Jungle Murad");
		users.setPassword("12345");

		users = userDAO.create(users);

		assertTrue(users.getUserId() > 0);
	}

	@org.junit.Test(expected = PersistenceException.class)
	public void testCreateUsersFieldNotSet() {
		Users users = new Users();

		users = userDAO.create(users);

	}

	@org.junit.Test
	public void testUpdateUsers() {

		Users users = new Users();
		users.setUserId(22);
		users.setEmail("Update@gmail.com");
		users.setFullName("Update");
		users.setPassword("123456");

		String expected = "123456";
		String actual = users.getPassword();

		assertEquals(expected, actual);
		users = userDAO.update(users);
	}

	@Test
	public void testGetUsersFound() {
		Integer userId = 22;
		Users users = userDAO.get(userId);

		if (users != null) {
			System.out.println(users.getEmail());
		}
		assertNotNull(users);
	}

	@Test
	public void testGetUsersNotFound() {
		Integer userId = 99;
		Users users = userDAO.get(userId);

		assertNull(users);
	}

	@Test
	public void testDeleteUser() {
		Integer userId = 19;
		userDAO.delete(userId);

		Users users = userDAO.get(userId);

		assertNull(users);
	}

	@Test(expected = Exception.class)
	public void testDeleteNonExistUsers() {
		Integer userId = 100;
		userDAO.delete(userId);
	}

	@Test
	public void testListAll() {
		List<Users> listAll = userDAO.listAll();

		for (Users users : listAll) {
			System.out.println(users.getEmail());
		}

		assertTrue(listAll.size() > 0);
	}

	@Test
	public void testCount() {
		long count = userDAO.count();

		assertEquals(6, count);
	}

	@Test
	public void testFindByEmail() {
		String email = "messi@gmail.com";

		Users users = userDAO.findByEmail(email);

		assertNotNull(users);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		BaseDAOTest.tearDownAfterClass();
	}

}
