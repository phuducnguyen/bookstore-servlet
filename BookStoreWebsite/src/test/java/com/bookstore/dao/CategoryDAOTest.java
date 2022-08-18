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

public class CategoryDAOTest extends BaseDAOTest {
	
	private static CategoryDAO categoryDAO;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		BaseDAOTest.setUpBeforeClass();
		categoryDAO = new CategoryDAO(entityManager);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		BaseDAOTest.tearDownAfterClass();
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
		Integer catId = 13;
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
}
