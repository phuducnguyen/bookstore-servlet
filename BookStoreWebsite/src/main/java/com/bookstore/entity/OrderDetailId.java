package com.bookstore.entity;

import java.util.Objects;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class OrderDetailId implements java.io.Serializable {

  private Book book;
  private BookOrder bookOrder;

  public OrderDetailId() {}

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "book_id", insertable = false, updatable = false, nullable = false)
  public Book getBook() {
    return this.book;
  }

  public void setBook(Book book) {
    this.book = book;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id", insertable = false, updatable = false, nullable = false)
  public BookOrder getBookOrder() {
    return this.bookOrder;
  }

  public void setBookOrder(BookOrder bookOrder) {
    this.bookOrder = bookOrder;
  }

  @Override
  public int hashCode() {
    return Objects.hash(book, bookOrder);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    OrderDetailId other = (OrderDetailId) obj;
    return Objects.equals(book, other.book) && Objects.equals(bookOrder, other.bookOrder);
  }

}
