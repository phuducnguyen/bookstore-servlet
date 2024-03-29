package com.bookstore.service;

import static com.bookstore.util.CommonUtility.forwardToPage;
import static com.bookstore.util.CommonUtility.showMessageBackend;
import static com.bookstore.util.CommonUtility.showMessageFrontend;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.bookstore.dao.CustomerDAO;
import com.bookstore.dao.OrderDAO;
import com.bookstore.dao.ReviewDAO;
import com.bookstore.entity.Customer;

public class CustomerServices {
  private CustomerDAO customerDAO;
  private HttpServletRequest request;
  private HttpServletResponse response;

  public CustomerServices(HttpServletRequest request, HttpServletResponse response) {
    super();
    this.request = request;
    this.response = response;
    this.customerDAO = new CustomerDAO();
  }

  public void listCustomers(String message) throws ServletException, IOException {
    List<Customer> listCustomers = customerDAO.listAll();
    request.setAttribute("listCustomers", listCustomers);

    if (message != null) {
      request.setAttribute("message", message);
    }

    forwardToPage("customer_list.jsp", message, request, response);
  }

  public void listCustomers() throws ServletException, IOException {
    listCustomers(null);
  }

  public void createCustomer() throws ServletException, IOException {
    String email = request.getParameter("email");
    Customer existCustomer = customerDAO.findByEmail(email);

    // Find customer that already signing this email
    if (existCustomer != null) {
      // If existed, display friendly error page
      String message = "Could not create new customer: the email " + email
          + " is already registered by another customer.";
      listCustomers(message);
    } else {
      // Otherwise, create new customer
      Customer newCustomer = new Customer();
      updateCustomerFieldsFromForm(newCustomer);
      customerDAO.create(newCustomer);

      String message = "New customer has been created successfully.";
      listCustomers(message);
    }
  }

  public void editCustomer() throws ServletException, IOException {
    // Get parameters from request
    Integer customerId = Integer.parseInt(request.getParameter("id"));
    Customer customer = customerDAO.get(customerId);

    if (customer == null) {
      String message = "Could not find customer with ID " + customerId;
      showMessageBackend(message, request, response);
    } else {
      /*
       * If left blank, the customer's password won't be updated Set password as null to make the
       * password is left blank by default Work with the encrypted password feature
       */
      customer.setPassword(null);
      request.setAttribute("customer", customer);
      forwardToPage("customer_form.jsp", request, response);
    }
  }

  public void updateCustomer() throws ServletException, IOException {
    // Get parameters from existing customers
    Integer customerId = Integer.parseInt(request.getParameter("customerId"));
    String email = request.getParameter("email");

    Customer customerByEmail = customerDAO.findByEmail(email);
    String message = null;

    // Check if existing customers or not have the same ID as the existing ones
    if (customerByEmail != null && customerByEmail.getCustomerId() != customerId) {
      message = "Could not update the customer ID " + customerId
          + " because there's an existing customer having the same email.";
    } else {
      // Otherwise, update this customer
      Customer customerById = customerDAO.get(customerId);
      updateCustomerFieldsFromForm(customerById);

      customerDAO.update(customerById);

      message = "The customer has been updated succesfully.";
    }

    listCustomers(message);
  }

  public void deleteCustomer() throws ServletException, IOException {
    // Get parameters from request
    Integer customerId = Integer.parseInt(request.getParameter("id"));
    Customer customer = customerDAO.get(customerId);

    // Delete existed customer or display error if non-exist customer
    if (customer != null) {
      ReviewDAO reviewDAO = new ReviewDAO();
      long reviewCount = reviewDAO.countByCustomer(customerId);

      /**
       * Improve feature: Check exit before deleting the customer 
       * If there are still reviews or orders associated with customer 
       * Administrator cannot delete this customer
       */
      if (reviewCount > 0) {
        // There are reviews posted by the customer, display an error message
        String message = "Could not delete customer with ID " + customerId
            + " because he/she posted reviews for books.";
        showMessageBackend(message, request, response);
      } else {
        OrderDAO orderDAO = new OrderDAO();
        long orderCount = orderDAO.countByCustomer(customerId);
        
        if (orderCount > 0) {
          String message = "Could not delete customer with ID " + customerId
              + "because he/she placed orders.";
          showMessageBackend(message, request, response);
        } else {
          customerDAO.delete(customerId);
          String message = "The customer has been deleted successfully.";
          listCustomers(message);          
        }
      }
    } else {
      String message = "Could not find customer with ID " + customerId + ", "
          + "or it has been deleted by another admin";
      showMessageBackend(message, request, response);
    }
  }

