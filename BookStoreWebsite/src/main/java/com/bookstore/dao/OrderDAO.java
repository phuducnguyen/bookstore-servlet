package com.bookstore.dao;

import java.util.Date;
import java.util.List;
import com.bookstore.entity.BookOrder;

public class OrderDAO extends JpaDAO<BookOrder> implements GenericDAO<BookOrder> {

  @Override
  public BookOrder create(BookOrder order) {
    order.setOrderDate(new Date());
    order.setPaymentMethod("Cash On Delivery");
    order.setStatus("Processing");
    
    return super.create(order);
  }

  @Override
  public BookOrder update(BookOrder order) {
    return super.update(order);
  }

  @Override
  public BookOrder get(Object orderId) {
    return super.find(BookOrder.class, orderId);
  }

  @Override
  public void delete(Object id) {
    super.delete(BookOrder.class, id);
  }

  @Override
  public List<BookOrder> listAll() {
    return super.findWithNamedQuery("BookOrder.findAll");
  }

  @Override
  public long count() {
    return super.countWithNamedQuery("BookOrder.countAll");
  }
  
  public long countOrderDetailByBook(int bookId) {
    return super.countWithNamedQuery("BookOrder.countByBook", "bookId", bookId);
  }

}