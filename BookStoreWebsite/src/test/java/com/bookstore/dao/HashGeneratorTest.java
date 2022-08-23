package com.bookstore.dao;

import static org.junit.Assert.*;

import org.junit.Test;

public class HashGeneratorTest {

	@Test
	public void testGenerateMD5() {
		String password = "mysecret";
		String encryptedPassword = HashGeneratorUtils.generateMD5(password);
		
		System.out.println(encryptedPassword);
		
		assertTrue(true);
	}

	@Test
	public void testGenerateSHA1() {
		String password = "mysecret";
		String encryptedPassword = HashGeneratorUtils.generateSHA1(password);
		
		System.out.println(encryptedPassword);
		
		assertTrue(true);
	}
	
	@Test
	public void testGenerateSHA256() {
		String password = "mysecret";
		String encryptedPassword = HashGeneratorUtils.generateSHA256(password);
		
		System.out.println(encryptedPassword);
		
		assertTrue(true);
	}
}
