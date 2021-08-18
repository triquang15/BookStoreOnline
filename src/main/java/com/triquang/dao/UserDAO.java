package com.triquang.dao;

import java.util.List;

import javax.persistence.EntityManager;

import com.triquang.entity.Users;

public class UserDAO extends JpaDAO<Users> implements GenericDAO<Users> {

	public UserDAO(EntityManager entityManager) {
		super(entityManager);
		// TODO Auto-generated constructor stub
	}

	public Users create(Users users) {
		return super.create(users);
	}

	@Override
	public Users update(Users users) {

		return super.update(users);
	}

	@Override
	public Users get(Object userId) {
		// TODO Auto-generated method stub
		return super.find(Users.class, userId);
	}

	@Override
	public void delete(Object userId) {
		// TODO Auto-generated method stub
		super.delete(Users.class, userId);

	}

	public Users findByEmail(String email) {
		List<Users> listResult = super.findWithNamedQuery("Users.findByEmail", "email", email);

		if (listResult != null && listResult.size() > 0) {
			return listResult.get(0);
		}

		return null;
	}

	@Override
	public List<Users> listAll() {
		// TODO Auto-generated method stub
		return super.findWithNamedQuery("Users.findAll");
	}

	@Override
	public long count() {

		return super.countWithNamedQuery("Users.countAll");
	}

}
