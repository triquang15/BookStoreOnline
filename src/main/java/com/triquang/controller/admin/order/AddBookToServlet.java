package com.triquang.controller.admin.order;

import com.triquang.controller.BaseServlet;
import com.triquang.dao.BookDAO;
import com.triquang.entity.Book;
import com.triquang.entity.BookOrder;
import com.triquang.entity.OrderDetail;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AddBookToServlet
 */
@WebServlet("/admin/add_book_to_order")
public class AddBookToServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public AddBookToServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int bookId = Integer.parseInt(request.getParameter("bookId"));
		int quantity = Integer.parseInt(request.getParameter("quantity"));

		BookDAO bookDAO = new BookDAO(entityManager);
		Book book = bookDAO.get(bookId);
		
		HttpSession session = request.getSession();
		BookOrder order = (BookOrder) session.getAttribute("order");
		
		float subtotal = quantity * book.getPrice();
		
		OrderDetail orderDetail = new OrderDetail();
		orderDetail.setBook(book);
		orderDetail.setQuantity(quantity);
		orderDetail.setSubtotal(subtotal);
		
		float newTotal = order.getTotal() + subtotal;
		order.setTotal(newTotal);
		
		order.getOrderDetails().add(orderDetail);
		
		request.setAttribute("book", book);
		session.setAttribute("newBook", true);
		
		String resultPage = "add_book_result.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(resultPage);
		dispatcher.forward(request, response);
	}

}
