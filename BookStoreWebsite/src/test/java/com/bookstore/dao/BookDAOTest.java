package com.bookstore.dao;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bookstore.entity.Book;
import com.bookstore.entity.Category;

public class BookDAOTest {

  private static BookDAO bookDAO;

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    bookDAO = new BookDAO();
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
    bookDAO.close();
  }

  @Test
  public void testCreateBook() throws ParseException, IOException {
    Book newBook = new Book();

    Category category = new Category("Advanced Java");
    category.setCategoryId(1);
    newBook.setCategory(category);

    newBook.setTitle("Effective Java (2nd Edition)");
    newBook.setAuthor("Joshua Bloch");
    newBook.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit");
    newBook.setPrice(38.87f);
    newBook.setIsbn("0321356683");

    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    Date publishDate = dateFormat.parse("05/28/2008");
    newBook.setPublishDate(publishDate);

    // Get Image's URI manually
    String imagePath = "/home/martin/dummy-data-books/books/Effective Java.jpg";

    // Read all the bytes from a Image file
    byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
    newBook.setImage(imageBytes);

    Book createdBook = bookDAO.create(newBook);

    assertTrue(createdBook.getBookId() > 0);
  }

  @Test
  public void testCreate2ndBook() throws ParseException, IOException {
    Book newBook = new Book();

    Category category = new Category("Java Core");
    category.setCategoryId(2);
    newBook.setCategory(category);

    newBook.setTitle("Java 8 in Action");
    newBook.setAuthor("Alan Mycroft");
    newBook.setDescription(
        "Java 8 in Action is a clearly written guide to the new features of Java 8");
    newBook.setPrice(36.72f);
    newBook.setIsbn("1617291994");

    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    Date publishDate = dateFormat.parse("08/28/2014");
    newBook.setPublishDate(publishDate);

    // Get Image's URI manually
    String imagePath = "/home/martin/dummy-data-books/books/Java 8 in Action.jpg";

    // Read all the bytes from a Image file
    byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
    newBook.setImage(imageBytes);

    Book createdBook = bookDAO.create(newBook);

    assertTrue(createdBook.getBookId() > 0);
  }

  @Test
  public void testUpdateBook() throws IOException, ParseException {
    Book existBook = new Book();
    existBook.setBookId(1);

    Category category = new Category("Java Core");
    category.setCategoryId(2);
    existBook.setCategory(category);

    existBook.setTitle("Effective Java (3rd Edition)");
    existBook.setAuthor("Joshua Bloch");
    existBook.setDescription("Lorem ipsum dolor sit amet, consectetur adipiscing elit");
    existBook.setPrice(40f);
    existBook.setIsbn("0321356683");

    DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
    Date publishDate = dateFormat.parse("05/28/2008");
    existBook.setPublishDate(publishDate);

    // Get Image's URI manually
    String imagePath = "/home/martin/dummy-data-books/books/Effective Java.jpg";

    // Read all the bytes from a Image file
    byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
    existBook.setImage(imageBytes);

    Book updatedBook = bookDAO.update(existBook);

    assertEquals(updatedBook.getTitle(), "Effective Java (3rd Edition)");
  }

  @Test
  public void testGetBookFail() {
    Integer bookId = 99;
    Book book = bookDAO.get(bookId);

    assertNull(book);
  }

  @Test
  public void testGetBookSuccess() {
    Integer bookId = 2;
    Book book = bookDAO.get(bookId);

    assertNotNull(book);
  }

  @Test(expected = EntityNotFoundException.class)
  public void testDeleteBookFail() {
    Integer bookId = 100;
    bookDAO.delete(bookId);
  }

  @Test
  public void testListAll() {
    List<Book> listBooks = bookDAO.listAll();

    for (Book aBook : listBooks) {
      System.out.println(aBook.getTitle() + " - " + aBook.getAuthor());
    }

    assertFalse(listBooks.isEmpty());
  }

  @Test
  public void testFindByTitleNotExist() {
    String title = "Thinking in Java";
    Book book = bookDAO.findByTitle(title);

    assertNull(book);
  }

  @Test
  public void testFindByTitleExist() {
    String title = "Java 8 in Action";
    Book book = bookDAO.findByTitle(title);

    System.out.println(book.getAuthor());
    System.out.println(book.getPrice());

    assertNotNull(book);
  }

  @Test
  public void testCount() {
    long totalBooks = bookDAO.count();

    assertEquals(2, totalBooks);
  }

  @Test
  public void testDeleteBookSuccess() {
    Integer bookId = 1;
    bookDAO.delete(bookId);

    assertTrue(true);
  }

  @Test
  public void testListNewBooks() {
    List<Book> listNewBooks = bookDAO.listNewBooks();
    for (Book aBook : listNewBooks) {
      System.out.println(aBook.getTitle() + " - " + aBook.getPublishDate());
    }

    assertEquals(4, listNewBooks.size());
  }

  @Test
  public void testSearchBookInTitle() {
    String keyword = "Java";
    List<Book> result = bookDAO.search(keyword);

    for (Book book : result) {
      System.out.println(book.getTitle());
    }

    assertEquals(6, result.size());
  }

  @Test
  public void testSearchBookInAuthor() {
    String keyword = "Kathy";
    List<Book> result = bookDAO.search(keyword);

    for (Book book : result) {
      System.out.println(book.getTitle());
    }

    assertEquals(2, result.size());
  }

  @Test
  public void testSearchBookInDescription() {
    String keyword = "The Big Picture";
    List<Book> result = bookDAO.search(keyword);

    for (Book book : result) {
      System.out.println(book.getTitle());
    }

    assertEquals(1, result.size());
  }

  @Test
  public void testListByCategory() {
    // Get the existing book that belongs to the category ID
    int categoryId = 12;

    List<Book> listBooks = bookDAO.listByCategory(categoryId);

    assertTrue(listBooks.size() > 0);
  }
}