  public void registerCustomer() throws ServletException, IOException {
    String email = request.getParameter("email");
    Customer existCustomer = customerDAO.findByEmail(email);
    String message = "";

    if (existCustomer != null) {
      message =
          "Could not register. The email " + email + " is already registered by another customer.";
      listCustomers(message);
    } else {
      Customer newCustomer = new Customer();
      updateCustomerFieldsFromForm(newCustomer);

      customerDAO.create(newCustomer);

      message = "You have registered successfully! Thank you.<br/>"
          + "<a href='login'>Click here</a> to login";
    }

    showMessageFrontend(message, request, response);
  }

  private void updateCustomerFieldsFromForm(Customer customer) {
    // Retrieve the information of the new customer from request
    String email = request.getParameter("email");
    String password = request.getParameter("password");
    String fullName = request.getParameter("fullName");
    String phone = request.getParameter("phone");
    String address = request.getParameter("address");
    String city = request.getParameter("city");
    String zipCode = request.getParameter("zipCode");
    String country = request.getParameter("country");

    // Set this value to the properties of the Customer object
    if (email != null && !email.equals("")) {
      customer.setEmail(email);
    }

    System.out.println(password);
    if (password != null && !password.equals("")) {
      /*
       * Use in production Password is encrypted using MD5
       */
      // String encryptedPassword = HashGeneratorUtils.generateMD5(password);
      // customer.setPassword(encryptedPassword);
      // For development, test
      customer.setPassword(password);
    }
    customer.setFullname(fullName);
    customer.setPhone(phone);
    customer.setAddress(address);
    customer.setCity(city);
    customer.setZipcode(zipCode);
    customer.setCountry(country);
  }

  public void showLogin() throws ServletException, IOException {
    forwardToPage("frontend/login.jsp", request, response);
  }

  public void doLogin() throws ServletException, IOException {
    // Read email and password from the request
    String email = request.getParameter("email");
    String password = request.getParameter("password");

    Customer customer = customerDAO.checkLogin(email, password);

    if (customer == null) {
      // Store this message in the request attribute
      String message = "Login failed. Please check your email and password.";
      request.setAttribute("message", message);

      showLogin();
    } else {
      /* In case the customer has logged in successfully */
      // Set a value in the session attribute here
      HttpSession session = request.getSession();
      Object objRedirectURL = session.getAttribute("redirectURL");

      session.setAttribute("loggedCustomer", customer);

      if (objRedirectURL != null) {
        String redirectURL = (String) objRedirectURL;
        session.removeAttribute(redirectURL);
        response.sendRedirect(redirectURL);
      } else {
        showCustomerProfile();

      }

    }
  }

  public void showCustomerProfile() throws ServletException, IOException {
    forwardToPage("frontend/customer_profile.jsp", request, response);
  }

  public void showCustomerProfileEditForm() throws ServletException, IOException {
    forwardToPage("frontend/edit_profile.jsp", request, response);
  }

  public void updateCustomerProfile() throws ServletException, IOException {
    // Retrieve the Customer Object from the session
    Customer customer = (Customer) request.getSession().getAttribute("loggedCustomer");

    // Update current values from the form
    updateCustomerFieldsFromForm(customer);
    customerDAO.update(customer);

    showCustomerProfile();
  }
}
