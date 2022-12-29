package com.bookstore.dao;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.bookstore.entity.Book;
import com.bookstore.entity.Customer;
import com.bookstore.entity.Review;

public class ReviewDAOTest {

  private static ReviewDAO reviewDao;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    reviewDao = new ReviewDAO();
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    reviewDao.close();
  }

  @Test
  public void testCreateReview() {
    Review review = new Review();
    Book book = new Book();
    Customer customer = new Customer();

    // Get sample data from database
    book.setBookId(36);
    customer.setCustomerId(16); 

    review.setBook(book);
    review.setCustomer(customer);

    review.setHeadline("Headline testing");
    review.setRating(4);
    review.setComment("Must read book!!!");

    Review savedReview = reviewDao.create(review);

    assertTrue(savedReview.getReviewId() > 0);
  }

  @Test
  public void testUpdateReview() {
    Integer reviewId = 15;
    Review review = reviewDao.get(reviewId);

    review.setHeadline("Exellent book");

    Review updatedReview = reviewDao.update(review);

    assertEquals(review.getHeadline(), updatedReview.getHeadline());
  }

  @Test
  public void testGet() {
    Integer reviewId = 15;
    Review review = reviewDao.get(reviewId);

    assertNotNull(review);
  }

  @Test
  public void testDeleteReview() {
    int reviewId = 16;
    reviewDao.delete(reviewId);
    
    Review review = reviewDao.get(reviewId);
    
    assertNull(review);
  }

  @Test
  public void testListAll() {
    List<Review> listReview = reviewDao.listAll();

    for (Review review : listReview) {
      System.out.println(review.getReviewId() + " - " + review.getBook().getTitle() + " - "
          + review.getCustomer().getFullname() + " - " + review.getHeadline() + " - "
          + review.getRating());
    }

    assertTrue(listReview.size() > 0);
  }

  @Test
  public void testCount() {
    long totalReviews = reviewDao.count();
    System.out.println("Total reviews: " + totalReviews);

    assertTrue(totalReviews > 0);
  }

}
