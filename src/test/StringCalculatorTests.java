package test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import controller.StringCalculator;

class StringCalculatorTests {

	@Test
	public void test_add_step1() {
		try {
			assertEquals(4, StringCalculator.add("4"));
			assertEquals(6, StringCalculator.add("4,2"));
			assertEquals(356, StringCalculator.add("42,314"));
			assertNotEquals(1000, StringCalculator.add("42,314"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertThrows(NumberFormatException.class, () -> StringCalculator.add("42;24"));
		assertThrows(NumberFormatException.class, () -> StringCalculator.add("42,HelloWorld"));
	}

	@Test
	public void test_add_step2() {
		try {
			assertEquals(380, StringCalculator.add("42,24,314,0"));
			assertNotEquals(380, StringCalculator.add("42,24,314,1"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test_add_step3() {
		try {
			assertEquals(380, StringCalculator.add("42,24\n314,0"));
			assertNotEquals(380, StringCalculator.add("42,24\n314,1"));
			assertEquals(42, StringCalculator.add("42,\n"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void test_add_step4() {
		try {
			assertEquals(380, StringCalculator.add("//;42;24;314;0"));
			assertEquals(380, StringCalculator.add("//;42;24;314,0"));
			assertEquals(42, StringCalculator.add("//;42"));
			assertEquals(0,StringCalculator.add("//;"));
			assertNotEquals(380, StringCalculator.add("//;42;24;314;1"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		assertThrows(Exception.class, () -> StringCalculator.add("//.42.24\n314.0"));
		assertThrows(NumberFormatException.class, () -> StringCalculator.add("//HelloWorld42HelloWorld24\n314"));
		assertThrows(NumberFormatException.class, () -> StringCalculator.add("//HelloWorld42;24\n314"));
	}

	@Test
	public void test_add_step5() {
		Throwable exception = assertThrows(NumberFormatException.class, () -> StringCalculator.add("//;42;24\n314;-1"));
		assertEquals(exception.getMessage(), "Negatives not allowed : [-1]");
		
		exception = assertThrows(NumberFormatException.class, () -> StringCalculator.add("//;42;-24\n314;-1"));
		assertEquals(exception.getMessage(), "Negatives not allowed : [-24, -1]");
	}

}