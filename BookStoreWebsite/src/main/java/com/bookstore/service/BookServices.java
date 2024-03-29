package com.bookstore.service;

import static com.bookstore.util.CommonUtility.*;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import com.bookstore.dao.BookDAO;
import com.bookstore.dao.CategoryDAO;
import com.bookstore.dao.OrderDAO;
import com.bookstore.entity.Book;
import com.bookstore.entity.Category;

public class BookServices {
  private BookDAO bookDAO;
  private CategoryDAO categoryDAO;
  private HttpServletRequest request;
  private HttpServletResponse response;

  public BookServices(HttpServletRequest request, HttpServletResponse response) {
    this.request = request;
    this.response = response;

    bookDAO = new BookDAO();
    categoryDAO = new CategoryDAO();
  }

  public void listBooks() throws ServletException, IOException {
    listBooks(null);
  }

  public void listBooks(String message) throws ServletException, IOException {
    List<Book> listBooks = bookDAO.listAll();
    request.setAttribute("listBooks", listBooks);

    if (message != null) {
      request.setAttribute("message", message);
    }

    String listPage = "book_list.jsp";
    RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);
    requestDispatcher.forward(request, response);
  }

  public void showNewBookForm() throws ServletException, IOException {
    List<Category> listCategories = categoryDAO.listAll();
    request.setAttribute("listCategories", listCategories);

    String newPage = "book_form.jsp";
    RequestDispatcher requestDispatcher = request.getRequestDispatcher(newPage);
    requestDispatcher.forward(request, response);
  }

  public void createBook() throws ServletException, IOException {
    String title = request.getParameter("title");

    Book existBook = bookDAO.findByTitle(title);

    // Show error message when duplicate title of book
    if (existBook != null) {
      String message = "Could not create new book because the title '" + title + "' already exists";
      listBooks(message);
      return;
    }

    Book newBook = new Book();
    readBookFields(newBook);

    Book createdBook = bookDAO.create(newBook);

    // If new book created, show successful message
    if (createdBook.getBookId() > 0) {
      String message = "A new book has been created successfully.";
      listBooks(message);
    }
  }

  public void readBookFields(Book book) throws ServletException, IOException {
    String title = request.getParameter("title");
    String author = request.getParameter("author");
    String description = request.getParameter("description");
    String isbn = request.getParameter("isbn");
    float price = Float.parseFloat(request.getParameter("price"));

    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    Date publishDate = null;

    try {
      publishDate = dateFormat.parse(request.getParameter("publishDate"));
    } catch (ParseException ex) {
      ex.printStackTrace();
      throw new ServletException("Error parsing publish date (Format is MM/dd/yyyy)");
    }

    // Set all the values for each field
    book.setTitle(title);
    book.setAuthor(author);
    book.setDescription(description);
    book.setIsbn(isbn);
    book.setPublishDate(publishDate);
    book.setPrice(price);

    Integer categoryId = Integer.parseInt(request.getParameter("category"));
    Category category = categoryDAO.get(categoryId);
    book.setCategory(category);

    Part part = request.getPart("bookImage");

    // Read the data of file uploaded in the form into an array of bytes
    if (part != null && part.getSize() > 0) {
      long size = part.getSize();
      byte[] imageBytes = new byte[(int) size];

      InputStream inputStream = part.getInputStream();
      inputStream.read(imageBytes);
      inputStream.close();

      book.setImage(imageBytes);
    }
  }

  public void editBook() throws ServletException, IOException {
    Integer bookId = Integer.parseInt(request.getParameter("id"));
    Book book = bookDAO.get(bookId);
    String destPage = "book_form.jsp";

    // In case, managers try to edit a book that has been deleted
    if (book != null) {
      List<Category> listCategories = categoryDAO.listAll();

      request.setAttribute("book", book);
      request.setAttribute("listCategories", listCategories);
    } else {
      destPage = "message.jsp";
      String message = "Could not find book with ID " + bookId;
      request.setAttribute("message", message);
    }

    RequestDispatcher requestDispatcher = request.getRequestDispatcher(destPage);
    requestDispatcher.forward(request, response);
  }

  public void updateBook() throws ServletException, IOException {
    Integer bookId = Integer.parseInt(request.getParameter("bookId"));
    String title = request.getParameter("title");

    Book existBook = bookDAO.get(bookId);
    Book bookByTitle = bookDAO.findByTitle(title);

    if (bookByTitle != null && !existBook.equals(bookByTitle)) {
      String message = "Could not update book because there's another book having same title.";
      listBooks(message);
      return;
    }

    readBookFields(existBook);

    bookDAO.update(existBook);

    String message = "The book has been update successfully.";
    listBooks(message);
  }


  public void deleteBook() throws ServletException, IOException {
    Integer bookId = Integer.parseInt(request.getParameter("id"));
    Book book = bookDAO.get(bookId);

    if (book == null) {
      String message = "Could not find book with ID " + bookId + ", or it might have been deleted";
      showMessageBackend(message, request, response);
    } else {
      // Advanced feature: Cannot delete a book which has reviews posted by customers
      if (!book.getReviews().isEmpty()) {
        String message = "Could not delete the book with ID " + bookId + " because it has reviews";
        showMessageBackend(message, request, response);
      } else {
        // Enhance: Prevent delete book when orders associated
        OrderDAO orderDAO = new OrderDAO();
        long countByOrder = orderDAO.countOrderDetailByBook(bookId);
        
        if (countByOrder > 0) {
          String message = "Could not delete book with ID " + bookId 
              + "because there are orders associated.";
          showMessageBackend(message, request, response);
        } else {
          String message = "The book has been deleted succesfully.";
          bookDAO.delete(bookId);
          listBooks(message);
        }
      }
    }
  }

  public void listBooksByCategory() throws ServletException, IOException {
    int categoryId = Integer.parseInt(request.getParameter("id"));
    Category category = categoryDAO.get(categoryId);

    if (category == null) {
      String message = "Sorry, this category is not available.";
      request.setAttribute("message", message);
      request.getRequestDispatcher("frontend/message.jsp").forward(request, response);

      return;
    }

    List<Book> listBooks = bookDAO.listByCategory(categoryId);

    request.setAttribute("listBooks", listBooks);
    request.setAttribute("category", category);

    String listPage = "frontend/books_list_by_category.jsp";
    RequestDispatcher requestDispatcher = request.getRequestDispatcher(listPage);
    requestDispatcher.forward(request, response);
  }

  public void viewBookDetail() throws ServletException, IOException {
    Integer bookId = Integer.parseInt(request.getParameter("id"));
    Book book = bookDAO.get(bookId);

    String destPage = "frontend/book_detail.jsp";

    if (book != null) {
      request.setAttribute("book", book);
    } else {
      destPage = "frontend/message.jsp";
      String message = "Sorry, this book is not available.";
      request.setAttribute("message", message);
    }

    RequestDispatcher requestDispatcher = request.getRequestDispatcher(destPage);
    requestDispatcher.forward(request, response);
  }

  public void search() throws ServletException, IOException {
    String keyword = request.getParameter("keyword");
    List<Book> result = null;

    if (keyword.equals("")) {
      result = bookDAO.listAll();
    } else {
      result = bookDAO.search(keyword);
    }

    request.setAttribute("keyword", keyword);
    request.setAttribute("result", result);

    String resultPage = "frontend/search_result.jsp";
    RequestDispatcher requestDispatcher = request.getRequestDispatcher(resultPage);
    requestDispatcher.forward(request, response);
  }
}
