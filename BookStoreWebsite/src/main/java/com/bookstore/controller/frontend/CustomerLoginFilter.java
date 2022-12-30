package com.bookstore.controller.frontend;

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

/**
 * Servlet Filter for Customer Login
 */
@WebFilter("/*")
public class CustomerLoginFilter extends HttpFilter implements Filter {
  
  // A set of URLs that need authentication
  private static final String[] LOGIN_REQUIRED_URLS = {
      "/view_profile", "/edit_profile", "/update_profile", "/write_review"
  };
  public CustomerLoginFilter() {}

  public void destroy() {}

  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
      throws IOException, ServletException {
    /* Identify if the customer has logged in or not */
    // Checking an attribute named `loggedCustomer` in the session
    HttpServletRequest httpRequest = (HttpServletRequest) request;
    HttpSession session = httpRequest.getSession(false);
    
    String path = httpRequest.getRequestURI().substring(httpRequest.getContextPath().length());
    
    // The request is coming to the admin section (back-end)
    // Continue do Filter, FilterChain
    if (path.startsWith("/admin/")) {
      chain.doFilter(request, response);
      return;
    }
     
    boolean loggedIn = session != null && session.getAttribute("loggedCustomer") != null;
    
    String requestURL = httpRequest.getRequestURL().toString();
    
    System.out.println("Path: " + path);
    System.out.println("loggedIn: " + loggedIn);
    
    // Prevent unauthorized access to the pages that require authentication
    if (!loggedIn && isLoginRequired(requestURL)) {
      String queryString = httpRequest.getQueryString();
      String redirectURL = requestURL;
      
      // After logged in successfully, the website should redirect to this URL again  
      if (queryString != null) {
        redirectURL = redirectURL.concat("?").concat(queryString);
      }
      
      // Use for redirect to previous pages that feature required customer login
      session.setAttribute("redirectURL", redirectURL);
      
      String loginPage = "frontend/login.jsp";
      RequestDispatcher dispatcher = httpRequest.getRequestDispatcher(loginPage);
      dispatcher.forward(httpRequest, response);
    } else {
      chain.doFilter(request, response);      
    }
  }
  
  private boolean isLoginRequired(String requestURL) {
    for (String loginRequiredURL : LOGIN_REQUIRED_URLS) {
      if (requestURL.contains(loginRequiredURL)) {
        return true;
      }
    }
    
    return false;
  }

  public void init(FilterConfig fConfig) throws ServletException {}

}
