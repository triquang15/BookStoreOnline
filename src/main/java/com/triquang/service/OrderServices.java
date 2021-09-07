package com.triquang.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.paypal.api.payments.ItemList;
import com.paypal.api.payments.Payment;
import com.paypal.api.payments.ShippingAddress;
import com.triquang.controller.frontend.shoppingcart.ShoppingCart;
import com.triquang.dao.OrderDAO;
import com.triquang.entity.Book;
import com.triquang.entity.BookOrder;
import com.triquang.entity.Customer;
import com.triquang.entity.OrderDetail;

public class OrderServices {

	private HttpServletRequest request;
	private HttpServletResponse response;
	private EntityManager entityManager;
	private OrderDAO orderDAO;

	public OrderServices(HttpServletRequest request, HttpServletResponse response, EntityManager entityManager) {
		this.request = request;
		this.response = response;
		orderDAO = new OrderDAO(entityManager);
	}

	public void listAllOrder() throws ServletException, IOException {
		listAllOrder(null);
	}

	public void listAllOrder(String message) throws ServletException, IOException {
		List<BookOrder> listOrder = orderDAO.listAll();

		if (message != null) {
			request.setAttribute("message", message);
		}
		request.setAttribute("listOrder", listOrder);

		String listPage = "order_list.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(listPage);
		dispatcher.forward(request, response);

	}

	public void viewOrderDetailForAdmin() throws ServletException, IOException {
		// TODO Auto-generated method stub
		int orderId = Integer.parseInt(request.getParameter("id"));

		BookOrder order = orderDAO.get(orderId);
		request.setAttribute("order", order);

		String detailPage = "order_detail.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(detailPage);
		dispatcher.forward(request, response);

	}

	public void showCheckOutForm() throws ServletException, IOException {
		// TODO Auto-generated method stub

		HttpSession session = request.getSession();
		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("cart");

		// Tax is 10% of subtotal
		float tax = shoppingCart.getTotalAmount() * 0.1f;

		// Shipping Fee is 0.1 USD per copy
		float shippingFee = shoppingCart.getTotalQuantity() * 1.0f;
		float total = shoppingCart.getTotalAmount() + tax + shippingFee;

		session.setAttribute("tax", tax);
		session.setAttribute("shippingFee", shippingFee);
		session.setAttribute("total", total);

		CommonUnity.generateCountryList(request);

		String checkOutPage = "frontend/checkout.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(checkOutPage);
		dispatcher.forward(request, response);

	}

	public void placeOrder() throws ServletException, IOException {
		// TODO Auto-generated method stub
		String paymentMethod = request.getParameter("paymentMethod");
		BookOrder order = readOrderInfo();
		if (paymentMethod.equals("paypal")) {
			PaymentServices paymentServices = new PaymentServices(request, response);
			request.getSession().setAttribute("order4Paypal", order);
			paymentServices.authorizePayment(order);

		} else {
			placeOrderCOD(order);
		}

	}

	public Integer placeOrderPaypal(Payment payment) {
		BookOrder order = (BookOrder) request.getAttribute("order4Paypal");
		ItemList itemList = payment.getTransactions().get(0).getItemList();
		ShippingAddress address = itemList.getShippingAddress();

		String phone = itemList.getShippingPhoneNumber();
		String recipientName = address.getRecipientName();

		String[] names = recipientName.split(" ");

		order.setFirstname(names[0]);
		order.setLastname(names[1]);
		order.setAddressLine1(address.getLine1());
		order.setAddressLine2(address.getLine2());
		order.setCity(address.getCity());
		order.setState(address.getState());
		order.setPhone(address.getPhone());
		order.setCountry(address.getCountryCode());
		
		return saveOrder(order);

	}

	private Integer saveOrder(BookOrder order) {
		BookOrder bookOrder = orderDAO.create(order);

		ShoppingCart shoppingCart = (ShoppingCart) request.getSession().getAttribute("cart");
		shoppingCart.clear();

		return bookOrder.getOrderId();
	}

	private BookOrder readOrderInfo() {

		String paymentMethod = request.getParameter("paymentMethod");
		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String phone = request.getParameter("phone");
		String address1 = request.getParameter("address1");
		String address2 = request.getParameter("address2");
		String city = request.getParameter("city");
		String state = request.getParameter("state");
		String zipcode = request.getParameter("zipcode");
		String country = request.getParameter("country");

		BookOrder order = new BookOrder();

		order.setFirstname(firstname);
		order.setLastname(lastname);
		order.setPhone(phone);
		order.setCity(city);
		order.setState(state);
		order.setCountry(country);
		order.setZipcode(zipcode);
		order.setAddressLine1(address1);
		order.setAddressLine2(address2);
		order.setPaymentMethod(paymentMethod);

		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("loggedCustomer");
		order.setCustomer(customer);

		ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("cart");
		Map<Book, Integer> items = shoppingCart.getItems();

		Iterator<Book> iterator = items.keySet().iterator();
		Set<OrderDetail> orderDetails = new HashSet<>();

		while (iterator.hasNext()) {
			Book book = iterator.next();
			Integer quantity = items.get(book);
			float subtotal = quantity * book.getPrice();

			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setBook(book);
			orderDetail.setBookOrder(order);
			orderDetail.setQuantity(quantity);
			orderDetail.setSubtotal(subtotal);

			orderDetails.add(orderDetail);
		}

		order.setOrderDetails(orderDetails);

		float tax = (Float) session.getAttribute("tax");
		float shippingFee = (Float) session.getAttribute("shippingFee");
		float total = (Float) session.getAttribute("total");

		order.setSubtotal(shoppingCart.getTotalAmount());
		order.setTax(tax);
		order.setShippingFee(shippingFee);
		order.setTotal(total);

		return order;
	}

