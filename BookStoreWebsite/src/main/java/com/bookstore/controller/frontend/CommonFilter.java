package com.bookstore.controller.frontend;

import java.io.IOException;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import com.bookstore.dao.CategoryDAO;
import com.bookstore.entity.Category;

@WebFilter("/*")
public class CommonFilter extends HttpFilter implements Filter {

  private final CategoryDAO categoryDAO;

  public CommonFilter() {
    categoryDAO = new CategoryDAO();
  }

  public void destroy() {
  }

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
    
    // When the request comes to the front-end only
    // Or not ADMIN role
    if (!path.startsWith("/admin/")) {
      // Set the necessary attribute 
      List<Category> listCategories = categoryDAO.listAll();
      request.setAttribute("listCategories", listCategories);
    }
    chain.doFilter(request, response);
  }

  public void init(FilterConfig fConfig) throws ServletException {}

  
  
}
