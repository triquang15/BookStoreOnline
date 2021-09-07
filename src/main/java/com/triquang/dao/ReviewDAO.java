package com.triquang.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.triquang.entity.Review;

public class ReviewDAO extends JpaDAO<Review> implements GenericDAO<Review> {

	public ReviewDAO(EntityManager entityManager) {
		super(entityManager);
		// TODO Auto-generated constructor stub
	}

	
	@Override
	public Review create(Review review) {
		// TODO Auto-generated method stub
		review.setReviewTime(new Date());
		return super.create(review);
	}


	@Override
	public Review get(Object reviewId) {
		// TODO Auto-generated method stub
		return super.find(Review.class, reviewId);
	}

	@Override
	public void delete(Object reviewId) {
		super.delete(Review.class, reviewId);
		
	}

	@Override
	public List<Review> listAll() {
		// TODO Auto-generated method stub
		return super.findWithNamedQuery("Review.listAll");
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return super.countWithNamedQuery("Review.countAll");
	}
	
	public Review finfByCustomerAndBook(Integer customerId, Integer bookId) {
		Map<String, Object>parameters = new HashMap<>();
		parameters.put("customerId",customerId);
		parameters.put("bookId", bookId);
		
		List<Review> result = super.findWithNamedQuery("Review.findByCustomerAndBook", parameters);
		
		if (!result.isEmpty()) {
			return result.get(0);
		}
		return null;
	}
	
	public List<Review> listMostRecentReviews(){
		return super.findWithNamedQuery("Review.listAll", 0 ,3);
	}

}
