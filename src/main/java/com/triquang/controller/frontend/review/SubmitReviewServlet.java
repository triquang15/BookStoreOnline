package com.triquang.controller.frontend.review;

import com.triquang.controller.BaseServlet;
import com.triquang.service.ReviewServices;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class SubmitReviewServlet
 */
@WebServlet("/submit_review")
public class SubmitReviewServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public SubmitReviewServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		ReviewServices reviewServices = new ReviewServices(request, response, entityManager);
		reviewServices.submitReview();
	}

}
