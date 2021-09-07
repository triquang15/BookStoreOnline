package com.triquang.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.triquang.entity.Book;
import com.triquang.entity.Customer;
import com.triquang.entity.Review;

public class ReviewDAOTest extends BaseDAOTest{

	private static ReviewDAO reviewDao;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BaseDAOTest.setUpBeforeClass();
		reviewDao = new ReviewDAO(entityManager);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		BaseDAOTest.tearDownAfterClass();
	}


	@Test
	public void testCreateReview() {
		Review review = new Review();
		Book book = new Book();
		book.setBookId(6);
		
		Customer customer = new Customer();
		customer.setCustomerId(10);
		
		review.setBook(book);
		review.setCustomer(customer);
		review.setHeadline("This is a very good book");
		review.setRating(3);
		review.setComment("good");
		
		Review saveReview = reviewDao.create(review);
		
		assertTrue(saveReview.getReviewId() > 0);
	}

	@Test
	public void testGet() {
		Integer reviewId = 2;
		Review review = reviewDao.get(reviewId);
		
		assertNotNull(review);
	}

	@Test
	public void testDeleteObject() {
		int reviewId = 2;
		reviewDao.delete(reviewId);
		
		Review review = reviewDao.get(reviewId);
		
		assertNull(review);
	}
	
	@Test
	public void testUpdate() {
		Integer reviewId = 2;
		Review review = reviewDao.get(reviewId);
		review.setHeadline("Very Good!");
		
		Review review2 = reviewDao.update(review);
		
		assertEquals(review.getHeadline(), review2.getHeadline());
	}

	@Test
	public void testListAll() {
		List<Review> listView = reviewDao.listAll();
		
		for (Review review : listView) {
			System.out.println(review.getReviewId() + "-" + review.getBook().getTitle()
					+ review.getCustomer().getFirstname());
		}
		
		assertTrue(listView.size() > 0);
	}

	@Test
	public void testCount() {
		long totalReviews = reviewDao.count();
		System.out.println("Total Reviews: " + totalReviews);
		assertTrue(totalReviews > 0);
	}

	@Test
	public void testFindByCustomerAndBookNotFound() {
		Integer  customerId = 100;
		Integer  bookId = 99;
		
		Review result = reviewDao.finfByCustomerAndBook(customerId, bookId);
		
		assertNull(result);
	}
	
	@Test
	public void testListMostRecent() {
		List<Review> reviews = reviewDao.listMostRecentReviews();
		
		assertEquals(3, reviews.size());
	}
}
