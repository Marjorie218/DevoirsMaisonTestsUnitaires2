package com.inti.formation.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MathematiqueServiceTest {
	@Autowired
	public MathematiqueService mathServ;
	
	@Test
	public void additionTest() {
//		MathematiqueService mathServ = new MathematiqueService();
//		long a = 5;
//		long b = 6;
//		long result = mathServ.addition(a, b);
		assertEquals(mathServ.addition(5, 6), 11);
	}

}
