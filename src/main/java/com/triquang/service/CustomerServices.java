package com.triquang.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.triquang.dao.CustomerDAO;
import com.triquang.entity.Customer;

public class CustomerServices {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private EntityManager entityManager;
	private CustomerDAO customerDAO;

	public CustomerServices(EntityManager entityManager, HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
		customerDAO = new CustomerDAO(entityManager);
	}

	private void customerFieldForm(Customer customer) {
		String email = request.getParameter("email");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String password = request.getParameter("password");
		String phone = request.getParameter("phone");
		String addressLine1 = request.getParameter("address1");
		String addressLine2 = request.getParameter("address2");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String zipCode = request.getParameter("zipCode");
		String country = request.getParameter("country");

		if (email != null && !email.equals("")) {
			customer.setEmail(email);
		}
		customer.setFirstname(firstname);
		customer.setLastname(lastname);

		if (password != null && !password.equals("")) {
			customer.setPassword(password);
		}
		customer.setPhone(phone);
		customer.setAddressLine1(addressLine1);
		customer.setAddressLine2(addressLine2);
		customer.setCity(city);
		customer.setState(state);
		customer.setZipcode(zipCode);
		customer.setCountry(country);
	}

	public void listCustomer(String message) throws ServletException, IOException {
		List<Customer> listCustomers = customerDAO.listAll();
		request.setAttribute("listCustomers", listCustomers);

		if (message != null) {
			request.setAttribute("message", message);
		}

		String listPage = "customer_list.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(listPage);
		dispatcher.forward(request, response);

	}

	public void listCustomers() throws ServletException, IOException {
		listCustomer(null);
	}

	public void createCustomer() throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email = request.getParameter("email");
		Customer existCustomer = customerDAO.findByEmail(email);

		if (existCustomer != null) {
			String message = "Could not create new customer: The email " + email
					+ " is already registered by another customer";
			listCustomer(message);
		} else {

			Customer customer = new Customer();
			customerFieldForm(customer);

			customerDAO.create(customer);

			String message = "New customer has been created successfully";
			listCustomer(message);
		}

	}

	public void editCustomer() throws ServletException, IOException {
		// TODO Auto-generated method stub
		Integer customerId = Integer.parseInt(request.getParameter("id"));
		Customer customer = customerDAO.get(customerId);

		request.setAttribute("customer", customer);
		
		CommonUnity.generateCountryList(request);
		
		String editPage = "customer_form.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(editPage);
		dispatcher.forward(request, response);

	}

	public void updateCustomer() throws ServletException, IOException {
		// TODO Auto-generated method stub
		Integer customerId = Integer.parseInt(request.getParameter("customerId"));
		String email = request.getParameter("email");

		Customer existCustomer = customerDAO.findByEmail(email);
		String message = null;

		if (existCustomer != null && existCustomer.getCustomerId() != customerId) {
			message = "Could not update the customer ID " + customerId
					+ "because there's an existing customer having the same email.";

		} else {

			Customer customer = customerDAO.get(customerId);
			customerFieldForm(customer);

			customerDAO.update(customer);

			message = "The customer has been updated successfully";

		}
		listCustomer(message);
	}

	public void deteleCustomer() throws ServletException, IOException {
		// TODO Auto-generated method stub
		Integer customerId = Integer.parseInt(request.getParameter("id"));
		customerDAO.delete(customerId);

		String message = "The customer has been deleted succussfully";
		listCustomer(message);

	}

	public void registerCustomer() throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email = request.getParameter("email");
		Customer existCustomer = customerDAO.findByEmail(email);
		String message = "";

		if (existCustomer != null) {
			message = "Could not register: The email " + email + " is already registered by another customer";

		} else {

			Customer customer = new Customer();
			customerFieldForm(customer);

			customerDAO.create(customer);

			message = "You have registered successfully. <br/>" + "<a href='login'>Click here</a> to login";

		}

		String messagePage = "frontend/message.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(messagePage);

		request.setAttribute("message", message);
		dispatcher.forward(request, response);

	}

	public void showLogin() throws ServletException, IOException {
		// TODO Auto-generated method stub
		String loginPage = "frontend/login.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(loginPage);
		dispatcher.forward(request, response);

	}

	public void doLogin() throws ServletException, IOException {
		// TODO Auto-generated method stub
		String email = request.getParameter("email");
		String password = request.getParameter("password");

		Customer customer = customerDAO.checkLogin(email, password);

		if (customer == null) {

			String message = "Login failed. Please check your email and password";
			request.setAttribute("message", message);
			showLogin();
		} else {

			HttpSession session = request.getSession();
			session.setAttribute("loggedCustomer", customer);

			Object objectURL = session.getAttribute("redirectURL");

			if (objectURL != null) {
				String redirectURL = (String) objectURL;
				session.removeAttribute(redirectURL);
				response.sendRedirect(redirectURL);
			} else {

				showCustomerProfile();
			}
		}
	}

	public void showCustomerProfile() throws ServletException, IOException {
		String showProfile = "frontend/customer_profile.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(showProfile);
		dispatcher.forward(request, response);

	}

	public void editCustomerProfile() throws ServletException, IOException {
		
		CommonUnity.generateCountryList(request);
		
		String editProfile = "frontend/edit_profile.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(editProfile);
		dispatcher.forward(request, response);

	}

	public void updateProfile() throws ServletException, IOException {
		Customer customer = (Customer) request.getSession().getAttribute("loggedCustomer");

		customerFieldForm(customer);
		customerDAO.update(customer);

		showCustomerProfile();
	}

	public void newCustomer() throws ServletException, IOException {
		// TODO Auto-generated method stub
		CommonUnity.generateCountryList(request);

		String customerForm = "customer_form.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(customerForm);
		dispatcher.forward(request, response);
	}

	
	public void showCustomerRegisterCustomerForm() throws ServletException, IOException {
		
		CommonUnity.generateCountryList(request);
		
		String registerForm = "frontend/register_form.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(registerForm);
		dispatcher.forward(request, response);
	}
}
