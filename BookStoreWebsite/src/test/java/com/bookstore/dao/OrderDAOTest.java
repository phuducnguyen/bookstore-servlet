package com.bookstore.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.bookstore.entity.Book;
import com.bookstore.entity.BookOrder;
import com.bookstore.entity.Customer;
import com.bookstore.entity.OrderDetail;

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
  public void testCreateBookOrder1() {
    BookOrder order = new BookOrder();
    Customer customer = new Customer();
    customer.setCustomerId(13);
    
    order.setCustomer(customer);
    order.setRecipientName("Phu Duc Nguyen");
    order.setRecipientPhone("18008181");
    order.setShippingAddress("69622 Villeurbanne cedex, Lyon, France");
    
    Set<OrderDetail> orderDetails = new HashSet<>();
    OrderDetail orderDetail = new OrderDetail();
    
    Book book = new Book(32);
    orderDetail.setBook(book);
    orderDetail.setBookOrder(order);
    orderDetail.setQuantity(2);
    orderDetail.setSubtotal(80f);
    
    orderDetails.add(orderDetail);
    
    order.setOrderDetails(orderDetails);
    
    orderDAO.create(order);
  
    assertTrue(order.getOrderId() > 0);
  }
  
  @Test
  public void testCreateBookOrder2() {
    BookOrder order = new BookOrder();
    Customer customer = new Customer();
    customer.setCustomerId(12);
    
    order.setCustomer(customer);
    order.setRecipientName("Phu Duc Nguyen");
    order.setRecipientPhone("18008181");
    order.setShippingAddress("69622 Villeurbanne cedex, Lyon, France");
    
    Set<OrderDetail> orderDetails = new HashSet<>();
    OrderDetail orderDetail1 = new OrderDetail();
    
    Book book1 = new Book(32);
    orderDetail1.setBook(book1);
    orderDetail1.setBookOrder(order);
    orderDetail1.setQuantity(2);
    orderDetail1.setSubtotal(80f);
    
    orderDetails.add(orderDetail1);
    
    Book book2 = new Book(34);
    OrderDetail orderDetail2 = new OrderDetail();
    orderDetail2.setBook(book2);
    orderDetail2.setBookOrder(order);
    orderDetail2.setQuantity(1);
    orderDetail2.setSubtotal(50.5f);
    
    orderDetails.add(orderDetail2);
    
    order.setOrderDetails(orderDetails);
    
    orderDAO.create(order);
  
    assertTrue(order.getOrderId() > 0);
  }

  @Test
  public void testUpdateBookOrderShippingAddress() {
    Integer orderId = 31;
    BookOrder order = orderDAO.get(orderId);
    order.setShippingAddress("New Shipping Address");
    
    orderDAO.update(order);
    
    BookOrder updatedOrder = orderDAO.get(orderId);
    
    assertEquals(order.getShippingAddress(), updatedOrder.getShippingAddress());
  }
  
  @Test
  public void testUpdateBookOrderDetail() {
    Integer orderId = 32;
    BookOrder order = orderDAO.get(orderId);
    
    Iterator<OrderDetail> iterator = order.getOrderDetails().iterator();
    
    // Retrieve data and update exist book information in the order
    while (iterator.hasNext()) {
      OrderDetail orderDetail = iterator.next();
      if (orderDetail.getBook().getBookId() == 34) {
        orderDetail.setQuantity(3);
        orderDetail.setSubtotal(151.5f);
      }
      
    }
    orderDAO.update(order);
    
//    BookOrder updatedOrder = orderDAO.get(orderId);

    iterator = order.getOrderDetails().iterator();
    
    // Data calculated by DEV
    int expectedQuantity = 3;
    float expectedSubtotal = 151.5f;
    int actualQuantity = 0;
    float actualSubtotal = 0;
    
    while (iterator.hasNext()) {
      OrderDetail orderDetail = iterator.next();
      if (orderDetail.getBook().getBookId() == 34) {
        actualQuantity = orderDetail.getQuantity();
        actualSubtotal = orderDetail.getSubtotal();
      }
    }
    
    assertEquals(expectedQuantity, actualQuantity);
    assertEquals(expectedSubtotal, actualSubtotal, 1e-5);
  }

  @Test
  public void testGet() {
    Integer orderId = 31;
    BookOrder order = orderDAO.get(orderId);
    
    assertEquals(1, order.getOrderDetails().size());
  }

  @Test
  public void testDeleteObject() {
    int orderId = 31;
    orderDAO.delete(orderId);
  
    BookOrder order = orderDAO.get(orderId);
    
    assertNull(order);
  }

  @Test
  public void testListAll() {
    List<BookOrder> listOrders = orderDAO.listAll();
    
    for (BookOrder order : listOrders) {
      System.out.println(order.getOrderId() + " - " + order.getCustomer().getFullname() 
          + " - " + order.getTotal() + " - " + order.getStatus());
    
      for (OrderDetail detail : order.getOrderDetails()) {
        Book book = detail.getBook();
        int quantity = detail.getQuantity();
        float subtotal = detail.getSubtotal();
        System.out.println("\t" + book.getTitle() + " - " + quantity + " - " + subtotal);
      }
    }
    
    assertTrue(listOrders.size() > 0);
  }

  @Test
  public void testCount() {
    long totalOrders = orderDAO.count();
    
    assertEquals(2, totalOrders);
  }

}
