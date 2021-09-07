package com.triquang.controller.frontend.customer;

import com.triquang.controller.BaseServlet;
import com.triquang.service.CustomerServices;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EditCustomerProfileServlet
 */
@WebServlet("/edit_profile")
public class EditCustomerProfileServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public EditCustomerProfileServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CustomerServices customerServices = new CustomerServices(entityManager, request, response);
		customerServices.editCustomerProfile();
		
	}

}
