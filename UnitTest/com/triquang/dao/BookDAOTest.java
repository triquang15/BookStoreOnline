package com.triquang.dao;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.triquang.entity.Book;
import com.triquang.entity.Category;

public class BookDAOTest extends BaseDAOTest {

	private static BookDAO bookDAO;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BaseDAOTest.setUpBeforeClass();
		bookDAO = new BookDAO(entityManager);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		BaseDAOTest.tearDownAfterClass();
	}

	@Test
	public void testCreateBook() throws ParseException, IOException {
		Book newBook = new Book();

		Category category = new Category("Music");
		category.setCategoryId(1);
		newBook.setCategory(category);
		newBook.setTitle("Choral Music: Methods and Materials");
		newBook.setAuthor("	\r\n" + "Barbara A. Brinson");
		newBook.setDescription(
				"This essential text provides choral music educators with a well-organized, practical introduction to directing choirs and managing choral programs at the middle-school through high-school level. It offers step-by-step advice on designing and administering a choral program, from curricula to repertoire to performance, and helps instructors develop a personal philosophy of music education.");
		newBook.setPrice(41.02f);
		newBook.setIsbn("978-1133599661");

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date publishDate = dateFormat.parse("09/02/2021");
		newBook.setPublishDate(publishDate);

		String imagePath = "D:\\Programming Languages Courses\\Java EE\\BookStoreWebsite\\src\\main\\webapp\\images\\Music.jpg";
		byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
		newBook.setImage(imageBytes);

		Book createBook = bookDAO.create(newBook);

		assertTrue(createBook.getBookId() > 0);
	}

	@Test
	public void testUpdateBook() throws ParseException, IOException {
		Book existBook = new Book();
		existBook.setBookId(2);

		Category category = new Category("Development");
		category.setCategoryId(2);
		existBook.setCategory(category);

		existBook.setTitle("Python for Data Analysis");
		existBook.setAuthor("Wes McKinney");
		existBook.setDescription(
				"Get complete instructions for manipulating, processing, cleaning, and crunching datasets in Python. Updated for Python 3.6, the second edition of this hands-on guide is packed with practical case studies that show you how to solve a broad set of data analysis problems effectively. You’ll learn the latest versions of pandas, NumPy, IPython, and Jupiter in the process.");
		existBook.setPrice(41.02f);
		existBook.setIsbn("978-1491957665");

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date publishDate = dateFormat.parse("05/12/2021");
		existBook.setPublishDate(publishDate);

		String imagePath = "D:\\Programming Languages Courses\\Java EE\\BookStoreWebsite\\src\\main\\webapp\\images\\Python.jpg";
		byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
		existBook.setImage(imageBytes);

		Book updateBook = bookDAO.update(existBook);

		assertEquals(updateBook.getTitle(), "Python for Data Analysis");
	}

	@Test(expected = EntityNotFoundException.class)
	public void deleteFail() {
		Integer bookId = 100;
		bookDAO.delete(bookId);
	}

	@Test
	public void deleteSuccess() {
		Integer bookId = 4;
		bookDAO.delete(bookId);

		assertTrue(true);
	}

	@Test
	public void testGetBookFail() {
		Integer bookId = 99;
		Book book = bookDAO.get(bookId);

		assertNull(book);
	}

	@Test
	public void testGetBookSuccess() {
		Integer bookId = 2;
		Book book = bookDAO.get(bookId);

		assertNotNull(book);
	}

	@Test
	public void testListAll() {
		List<Book> listBooks = bookDAO.listAll();

		for (Book book : listBooks) {
			System.out.println(book.getTitle() + " - " + book.getAuthor());
		}

		assertFalse(listBooks.isEmpty());
	}

	@Test
	public void testFindByTitleNotExist() {
		String title = "Angular";

		Book book = bookDAO.findByTitle(title);

		assertNull(book);
	}

	@Test
	public void testFindByTitleExist() {
		String title = "Python for Data Analysis";

		Book book = bookDAO.findByTitle(title);

		System.out.println(book.getAuthor());
		System.out.println(book.getPrice());
		assertNotNull(book);
	}

	@Test
	public void testCount() {
		long totalBooks = bookDAO.count();

		assertEquals(3, totalBooks);
	}

	@Test
	public void testListByCategory() {
		int categoryId = 1;

		List<Book> listBooks = bookDAO.listByCategory(categoryId);

		assertTrue(listBooks.size() > 0);

	}
	
	@Test
	public void testNewBook() {
		List<Book> listBooks = bookDAO.listNewBook();
		for (Book book : listBooks) {
			System.out.println(book.getTitle() + " - " + book.getPublishDate());
		}
		
		assertEquals(listBooks.size(), 4);
	}
	
	@Test
	public void searchBook() {
		String keyword = "Java";
		List<Book> result = bookDAO.search(keyword);
		
		for (Book book : result) {
			System.out.println(book.getTitle());
		}
		 assertEquals(3, result.size());
	}
}
