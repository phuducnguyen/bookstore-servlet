package com.bookstore.service;

import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bookstore.dao.CustomerDAO;
import com.bookstore.entity.Customer;

public class CustomerService {
  private HttpServletRequest request;
  private HttpServletResponse response;
  
  public CustomerService(HttpServletRequest request, HttpServletResponse response) {
    this.request = request;
    this.response = response;
  }
  
  public void listCustomers() throws ServletException, IOException {
    CustomerDAO customerDAO = new CustomerDAO();
    List<Customer> listCustomers = customerDAO.listAll();
    String listPage = "customer_list.jsp";
    
    request.setAttribute("listCustomers", listCustomers);
    
    RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);
    requestDispatcher.forward(request, response);
  }
}
