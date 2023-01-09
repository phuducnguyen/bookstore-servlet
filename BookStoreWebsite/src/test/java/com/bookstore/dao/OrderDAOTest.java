package com.bookstore.dao;

import static org.junit.Assert.*;
import java.util.HashSet;
import java.util.Set;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.bookstore.entity.Book;
import com.bookstore.entity.BookOrder;
import com.bookstore.entity.Customer;
import com.bookstore.entity.OrderDetail;
import com.bookstore.entity.OrderDetailId;

public class OrderDAOTest {
  private static OrderDAO orderDAO;
  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    orderDAO = new OrderDAO();
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    orderDAO.close();
  }

  @Test
  public void testCreateBookOrder() {
    BookOrder order = new BookOrder();
    Customer customer = new Customer();
    customer.setCustomerId(12);
    
    order.setCustomer(customer);
    order.setRecipientName("Phu Duc Nguyen");
    order.setRecipientPhone("18008181");
    order.setShippingAddress("69622 Villeurbanne cedex");
    
    Set<OrderDetail> orderDetails = new HashSet<>();
    OrderDetail orderDetail = new OrderDetail();
    
    Book book = new Book(32);
    orderDetail.setBook(book);
    orderDetail.setBookOrder(order);
    orderDetail.setQuantity(2);
    orderDetail.setSubtotal(80f);
    
    orderDetails.add(orderDetail);
    
    order.setOrderDetails(orderDetails);
    
    BookOrder savedOrder = orderDAO.create(order);
  
//    assertNotNull(savedOrder);
    assertTrue(savedOrder != null && savedOrder.getOrderDetails().size() > 0);
  }

  @Test
  public void testUpdateBookOrder() {
    fail("Not yet implemented");
  }

  @Test
  public void testGet() {
    fail("Not yet implemented");
  }

  @Test
  public void testDeleteObject() {
    fail("Not yet implemented");
  }

  @Test
  public void testListAll() {
    fail("Not yet implemented");
  }

  @Test
  public void testCount() {
    fail("Not yet implemented");
  }

}
