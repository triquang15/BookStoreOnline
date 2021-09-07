package com.triquang.controller.admin.customer;

import com.triquang.controller.BaseServlet;
import com.triquang.service.CustomerServices;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EditCustomerServlet
 */
@WebServlet("/admin/edit_customer")
public class EditCustomerServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public EditCustomerServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		CustomerServices customerServices = new CustomerServices(entityManager, request, response);
		customerServices.editCustomer();
	}

}
