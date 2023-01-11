package com.bookstore.service;

import java.io.IOException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.bookstore.controller.frontend.shoppingcart.ShoppingCart;
import com.bookstore.dao.OrderDAO;
import com.bookstore.entity.Book;
import com.bookstore.entity.BookOrder;
import com.bookstore.entity.Customer;
import com.bookstore.entity.OrderDetail;
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

  public void showCheckoutForm() throws ServletException, IOException {
    forwardToPage("frontend/checkout.jsp", request, response);
  }

  public void placeOrder() throws ServletException, IOException {
    String recipientName = request.getParameter("recipientName");
    String recipientPhone = request.getParameter("recipientPhone");
    String address = request.getParameter("address");
    String city = request.getParameter("city");
    String zipcode = request.getParameter("zipcode");
    String country = request.getParameter("country");
    String paymentMethod = request.getParameter("paymentMethod");
    String shippingAddress = address + ", " + city + ", " + zipcode + ", " + country;
    
    BookOrder order = new BookOrder();
    order.setRecipientName(recipientName);
    order.setRecipientPhone(recipientPhone);
    order.setShippingAddress(shippingAddress);
    order.setPaymentMethod(paymentMethod);
    
    HttpSession session = request.getSession();
    Customer customer = (Customer) session.getAttribute("loggedCustomer");
    order.setCustomer(customer);
    
    ShoppingCart shoppingCart = (ShoppingCart) session.getAttribute("cart");
    Map<Book, Integer> items = shoppingCart.getItems();
    
    Iterator<Book> iterator = items.keySet().iterator();
    
    Set<OrderDetail> orderDetails = new HashSet<>();
    
    /*
     *  Each element in the shopping cart, we create a new OrderDetail Object
     *  to store information about the book, quantity, subtotal, etc.
     */
    while (iterator.hasNext()) {
      Book book = iterator.next();
      Integer quantity = items.get(book);
      float subtotal = quantity * book.getPrice();
      
      OrderDetail orderDetail = new OrderDetail();
      orderDetail.setBook(book);
      orderDetail.setBookOrder(order);
      orderDetail.setQuantity(quantity);
      orderDetail.setSubtotal(subtotal);
      
      // Add each OrderDetail object to the Set collection
      orderDetails.add(orderDetail);
    }
    // Then pass this Set collection to BookOrder
    order.setOrderDetails(orderDetails);
    order.setTotal(shoppingCart.getTotalAmount());
    
    // After creating the order, clear the shopping cart
    orderDAO.create(order);
    
    shoppingCart.clear();
    
    String message = "Thank you. Your order has been received."
        + "We will deliver your books within a few days.";
    
    showMessageFrontend(message, request, response);
  }

  public void listOrderByCustomer() throws ServletException, IOException {
    HttpSession session = request.getSession();
    Customer customer = (Customer) session.getAttribute("loggedCustomer");
    List<BookOrder> listOrders = orderDAO.listByCustomer(customer.getCustomerId());
    
    request.setAttribute("listOrders", listOrders);
    
    forwardToPage("frontend/order_list.jsp", request, response);
  }

  public void showOrderDetailForCustomer() throws ServletException, IOException {
    Integer orderId = Integer.parseInt(request.getParameter("id"));
    
    HttpSession session = request.getSession();
    Customer customer = (Customer) session.getAttribute("loggedCustomer");
    
    BookOrder order = orderDAO.get(orderId, customer.getCustomerId());
    request.setAttribute("order", order);
    
    forwardToPage("frontend/order_detail.jsp", request, response);
  }
}
