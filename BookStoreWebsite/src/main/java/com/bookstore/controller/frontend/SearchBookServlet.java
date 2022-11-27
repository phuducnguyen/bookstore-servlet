package com.bookstore.controller.frontend;

import com.bookstore.controller.BaseServlet;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/search_book")
public class SearchBookServlet extends BaseServlet {
	private static final long serialVersionUID = 1L;

    public SearchBookServlet() {
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
	
	}
}
