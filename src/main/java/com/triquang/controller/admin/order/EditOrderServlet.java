package com.triquang.controller.admin.order;

import com.triquang.controller.BaseServlet;
import com.triquang.service.OrderServices;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EditOrderServlet
 */
@WebServlet("/admin/edit_order")
public class EditOrderServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public EditOrderServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		OrderServices orderServices = new OrderServices(request, response, entityManager);
		orderServices.showEditOrderForm();
	}

}
