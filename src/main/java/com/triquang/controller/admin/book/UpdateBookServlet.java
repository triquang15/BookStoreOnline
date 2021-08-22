package com.triquang.controller.admin.book;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.triquang.controller.BaseServlet;
import com.triquang.service.BookServices;

/**
 * Servlet implementation class UpdateBookServlet
 */
@WebServlet("/admin/update_book")
@MultipartConfig(fileSizeThreshold = 1024 * 10, // 10KB
maxFileSize = 1024 * 300, // 300MB
maxRequestSize = 1024 * 1024 // 1MB
)
public class UpdateBookServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateBookServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		BookServices bookServices = new BookServices(entityManager, request, response);
		bookServices.updateteBook();
	}

}
