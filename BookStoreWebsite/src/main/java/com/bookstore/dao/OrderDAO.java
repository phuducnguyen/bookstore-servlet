package com.bookstore.dao;

import java.util.Date;
import java.util.List;
import com.bookstore.entity.BookOrder;

public class OrderDAO extends JpaDAO<BookOrder> implements GenericDAO<BookOrder> {

  @Override
  public BookOrder create(BookOrder entity) {
    entity.setOrderDate(new Date());
    entity.setPaymentMethod("Cash On Delivery");
    entity.setStatus("Processing");
    
    return super.create(entity);
  }

  @Override
  public BookOrder update(BookOrder entity) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public BookOrder get(Object id) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public void delete(Object id) {
    // TODO Auto-generated method stub
    
  }

  @Override
  public List<BookOrder> listAll() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public long count() {
    // TODO Auto-generated method stub
    return 0;
  }

}
