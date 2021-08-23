package com.triquang.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.triquang.dao.BookDAO;
import com.triquang.dao.CategoryDAO;
import com.triquang.entity.Book;
import com.triquang.entity.Category;

public class BookServices {

	private BookDAO bookDAO;
	private EntityManager entityManager;
	private CategoryDAO categoryDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;

	public BookServices(EntityManager entityManager, HttpServletRequest request, HttpServletResponse response) {
		this.entityManager = entityManager;
		this.request = request;
		this.response = response;
		bookDAO = new BookDAO(entityManager);
		categoryDAO = new CategoryDAO(entityManager);
	}

	public void listBooks() throws ServletException, IOException {
		listBooks(null);
	}

	public void listBooks(String message) throws ServletException, IOException {
		List<Book> listBooks = bookDAO.listAll();
		request.setAttribute("listBooks", listBooks);

		if (message != null) {
			request.setAttribute("message", message);
		}

		String listPage = "book_list.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(listPage);
		dispatcher.forward(request, response);

	}

	public void showBookNewForm() throws ServletException, IOException {
		List<Category> listCategories = categoryDAO.listAll();
		request.setAttribute("listCategories", listCategories);

		String newPage = "book_form.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(newPage);
		dispatcher.forward(request, response);

	}

	public void createBook() throws ServletException, IOException {

		String title = request.getParameter("title");
		Book existBook = bookDAO.findByTitle(title);

		if (existBook != null) {
			String message = "Could not create book, because the title " + title + "already exist";

			listBooks(message);
			return;
		}

		Book newBook = new Book();
		readBookField(newBook);

		Book createBook = bookDAO.create(newBook);
		if (createBook.getBookId() > 0) {
			String message = "A new book created successfully.";
			request.setAttribute("message", message);

			listBooks(message);
		}
	}

	public void editBook() throws ServletException, IOException {
		Integer bookId = Integer.parseInt(request.getParameter("id"));
		Book book = bookDAO.get(bookId);
		String destPage = "book_form.jsp";

		if (book != null) {
			List<Category> listCategories = categoryDAO.listAll();

			request.setAttribute("book", book);
			request.setAttribute("listCategories", listCategories);

		} else {
			destPage = "message.jsp";
			String message = "Could not find book with ID " + bookId;
			request.setAttribute("message", message);
		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(destPage);
		requestDispatcher.forward(request, response);

	}

	public void readBookField(Book book) throws ServletException, IOException {
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String description = request.getParameter("description");
		String isbn = request.getParameter("isbn");
		float price = Float.parseFloat(request.getParameter("price"));

		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date publishDate = null;
		try {
			publishDate = dateFormat.parse(request.getParameter("publishDate"));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new ServletException("Error parsing publish date (format is MM/dd/yyyy)");

		}

		book.setTitle(title);
		book.setAuthor(author);
		book.setDescription(description);
		book.setIsbn(isbn);
		book.setPrice(price);
		book.setPublishDate(publishDate);

		Integer categoreId = Integer.parseInt(request.getParameter("category"));
		Category category = categoryDAO.get(categoreId);
		book.setCategory(category);

		Part part = request.getPart("bookImage");
		if (part != null && part.getSize() > 0) {
			long size = part.getSize();
			byte[] imageBytes = new byte[(int) size];

			InputStream inputStream = part.getInputStream();
			inputStream.read(imageBytes);
			inputStream.close();

			book.setImage(imageBytes);
		}
	}

	public void updateteBook() throws ServletException, IOException {
		// TODO Auto-generated method stub
		Integer bookId = Integer.parseInt(request.getParameter("bookId"));
		String title = request.getParameter("title");

		Book bookTitle = bookDAO.findByTitle(title);
		Book existBook = bookDAO.get(bookId);

		if (!existBook.equals(bookTitle)) {
			String message = "Could not update book because there's another book heaving same title";

			listBooks(message);
			return;
		}
		readBookField(existBook);
		bookDAO.update(existBook);

		String message = "The book has been updated successfully";
		listBooks(message);

	}

	public void deleteBook() throws ServletException, IOException {
		// TODO Auto-generated method stub
		Integer bookId = Integer.parseInt(request.getParameter("id"));

		bookDAO.delete(bookId);
		String message = "The book has been delete successfully";
		listBooks(message);

	}

	public void listBookByCategory() throws ServletException, IOException {
		// TODO Auto-generated method stub
		int categoryId = Integer.parseInt(request.getParameter("id"));
		Category category = categoryDAO.get(categoryId);

		if (category == null) {
			String message = "Sorry, the category ID " + categoryId + " is not available.";
			request.setAttribute("message", message);
			request.getRequestDispatcher("frontend/message.jsp").forward(request, response);

			return;
		}

		List<Book> listBooks = bookDAO.listByCategory(categoryId);

		request.setAttribute("listBooks", listBooks);
		request.setAttribute("category", category);

		String listPage = "frontend/books_list_by_category.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);
		requestDispatcher.forward(request, response);

	}

	public void viewBookDetail() throws ServletException, IOException {
		// TODO Auto-generated method stub
		Integer bookId = Integer.parseInt(request.getParameter("id"));
		Book book = bookDAO.get(bookId);

		String destPage = "frontend/book_detail.jsp";

		if (book != null) {
			request.setAttribute("book", book);

		} else {
			destPage = "frontend/message.jsp";
			String message = "Sorry, the book with ID " + bookId + " is not available.";
			request.setAttribute("message", message);
		}

		RequestDispatcher requestDispatcher = request.getRequestDispatcher(destPage);
		requestDispatcher.forward(request, response);

	}

	public void search() throws ServletException, IOException {
		// TODO Auto-generated method stub
		String keyword = request.getParameter("keyword");
		List<Book> result = null;

		if (keyword.equals("")) {
			result = bookDAO.listAll();
		} else {
			result = bookDAO.search(keyword);
		}

		request.setAttribute("keyword", keyword);
		request.setAttribute("result", result);

		String listPage = "frontend/search.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);
		requestDispatcher.forward(request, response);

	}

}