	private void placeOrderCOD(BookOrder order) throws ServletException, IOException {
		// TODO Auto-generated method stub
		saveOrder(order);

		String message = "Thank you. Your order has been received." + "We will deliver your books within a few day.";
		request.setAttribute("message", message);

		String messagePage = "frontend/message.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(messagePage);
		dispatcher.forward(request, response);

	}

	public void listOrderByCustomer() throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("loggedCustomer");

		List<BookOrder> listOrders = orderDAO.listByCustomer(customer.getCustomerId());

		request.setAttribute("listOrders", listOrders);

		String historyPage = "frontend/order_list.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(historyPage);
		dispatcher.forward(request, response);
	}

	public void showOrderDetailCustomer() throws ServletException, IOException {
		// TODO Auto-generated method stub

		int orderId = Integer.parseInt(request.getParameter("id"));

		HttpSession session = request.getSession();
		Customer customer = (Customer) session.getAttribute("loggedCustomer");

		BookOrder order = orderDAO.get(orderId, customer.getCustomerId());
		request.setAttribute("order", order);

		String detailPage = "frontend/order_detail.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(detailPage);
		dispatcher.forward(request, response);

	}

	public void showEditOrderForm() throws ServletException, IOException {
		// TODO Auto-generated method stub
		Integer orderId = Integer.parseInt(request.getParameter("id"));

		HttpSession session = request.getSession();
		Object isPendingBook = session.getAttribute("newBook");

		if (isPendingBook == null) {
			BookOrder order = orderDAO.get(orderId);
			session.setAttribute("order", order);
		} else {
			session.removeAttribute("newBook");
		}

		CommonUnity.generateCountryList(request);

		String editPage = "order_form.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(editPage);
		dispatcher.forward(request, response);

	}

	public void updateOrder() throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		BookOrder order = (BookOrder) session.getAttribute("order");

		String firstname = request.getParameter("firstname");
		String lastname = request.getParameter("lastname");
		String phone = request.getParameter("phone");
		String address1 = request.getParameter("address1");
		String address2 = request.getParameter("address2");
		String city = request.getParameter("city");
		String zipcode = request.getParameter("zipcode");
		String state = request.getParameter("state");
		String country = request.getParameter("country");
		String paymentMethod = request.getParameter("paymentMethod");
		String orderStatus = request.getParameter("orderStatus");

		Float shippingFee = Float.parseFloat(request.getParameter("shippingFee"));
		Float tax = Float.parseFloat(request.getParameter("tax"));

		order.setFirstname(firstname);
		order.setLastname(lastname);
		order.setPhone(phone);
		order.setAddressLine1(address1);
		order.setAddressLine2(address2);
		order.setCity(city);
		order.setState(state);
		order.setZipcode(zipcode);
		order.setCountry(country);
		order.setPaymentMethod(paymentMethod);
		order.setStatus(orderStatus);
		order.setShippingFee(shippingFee);
		order.setTax(tax);

		String[] arrayBookId = request.getParameterValues("bookId");
		String[] arrayPrice = request.getParameterValues("price");
		String[] arrayQuantity = new String[arrayBookId.length];

		for (int i = 1; i <= arrayQuantity.length; i++) {
			arrayQuantity[i - 1] = request.getParameter("quantity" + i);
		}

		Set<OrderDetail> orderDetails = order.getOrderDetails();
		orderDetails.clear();

		float totalAmount = 0.0f;

		for (int i = 0; i < arrayBookId.length; i++) {
			int bookId = Integer.parseInt(arrayBookId[i]);
			int quantity = Integer.parseInt(arrayQuantity[i]);
			float price = Float.parseFloat(arrayPrice[i]);

			float subtotal = price * quantity;

			OrderDetail orderDetail = new OrderDetail();
			orderDetail.setBook(new Book(bookId));
			orderDetail.setQuantity(quantity);
			orderDetail.setSubtotal(subtotal);
			orderDetail.setBookOrder(order);

			orderDetails.add(orderDetail);

			totalAmount += subtotal;
		}

		order.setSubtotal(totalAmount);
		totalAmount += shippingFee;
		totalAmount += tax;

		order.setTotal(totalAmount);

		orderDAO.update(order);

		String message = "The order " + order.getOrderId() + " has been updated successfully";

		listAllOrder(message);
	}

	public void deleteOrder() throws ServletException, IOException {
		// TODO Auto-generated method stub
		Integer orderId = Integer.parseInt(request.getParameter("id"));
		orderDAO.delete(orderId);

		String message = "The order ID " + orderId + " has been deleted";
		listAllOrder(message);

	}

}
