package com.triquang.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.triquang.entity.Book;
import com.triquang.entity.BookOrder;
import com.triquang.entity.Customer;

public class OrderDAO extends JpaDAO<BookOrder> implements GenericDAO<BookOrder> {

	public OrderDAO(EntityManager entityManager) {
		super(entityManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public BookOrder create(BookOrder order) {
		// TODO Auto-generated method stub
		order.setOrderDate(new Date());
		order.setStatus("Processing");

		return super.create(order);
	}

	@Override
	public BookOrder update(BookOrder order) {
		// TODO Auto-generated method stub
		return super.update(order);
	}

	@Override
	public BookOrder get(Object orderId) {
		// TODO Auto-generated method stub
		return super.find(BookOrder.class, orderId);
	}

	public BookOrder get(Integer orderId, Integer customerId) {
		// TODO Auto-generated method stub
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("orderId", orderId);
		parameters.put("customerId", customerId);

		List<BookOrder> rs = super.findWithNamedQuery("BookOrder.findByIdAndCustomer", parameters);
		if (!rs.isEmpty()) {
			return rs.get(0);
		}
		return null;
	}

	@Override
	public void delete(Object orderId) {
		// TODO Auto-generated method stub
		super.delete(BookOrder.class, orderId);

	}

	@Override
	public List<BookOrder> listAll() {
		// TODO Auto-generated method stub
		return super.findWithNamedQuery("BookOrder.findAll");
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return super.countWithNamedQuery("BookOrder.countAll");
	}

	public List<BookOrder> listByCustomer(Integer customerId) {

		return super.findWithNamedQuery("BookOrder.findByCustomer", "customerId", customerId);

	}
	
	public List<BookOrder> listMostRecentSales(){
		return super.findWithNamedQuery("BookOrder.findAll", 0, 3);
		
	}

}
