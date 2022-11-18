package com.bookstore.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.bookstore.dao.BookDAO;
import com.bookstore.dao.CategoryDAO;
import com.bookstore.entity.Book;
import com.bookstore.entity.Category;

public class BookServices {
	private EntityManager entityManager;
	private BookDAO bookDAO;
	private CategoryDAO categoryDAO;
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public BookServices(EntityManager entityManager, 
			HttpServletRequest request, HttpServletResponse response) {
		this.entityManager = entityManager;
		this.request = request;
		this.response = response;
		
		bookDAO = new BookDAO(entityManager);
		categoryDAO = new CategoryDAO(entityManager);
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
		// Get all the Book's object values from request parameters
		Integer categoryId = Integer.parseInt(request.getParameter("category"));
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String description = request.getParameter("description");
		String isbn = request.getParameter("isbn");
		float price = Float.parseFloat(request.getParameter("price"));
		
		Book existBook = bookDAO.findByTitle(title);
		
		// Show error message when duplicate title of book
		if (existBook != null) {
			String message = "Could not create new book because the title '" 
						+ title + "' already exists";
			listBooks(message);
			return;
		}
		
		// The pattern describing the date and time format
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
		Date publishDate = null;
		
		try {
			publishDate = dateFormat.parse(request.getParameter("publishDate"));
		} catch (ParseException ex) {
			ex.printStackTrace();
			throw new ServletException("Error parsing publish date (Format is MM/dd/yyyy)");
		}
		
		System.out.println("Category ID: " + categoryId);
		System.out.println("Title: " + title);
		System.out.println("Author: " + author);
		System.out.println("Description: " + description);
		System.out.println("ISBN: " + isbn);
		System.out.println("Price: " + price);
		System.out.println("Publish Date: " + publishDate);
		
		Book newBook = new Book();
		// Set all the values for each field
		newBook.setTitle(title);
		newBook.setAuthor(author);
		newBook.setDescription(description);
		newBook.setIsbn(isbn);
		newBook.setPublishDate(publishDate);
		newBook.setPrice(price);
		
		Category category = categoryDAO.get(categoryId);
		newBook.setCategory(category);
		
		Part part = request.getPart("bookImage");
		
		// Read the data of file uploaded in the form into an array of bytes
		if (part != null && part.getSize() > 0) {
			long size = part.getSize();
			byte[] imageBytes = new byte[(int) size];
			
			InputStream inputStream = part.getInputStream();
			inputStream.read(imageBytes);
			inputStream.close();
			
			newBook.setImage(imageBytes);
		}
		
		Book createdBook = bookDAO.create(newBook);
		
		// If new book created, show successful message
		if (createdBook.getBookId() > 0) {
			String message = "A new book has been created successfully.";
			listBooks(message);
		}
	}

	public void editBook() throws ServletException, IOException {
		Integer bookId = Integer.parseInt(request.getParameter("id"));
		Book book = bookDAO.get(bookId);
		List<Category> listCategories = categoryDAO.listAll();
		
		request.setAttribute("book", book);
		request.setAttribute("listCategories", listCategories);
		
		String editPage = "book_form.jsp";
		RequestDispatcher requestDispatcher = request.getRequestDispatcher(editPage);
		requestDispatcher.forward(request, response);
	}
}
