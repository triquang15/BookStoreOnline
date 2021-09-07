package com.triquang.controller.frontend.shoppingcart;

import com.triquang.controller.BaseServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UpdateCartServlet
 */
@WebServlet("/update_cart")
public class UpdateCartServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public UpdateCartServlet() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String[] arrayBookId = request.getParameterValues("bookId");
		String[] arrayQuantity = new String[arrayBookId.length];

		for (int i = 1; i <= arrayQuantity.length; i++) {
			String aQuantity = request.getParameter("quantity" + i);
			arrayQuantity[i - 1] = aQuantity;
		}

		int[] bookId = Arrays.stream(arrayBookId).mapToInt(Integer::parseInt).toArray();
		int[] quantities = Arrays.stream(arrayBookId).mapToInt(Integer::parseInt).toArray();
		
		ShoppingCart cart = (ShoppingCart) request.getSession().getAttribute("cart");
		cart.updateCart(bookId, quantities);
		

		String cartPage = request.getContextPath().concat("/view_cart");
		response.sendRedirect(cartPage);
	}

}
