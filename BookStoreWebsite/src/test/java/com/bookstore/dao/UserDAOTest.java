package com.bookstore.dao;

import static org.junit.Assert.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bookstore.entity.Users;

public class UserDAOTest {
	
	private static EntityManagerFactory entityManagerFactory;
	private static EntityManager entityManager;
	
	private static String projectName = "BookStoreWebsite";
	private static UserDAO userDAO;
	
	@BeforeClass
	public static void setUpClass() {
		entityManagerFactory = Persistence.createEntityManagerFactory(projectName);
		entityManager = entityManagerFactory.createEntityManager();
	
		userDAO = new UserDAO(entityManager);
	}
	
	@Test
	public void testCreateUsers() {
		Users user = new Users();
		user.setEmail("marvin@email.com");
		user.setFullName("Marvin Ng");
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
		user.setEmail("jarvis@system.com");
		user.setFullName("J.A.R.V.I.S");
		user.setPassword("mysecret");
		
		user = userDAO.update(user);
		String expected = "mysecret";
		String actual = user.getPassword();
		
		assertEquals(expected, actual);
	}
	
	@AfterClass
	public static void tearDownClass() {
		entityManager.close();
		entityManagerFactory.close();
	}
	
}
