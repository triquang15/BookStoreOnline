package com.triquang.controller.admin.order;

import com.triquang.controller.BaseServlet;
import com.triquang.entity.BookOrder;
import com.triquang.entity.OrderDetail;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class RemoveBookOrderServlet
 */
@WebServlet("/admin/remove_book_form_order")
public class RemoveBookOrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public RemoveBookOrderServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		int bookId = Integer.parseInt(request.getParameter("id"));
		HttpSession session = request.getSession();
		BookOrder bookOrder = (BookOrder) session.getAttribute("order");

		Set<OrderDetail> orderDetails = bookOrder.getOrderDetails();
		Iterator<OrderDetail> iterator = orderDetails.iterator();

		while (iterator.hasNext()) {
			OrderDetail orderDetail = iterator.next();

			if (orderDetail.getBook().getBookId() == bookId) {
				float newTotal = bookOrder.getTotal() - orderDetail.getSubtotal();
				bookOrder.setTotal(newTotal);
				iterator.remove();
			}
		}

		String editOrderPage = "order_form.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(editOrderPage);
		dispatcher.forward(request, response);
	}

}
