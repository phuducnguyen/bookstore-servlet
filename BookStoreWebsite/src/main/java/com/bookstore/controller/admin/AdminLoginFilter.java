package com.bookstore.controller.admin;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@WebFilter("/admin/*")
public class AdminLoginFilter extends HttpFilter implements Filter {

  private static final long serialVersionUID = 1L;

  public AdminLoginFilter() {
    super();
  }

  public void destroy() {}

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    // Cast this Request Object to the type of HttpServletRequest
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    // Get a session if it exists
    HttpSession session = httpRequest.getSession(false);

    // Check login state by Session
    boolean loggedIn = (session != null) && (session.getAttribute("userEmail") != null);
    String loginURI = httpRequest.getContextPath() + "/admin/login";

    // Indicate the request is a login request
    boolean loginRequest = httpRequest.getRequestURI().equals(loginURI);

    boolean loginPage = httpRequest.getRequestURI().endsWith("login.jsp");

    // It already logged in, skip to access login URL
    if (loggedIn && (loginRequest || loginPage)) {
      // Show the admin homepage
      RequestDispatcher dispatcher = request.getRequestDispatcher("/admin/");
      dispatcher.forward(request, response);
    } else if (loggedIn || loginRequest) {
      // If the current request URI is the loginURI
      // Allow it to pass in the filter chain
      System.out.println("user logged in");
      chain.doFilter(request, response);
    } else {
      System.out.println("user not logged in");
      RequestDispatcher dispatcher = request.getRequestDispatcher("login.jsp");
      dispatcher.forward(request, response);
    }
  }

  public void init(FilterConfig fConfig) throws ServletException {}

}
