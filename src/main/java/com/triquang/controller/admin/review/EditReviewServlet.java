package com.triquang.controller.admin.review;

import com.triquang.controller.BaseServlet;
import com.triquang.service.ReviewServices;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EditReviewServlet
 */
@WebServlet("/admin/edit_review")
public class EditReviewServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public EditReviewServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ReviewServices reviewServices = new ReviewServices(request, response, entityManager);
		reviewServices.editReview();
	}

}
