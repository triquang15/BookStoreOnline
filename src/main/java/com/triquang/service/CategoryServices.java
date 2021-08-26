package com.triquang.service;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.triquang.dao.BookDAO;
import com.triquang.dao.CategoryDAO;
import com.triquang.entity.Category;

public class CategoryServices {

	private EntityManager entityManager;
	private CategoryDAO categoryDAO;

	private HttpServletRequest request;
	private HttpServletResponse response;

	public CategoryServices(EntityManager entityManager, HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.entityManager = entityManager;

		categoryDAO = new CategoryDAO(entityManager);

	}

	public void listCategory(String message) throws ServletException, IOException {
		// TODO Auto-generated method stub
		List<Category> listCategory = categoryDAO.listAll();
		request.setAttribute("listCategory", listCategory);

		if (message != null) {
			request.setAttribute("message", message);
		}

		String listPage = "category_list.jsp";

		RequestDispatcher dispatcher = request.getRequestDispatcher(listPage);
		dispatcher.forward(request, response);

	}

	public void createCategoty() throws ServletException, IOException {
		// TODO Auto-generated method stub
		String name = request.getParameter("name");
		Category existCategory = categoryDAO.findByName(name);

		if (existCategory != null) {
			String msg = "Could not create category";
			request.setAttribute("message", msg);

			RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
			dispatcher.forward(request, response);

		} else {
			Category category = new Category(name);
			categoryDAO.create(category);

			String message = "New category created successfully";
			listCategory(message);
		}

	}

	public void listCategory() throws ServletException, IOException {
		// TODO Auto-generated method stub
		listCategory(null);
	}

	public void editCategory() throws ServletException, IOException {

		int categoryId = Integer.parseInt(request.getParameter("id"));

		Category category = categoryDAO.get(categoryId);
		request.setAttribute("category", category);

		String editPage = "category_form.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(editPage);
		dispatcher.forward(request, response);

	}

	public void updateCategory() throws ServletException, IOException {
		// TODO Auto-generated method stub
		int categoryId = Integer.parseInt(request.getParameter("categoryId"));
		String categoryName = request.getParameter("name");

		Category categoryById = categoryDAO.get(categoryId);
		Category categoryByName = categoryDAO.findByName(categoryName);

		if (categoryByName != null && categoryById.getCategoryId() != categoryByName.getCategoryId()) {
			String message = "Could not update category.";

			request.setAttribute("message", message);

			RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
			dispatcher.forward(request, response);

		} else {
			categoryById.setName(categoryName);
			categoryDAO.update(categoryById);
			String message = "Category has been updated sucessfully.";
			listCategory(message);

		}

	}

	public void deleteCategory() throws ServletException, IOException {
		// TODO Auto-generated method stub
		int categoryId = Integer.parseInt(request.getParameter("id"));
		BookDAO bookDAO = new BookDAO(entityManager);
		long numberOfBooks = bookDAO.countByCategory(categoryId);
		String message;

		if (numberOfBooks > 0) {
			message = "Could not detete the category (ID: %d) because it currently contains some book";
			message = String.format(message, numberOfBooks);
		} else {
			categoryDAO.delete(categoryId);

			message = "The category with Id " + categoryId + "has been remove successfully.";
		}

		listCategory(message);

	}

}
