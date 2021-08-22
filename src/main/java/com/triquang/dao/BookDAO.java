package com.triquang.dao;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import com.triquang.entity.Book;

public class BookDAO extends JpaDAO<Book> implements GenericDAO<Book> {

	public BookDAO(EntityManager entityManager) {
		super(entityManager);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Book create(Book book) {
		// TODO Auto-generated method stub
		book.setLastUpdateTime(new Date());
		return super.create(book);
	}

	@Override
	public Book update(Book book) {
		book.setLastUpdateTime(new Date());
		return super.update(book);
	}

	@Override
	public Book get(Object bookId) {
		// TODO Auto-generated method stub
		return super.find(Book.class, bookId);
	}

	@Override
	public void delete(Object bookId) {
		// TODO Auto-generated method stub
		super.delete(Book.class, bookId);

	}

	@Override
	public List<Book> listAll() {
		// TODO Auto-generated method stub
		return super.findWithNamedQuery("Book.findAll");
	}

	@Override
	public long count() {
		// TODO Auto-generated method stub
		return super.countWithNamedQuery("Book.countAll");
	}

	public Book findByTitle(String title) {

		List<Book> result = super.findWithNamedQuery("Book.findByTitle", "title", title);

		if (!result.isEmpty()) {
			return result.get(0);
		}
		return null;
	}

}
