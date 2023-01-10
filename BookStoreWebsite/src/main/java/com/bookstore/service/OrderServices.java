package com.bookstore.service;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bookstore.dao.OrderDAO;
import com.bookstore.entity.BookOrder;
import static com.bookstore.util.CommonUtility.*;

public class OrderServices {
  private OrderDAO orderDAO;
  private HttpServletRequest request;
  private HttpServletResponse response;

  public OrderServices(HttpServletRequest request, HttpServletResponse response) {
    super();
    this.request = request;
    this.response = response;
    this.orderDAO = new OrderDAO();
  }

  public void listAllOrder() throws ServletException, IOException {
    List<BookOrder> listOrders = orderDAO.listAll();
    
    request.setAttribute("listOrders", listOrders);
    
    forwardToPage("order_list.jsp", request, response);
  }

  public void viewOrderDetailForAdmin() throws ServletException, IOException {
    Integer orderId = Integer.parseInt(request.getParameter("id"));
    
    BookOrder order = orderDAO.get(orderId);
    
    if (order != null) {
      request.setAttribute("order", order);
      forwardToPage("order_detail.jsp", request, response);
    } else {
      String message = "Could not find order with ID " + orderId;
      showMessageBackend(message, request, response);
    }    
  }
}
