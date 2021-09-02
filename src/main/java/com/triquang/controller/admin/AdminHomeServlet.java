package com.triquang.controller.admin;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.triquang.controller.BaseServlet;
import com.triquang.dao.BookDAO;
import com.triquang.dao.CustomerDAO;
import com.triquang.dao.OrderDAO;
import com.triquang.dao.ReviewDAO;
import com.triquang.dao.UserDAO;
import com.triquang.entity.BookOrder;
import com.triquang.entity.Review;

import antlr.collections.List;

/**
 * Servlet implementation class AdminHomeServlet
 */
@WebServlet("/admin/")
public class AdminHomeServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AdminHomeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDAO userDao = new UserDAO(entityManager);
		OrderDAO orderDao = new OrderDAO(entityManager);
		ReviewDAO reviewDao = new ReviewDAO(entityManager);
		BookDAO bookDao = new BookDAO(entityManager);
		CustomerDAO customerDao = new CustomerDAO(entityManager);
		
		java.util.List<BookOrder> listMostRecentSales = orderDao.listMostRecentSales();
		java.util.List<Review> listMostRecentReviews = reviewDao.listMostRecentReviews();

		
		long totalUsers = userDao.count();
		long totalBooks = bookDao.count();
		long totalCustomers = customerDao.count();
		long totalReviews = reviewDao.count();
		long totalOrders = orderDao.count();
		
		request.setAttribute("listMostRecentSales", listMostRecentSales);
		request.setAttribute("listMostRecentReviews", listMostRecentReviews);
		
		request.setAttribute("totalUsers", totalUsers);
		request.setAttribute("totalBooks", totalBooks);
		request.setAttribute("totalCustomers", totalCustomers);
		request.setAttribute("totalReviews", totalReviews);
		request.setAttribute("totalOrders", totalOrders);
		
		String homepage = "index.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(homepage);
		requestDispatcher.forward(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
