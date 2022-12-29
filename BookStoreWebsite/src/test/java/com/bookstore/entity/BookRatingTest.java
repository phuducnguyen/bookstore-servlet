package com.bookstore.entity;

import static org.junit.Assert.assertEquals;
import java.util.HashSet;
import java.util.Set;
import org.junit.Test;

public class BookRatingTest {
  
  /*  Comparison floating-point numbers reference
   *  https://floating-point-gui.de/errors/comparison/#look-out-for-edge-cases */
  
  @Test
  public void testAverageRating1() {
    Book book = new Book();
    Set<Review> reviews = new HashSet<>();
    
    Review review1 = new Review();
    review1.setRating(5);
    reviews.add(review1);
    
    book.setReviews(reviews);
    
    float averageRating = book.getAvarageRating();
    
    assertEquals(5.0, averageRating, 1e-9);
  }
  
  @Test
  public void testAverageRating2() {
    Book book = new Book();
    
    float averageRating = book.getAvarageRating();
    assertEquals(0.0, averageRating, 1e-9);
  }
  
  @Test
  public void testAverageRating3() {
    Book book = new Book();
    Set<Review> reviews = new HashSet<>();
    
    Review review1 = new Review();
    Review review2 = new Review();
    Review review3 = new Review();

    review1.setRating(5);
    review2.setRating(4);
    review3.setRating(3);
        
    reviews.add(review2);
    reviews.add(review1);
    reviews.add(review3);
    
    book.setReviews(reviews);
    
    float averageRating = book.getAvarageRating();
    
    assertEquals(4.0, averageRating, 1e-9);
  }
}
