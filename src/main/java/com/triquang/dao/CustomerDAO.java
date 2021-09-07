package com.triquang.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;

import com.triquang.entity.Customer;

public class CustomerDAO extends JpaDAO<Customer> implements GenericDAO<Customer> {

	public CustomerDAO(EntityManager entityManager) {
		super(entityManager);
		// TODO Auto-generated constructor stub
	}
	
	

	@Override
	public Customer create(Customer customer) {
		// TODO Auto-generated method stub
		customer.setRegisterDate(new Date());
		return super.create(customer);
	}



	@Override
	public Customer get(Object id) {
		// TODO Auto-generated method stub
		return super.find(Customer.class, id);
	}

	@Override
	public void delete(Object id) {
		// TODO Auto-generated method stub
		 super.delete(Customer.class, id);
		
	}

	@Override
	public List<Customer> listAll() {
		// TODO Auto-generated method stub
		return super.findWithNamedQuery("Customer.findAll");
	}


	@Override
	public long count() {
		// TODO Auto-generated method stub
		return super.countWithNamedQuery("Customer.countAll");
	}
	
	public Customer findByEmail(String email) {
		List<Customer> result = super.findWithNamedQuery("Customer.findByEmail", "email", email);
		
		if (!result.isEmpty()) {
			return result.get(0);
		}
		return null;
	}
	
	public Customer checkLogin(String email, String password) {
		Map<String, Object> paramerters = new HashMap<>();
		paramerters.put("email", email);
		paramerters.put("pass", password);
		
		List<Customer> result = super.findWithNamedQuery("Customer.checkLogin",paramerters);
		
		if (!result.isEmpty()) {
			return result.get(0);
			
		}
		return null;
	}

}
