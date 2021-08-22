package com.triquang.dao;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TestHashPass {
	@Test
	public void testGenerateMD5() {
		String password = "1234567";
		String encryptedPassword = HashGenerator.generateMD5(password);
		
		System.out.println(encryptedPassword);
		
		assertTrue(true);
	}
}
