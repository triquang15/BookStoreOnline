package com.triquang.dao;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import com.triquang.entity.Book;
import com.triquang.entity.Review;

public class BookRatingTest {

	@Test
	public void testAverageRating () {
		Book book = new Book();
		
		Set<Review> reviews = new HashSet<>();
		Review review = new Review();
		review.setRating(5);
		reviews.add(review);
		
		book.setReviews(reviews);
		
		float averageRating = book.getAverageRating();
		
		assertEquals(5.0, averageRating, 0.0);
	}
	
	@Test
	public void testRatingString1() {
		float averageRating = 0.0f;
		Book book = new Book();
		String actual = book.getRatingString(averageRating);
		
		String expected = "off,off,off,off,off";
		
		assertEquals(expected, actual);
	}

}
