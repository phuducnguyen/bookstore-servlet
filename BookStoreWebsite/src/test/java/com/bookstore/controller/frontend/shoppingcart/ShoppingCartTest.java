package com.bookstore.controller.frontend.shoppingcart;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;
import com.bookstore.entity.Book;

public class ShoppingCartTest {
  private static ShoppingCart cart;
  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    cart = new ShoppingCart();
    Book book = new Book(1);
    
    book.setPrice(10.2f);
    
    // Initialize cart has 2 books
    cart.addItem(book);
    cart.addItem(book);
  }

  @Test
  public void testAddItem() {
    Map<Book, Integer> items = cart.getItems();
    int quatity = items.get(new Book(1));
    
    assertEquals(2, quatity);
  }

  @Test
  public void testRemoveItem1() {
    cart.removeItem(new Book(1));
    
    assertTrue(cart.getItems().isEmpty());
  }
  
  @Test
  public void testRemoveItem2() {
    Book book2 = new Book(2);
    cart.removeItem(new Book(2));
    
    assertNull(cart.getItems().get(book2));
  }
  
  @Test
  public void testGetTotalQuantity() {
    Book book3 = new Book(3);
    cart.addItem(book3);
    cart.addItem(book3);
    cart.addItem(book3);
    
    assertEquals(5, cart.getTotalQuantity());
  }
  
  @Test
  public void testGetTotalAmount1() {
    ShoppingCart cart = new ShoppingCart();
    
    assertEquals(0.0f, cart.getTotalAmount(), 0.0f);
  }
  
  @Test
  public void testGetTotalAmount2() {
    System.out.println(cart.getTotalAmount());
    
    assertEquals(20.4f, cart.getTotalAmount(), 0.0f);
  }
  
  @Test
  public void testClearCart() {
    cart.clear();
    
    assertEquals(0, cart.getTotalQuantity());
  }
  
  @Test
  public void testUpdateCart() {
    ShoppingCart cart = new ShoppingCart();
    Book book1 = new Book(32);
    Book book2 = new Book(34);
    
    cart.addItem(book1);
    cart.addItem(book2);
    
    int[] bookIds = {32, 34};
    int[] quantitites = {3, 4};
    
    cart.updateCart(bookIds, quantitites);
    
    assertEquals(7, cart.getTotalQuantity());
  }

}
