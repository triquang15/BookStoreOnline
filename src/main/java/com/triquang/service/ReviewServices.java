package com.triquang.service;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.source.tree.LambdaExpressionTree.BodyKind;
import com.triquang.dao.BookDAO;
import com.triquang.dao.ReviewDAO;
import com.triquang.entity.Book;
import com.triquang.entity.Customer;
import com.triquang.entity.Review;

public class ReviewServices {
	private HttpServletRequest request;
	private HttpServletResponse response;
	private EntityManager entityManager;
	private ReviewDAO reviewDAO;
	private BookDAO bookDAO;

	public ReviewServices(HttpServletRequest request, HttpServletResponse response, EntityManager entityManager) {
		this.request = request;
		this.response = response;
		reviewDAO = new ReviewDAO(entityManager);
		bookDAO = new BookDAO(entityManager);

	}

	public void listAllReview() throws ServletException, IOException {
		listAllReview(null);
	}

	public void listAllReview(String message) throws ServletException, IOException {
		List<Review> listReviews = reviewDAO.listAll();

		request.setAttribute("listReviews", listReviews);

		if (message != null) {
			request.setAttribute("message", message);
		}

		String listPage = "review_list.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(listPage);
		dispatcher.forward(request, response);
	}

	public void editReview() throws ServletException, IOException {
		// TODO Auto-generated method stub
		Integer reviewId = Integer.parseInt(request.getParameter("id"));
		Review review = reviewDAO.get(reviewId);

		request.setAttribute("review", review);

		String editPage = "review_form.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(editPage);
		dispatcher.forward(request, response);

	}

	public void updateReview() throws ServletException, IOException {
		// TODO Auto-generated method stub
		Integer reviewId = Integer.parseInt(request.getParameter("reviewId"));
		String headline = request.getParameter("headline");
		String comment = request.getParameter("comment");

		Review review = reviewDAO.get(reviewId);
		review.setHeadline(headline);
		review.setComment(comment);

		reviewDAO.update(review);

		String message = "The review has been updated successfully";

		listAllReview(message);
	}

	public void deleteReview() throws ServletException, IOException {
		// TODO Auto-generated method stub
		Integer delteteId = Integer.parseInt(request.getParameter("id"));
		reviewDAO.delete(delteteId);

		String message = "The review has been deleted successfully";

		listAllReview(message);
	}

	public void showReviewForm() throws ServletException, IOException {
		// TODO Auto-generated method stub
		Integer bookId = Integer.parseInt(request.getParameter("book_id"));

		Book book = bookDAO.get(bookId);
		HttpSession session = request.getSession();
		session.setAttribute("book", book);

		Customer customer = (Customer) session.getAttribute("loggedCustomer");
		Review existReview = reviewDAO.finfByCustomerAndBook(customer.getCustomerId(), bookId);

		String targetPage = "frontend/review_form.jsp";
		
		if (existReview != null) {
			request.setAttribute("review", existReview);

			targetPage = "frontend/review_info.jsp";
		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(targetPage);
		dispatcher.forward(request, response);

	}

	public void submitReview() throws ServletException, IOException {
		// TODO Auto-generated method stub
		Integer bookId = Integer.parseInt(request.getParameter("bookId"));
		Integer rating = Integer.parseInt(request.getParameter("rating"));
		String headline = request.getParameter("headline");
		String comment = request.getParameter("comment");

		Review newReview = new Review();
		newReview.setHeadline(headline);
		newReview.setComment(comment);
		newReview.setRating(rating);

		Book book = new Book();
		book.setBookId(bookId);
		newReview.setBook(book);

		Customer customer = (Customer) request.getSession().getAttribute("loggedCustomer");
		newReview.setCustomer(customer);

		reviewDAO.create(newReview);

		String messagePage = "frontend/review_done.jsp";
		RequestDispatcher dispatcher = request.getRequestDispatcher(messagePage);
		dispatcher.forward(request, response);

	}

}
