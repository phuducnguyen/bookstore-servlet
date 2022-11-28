package com.bookstore.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.bookstore.entity.Category;

public class CategoryDAOTest {
	
	private static CategoryDAO categoryDAO;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		categoryDAO = new CategoryDAO();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	    categoryDAO.close();
	}

	@Test
	public void testCreateCategory() {
		Category newCat = new Category("Health");
		Category category = categoryDAO.create(newCat);
		
		assertTrue(category != null && category.getCategoryId() > 0);
	}
	
	@Test
	public void testUpdateCategory() {
		Category cat = new Category("Java Core");
		cat.setCategoryId(11);
		
		Category category = categoryDAO.update(cat);
		
		assertEquals(cat.getName(), category.getName());
	}
	
	@Test
	public void testGet() {
		Integer catId = 11;
		Category cat = categoryDAO.get(catId);
		
		assertNotNull(cat);
	}
	
	@Test
	public void testDeleteCategory() {
		Integer catId = 15;
		categoryDAO.delete(catId);
		
		Category cat = categoryDAO.get(catId);
		
		assertNull(cat);
	}
	
	@Test
	public void testListAll() {
		List<Category> listCategories = categoryDAO.listAll();
		
		listCategories.forEach(c -> System.out.println(c.getName() + ", "));
		
		assertTrue(listCategories.size() > 0);
	}
	
	@Test
	public void testCount() {
		long totalCategories = categoryDAO.count();
		
		assertEquals(2, totalCategories);
	}
	
	@Test
	public void testFindByName() {
		String name = "Java Core";
		Category category = categoryDAO.findByName(name);
		
		assertNotNull(category);
	}
	
	@Test
	public void testFindByNameNotFound() {
		String name = "Java Core1";
		Category category = categoryDAO.findByName(name);
		
		assertNull(category);
	}
}
