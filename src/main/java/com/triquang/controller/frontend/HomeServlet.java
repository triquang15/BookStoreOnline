package com.triquang.controller.frontend;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.triquang.controller.BaseServlet;
import com.triquang.dao.BookDAO;
import com.triquang.dao.CategoryDAO;
import com.triquang.entity.Book;
import com.triquang.entity.Category;

/**
 * Servlet implementation class HomeServlet
 */
@WebServlet("")
public class HomeServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public HomeServlet() {
        super();
       
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		CategoryDAO categoryDAO = new CategoryDAO(entityManager);
		BookDAO bookDAO = new BookDAO(entityManager);
		
		List<Category> listCategories = categoryDAO.listAll();
		List<Book> listNewBooks = bookDAO.listNewBook();
		List<Book> listBestSellingBook = bookDAO.listBestSellingBook();
		List<Book> listMostFavoredBooks = bookDAO.listMostFavoredBooks();
		
		request.setAttribute("listNewBooks", listNewBooks);
		request.setAttribute("listCategories", listCategories);
		request.setAttribute("listBestSellingBook", listBestSellingBook);
		request.setAttribute("listMostFavoredBooks", listMostFavoredBooks);
		
		String homepage = "frontend/index.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(homepage);
		requestDispatcher.forward(request, response);
	}

}
