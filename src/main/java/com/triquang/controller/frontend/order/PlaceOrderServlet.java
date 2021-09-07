package com.triquang.controller.frontend.order;

import com.triquang.controller.BaseServlet;
import com.triquang.service.OrderServices;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class PlaceOrderServlet
 */
@WebServlet("/place_order")
public class PlaceOrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public PlaceOrderServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		OrderServices orderServices = new OrderServices(request, response, entityManager);
		orderServices.placeOrder();
		
	}

}
