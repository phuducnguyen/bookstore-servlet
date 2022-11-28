package com.bookstore.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bookstore.entity.Users;

public class UserDAOTest {

  private static UserDAO userDAO;

  @BeforeClass
  public static void setUpClass() throws Exception {
    userDAO = new UserDAO();
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
    userDAO.close();
  }

  @Test
  public void testCreateUsers() {
    Users user = new Users();
    user.setEmail("michele@email.com");
    user.setFullName("Michele Barfety");
    user.setPassword("79797979");

    user = userDAO.create(user);

    assertTrue(user.getUserId() > 0);
  }

  @Test(expected = PersistenceException.class)
  public void testCreateUsersFieldsNotSet() {
    Users user = new Users();

    user = userDAO.create(user);
  }

  @Test
  public void testUpdateUsers() {
    Users user = new Users();
    user.setUserId(20);
    user.setEmail("jarvis@system.net");
    user.setFullName("J.A.R.V.I.S");
    user.setPassword("mysecret");

    user = userDAO.update(user);
    String expected = "mysecret";
    String actual = user.getPassword();

    assertEquals(expected, actual);
  }

  @Test
  public void testUpdateUsersEncryptedPassword() {
    Integer userId = 20;
    Users user = userDAO.get(userId);

    String password = user.getPassword();
    String encryptedPassword = HashGeneratorUtils.generateSHA256(password);

    user.setPassword(encryptedPassword);
    user = userDAO.update(user);

    assertEquals(encryptedPassword, user.getPassword());
  }

  @Test
  public void testGetUsersFound() {
    Integer userId = 20;
    Users user = userDAO.get(userId);

    if (user != null) {
      System.out.println(user.getEmail());
    }

    assertNotNull(user);
  }

  @Test
  public void testGetUsersNotFound() {
    Integer userId = 99;
    Users user = userDAO.get(userId);

    assertNull(user);
  }

  @Test
  public void testDeleteUsers() {
    Integer userId = 23;
    userDAO.delete(userId);

    Users user = userDAO.get(userId);

    assertNull(user);
  }

  @Test(expected = EntityNotFoundException.class)
  public void testDeleteNonExistUsers() {
    Integer userId = 99;
    userDAO.delete(userId);
  }

  @Test
  public void testListAll() {
    List<Users> listUsers = userDAO.listAll();

    for (Users user : listUsers) {
      System.out.println(user.getEmail());
    }

    assertTrue(listUsers.size() > 0);
  }

  @Test
  public void testCount() {
    long totalUsers = userDAO.count();

    assertEquals(4, totalUsers);
  }

  @Test
  public void testFindByEmail() {
    String email = "michele@email.com";
    Users user = userDAO.findByEmail(email);

    assertNotNull(user);
  }

  @Test
  public void testCheckLoginSuccess() {
    String email = "michele@email.com";
    String password = "79797979";
    boolean loginResult = userDAO.checkLogin(email, password);

    assertTrue(loginResult);
  }

  @Test
  public void testCheckLoginFailed() {
    String email = "michele@email.com";
    String password = "mysecret";
    boolean loginResult = userDAO.checkLogin(email, password);

    assertFalse(loginResult);
  }
}
