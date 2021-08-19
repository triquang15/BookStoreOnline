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

import com.triquang.dao.HashGenerator;
import com.triquang.dao.UserDAO;
import com.triquang.entity.Users;

public class UserServices {

	private UserDAO userDAO;
	private EntityManager entityManager;

	private HttpServletRequest request;
	private HttpServletResponse response;

	public UserServices(EntityManager entityManager, HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		this.entityManager = entityManager;

		userDAO = new UserDAO(entityManager);

	}

	public void listUser() throws ServletException, IOException {

		listUser(null);

	}

	public void listUser(String message) throws ServletException, IOException {
		List<Users> listUsers = userDAO.listAll();

		request.setAttribute("listUsers", listUsers);

		if (message != null) {
			request.setAttribute("message", message);
		}

		String listPage = "user_list.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);

		requestDispatcher.forward(request, response);

	}

	public void createUser() throws ServletException, IOException {

		String email = request.getParameter("email");
		String fullName = request.getParameter("fullname");
		String password = request.getParameter("password");

		Users existUser = userDAO.findByEmail(email);

		if (existUser != null) {

			String message = email + " already exist";
			request.setAttribute("message", message);

			RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
			dispatcher.forward(request, response);

		} else {
			Users users = new Users(email, fullName, password);

			userDAO.create(users);
			listUser("New user created successfully");
		}

	}

	public void editUser() throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("id"));
		Users users = userDAO.get(userId);

		String editPage = "user_form.jsp";
		request.setAttribute("user", users);
		RequestDispatcher dispatcher = request.getRequestDispatcher(editPage);

		dispatcher.forward(request, response);
	}

	public void deleteUser() throws ServletException, IOException {
		int userId = Integer.parseInt(request.getParameter("id"));

		Users userById = userDAO.get(userId);

		String message = "Deleted successfully";

		if (userId == 40) {

			message = "The default admin user account cannot be deleted.";

			request.setAttribute("message", message);

			request.getRequestDispatcher("message.jsp").forward(request, response);

			return;

		}
		if (userById != null) {

			userDAO.delete(userId);

		} else {

			message = "ID does not exit";

			listUser(message);

		}

		listUser(message);
	}

	public void login() throws ServletException, IOException {
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		boolean loginResult = userDAO.checkLogin(email, password);

		if (loginResult) {

			request.getSession().setAttribute("useremail", email);

			RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/");
			dispatcher.forward(request, response);
		} else {

			String message = "Login failed";
			request.setAttribute("message", message);

			RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
			dispatcher.forward(request, response);
		}
	}

	public void updateUser() throws ServletException, IOException {

		int userId = Integer.parseInt(request.getParameter("userId"));
		String email = request.getParameter("email");
		String fullName = request.getParameter("fullname");
		String password = request.getParameter("password");

		Users userById = userDAO.get(userId);
		Users userByEmail = userDAO.findByEmail(email);

		if (userByEmail != null && userByEmail.getUserId() != userById.getUserId()) {

			String message = "Could not update user";
			request.setAttribute("message", message);

			RequestDispatcher dispatcher = request.getRequestDispatcher("message.jsp");
			dispatcher.forward(request, response);

		} else {

			userById.setEmail(email);
			userById.setFullName(fullName);

			if (password != null & !password.isEmpty()) {
				String encryptedPassword = HashGenerator.generateMD5(password);
				userById.setPassword(encryptedPassword);
			}

			userDAO.update(userById);

			String message = "User has been updated successfully";
			listUser(message);
		}

	}
}