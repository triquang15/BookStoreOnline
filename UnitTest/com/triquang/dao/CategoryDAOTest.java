package com.triquang.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.triquang.entity.Category;

public class CategoryDAOTest extends BaseDAOTest {

	private static CategoryDAO categoryDAO;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BaseDAOTest.setUpBeforeClass();

		categoryDAO = new CategoryDAO(entityManager);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		BaseDAOTest.tearDownAfterClass();
	}

	@Test
	public void testCreateCategory() {
		Category newCate = new Category("PHP");
		Category category = categoryDAO.create(newCate);

		assertTrue(category != null && category.getCategoryId() > 0);
	}

	@Test
	public void testUpdateCategory() {
		Category cat = new Category("JavaScript");
		cat.setCategoryId(11);

		Category category = categoryDAO.update(cat);

		assertEquals(cat.getName(), category.getName());
	}

	@Test
	public void testGet() {
		Integer catId = 11;
		Category category = categoryDAO.get(catId);

		assertNotNull(category);
	}

	@Test
	public void testDeleteObject() {
		Integer catId = 12;
		categoryDAO.delete(catId);
		Category category = categoryDAO.get(catId);

		assertNull(category);
	}

	@Test
	public void testListAll() {
		List<Category> listCategory = categoryDAO.listAll();
		listCategory.forEach(c -> System.out.println(c.getName() + ", "));

		assertTrue(listCategory.size() > 0);
	}

	@Test
	public void testCount() {
		long totalCategory = categoryDAO.count();

		assertEquals(3, totalCategory);
	}

	@Test
	public void testFindByName() {
		String name = "Java";	
		Category category = categoryDAO.findByName(name);
		
		assertNotNull(category);
		
	}

}
