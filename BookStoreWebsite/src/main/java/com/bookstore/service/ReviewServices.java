package com.bookstore.service;

import static com.bookstore.util.CommonUtility.*;
import java.io.IOException;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.bookstore.dao.BookDAO;
import com.bookstore.dao.ReviewDAO;
import com.bookstore.entity.Book;
import com.bookstore.entity.Customer;
import com.bookstore.entity.Review;

public class ReviewServices {
  private ReviewDAO reviewDAO;
  private HttpServletRequest request;
  private HttpServletResponse response;

  public ReviewServices(HttpServletRequest request, HttpServletResponse response) {
    super();
    this.request = request;
    this.response = response;
    this.reviewDAO = new ReviewDAO();
  }

  public void listAllReview() throws ServletException, IOException {
    listAllReview(null);
  }

  public void listAllReview(String message) throws ServletException, IOException {
    List<Review> listReviews = reviewDAO.listAll();

    request.setAttribute("listReviews", listReviews);

    if (message != null) {
      request.setAttribute("message", message);
    }

    forwardToPage("review_list.jsp", request, response);
  }

  public void editReview() throws ServletException, IOException {
    Integer reviewId = Integer.parseInt(request.getParameter("id"));
    Review review = reviewDAO.get(reviewId);

    if (review == null) {
      String message = "Could not find review with ID " + reviewId;
      showMessageBackend(message, request, response);
    } else {
      request.setAttribute("review", review);
      forwardToPage("review_form.jsp", request, response);
    }
  }

  public void updateReview() throws ServletException, IOException {
    Integer reviewId = Integer.parseInt(request.getParameter("reviewId"));
    String headline = request.getParameter("headline");
    String comment = request.getParameter("comment");

    Review review = reviewDAO.get(reviewId);
    review.setHeadline(headline);
    review.setComment(comment);

    reviewDAO.update(review);

    String message = "The review has been updated successfully.";

    listAllReview(message);
  }

  public void deleteReview() throws ServletException, IOException {
    Integer reviewId = Integer.parseInt(request.getParameter("id"));
    Review review = reviewDAO.get(reviewId);
    String message = null;
    
    if (review == null) {
      message = "Could not find review with ID " + reviewId 
          + ", or it might have been deleted!";
      showMessageBackend(message, request, response);
    } else {
      reviewDAO.delete(reviewId);
      message = "The review has been deleted successfully.";
      listAllReview(message);
    }
  }

  public void showReviewForm() throws ServletException, IOException {
    Integer bookId = Integer.parseInt(request.getParameter("book_id"));
    BookDAO bookDao = new BookDAO();
    Book book = bookDao.get(bookId);
    
    HttpSession session = request.getSession();
    
    session.setAttribute("book", book);
    
    Customer customer = (Customer) session.getAttribute("loggedCustomer");
    
    Review existReview = reviewDAO.findByCustomerAndBook(customer.getCustomerId(), bookId);
    
    if (existReview != null) {
      request.setAttribute("review", existReview);
      forwardToPage("frontend/review_info.jsp", request, response);
    } else {
      forwardToPage("frontend/review_form.jsp", request, response);      
    }
  }

  public void submitReview() throws ServletException, IOException {
    Integer bookId = Integer.parseInt(request.getParameter("bookId"));
    Integer rating = Integer.parseInt(request.getParameter("rating"));
    String headline = request.getParameter("headline");
    String comment = request.getParameter("comment");
    
    Review newReview = new Review();
    Book book = new Book();
    Customer customer = (Customer) request.getSession().getAttribute("loggedCustomer");
    
    book.setBookId(bookId);
    newReview.setHeadline(headline);
    newReview.setComment(comment);
    newReview.setRating(rating);
    newReview.setBook(book);
    newReview.setCustomer(customer);
    
    reviewDAO.create(newReview);
    
    forwardToPage("frontend/review_done.jsp", request, response);  
  }
}
