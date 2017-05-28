package pi.uebung01.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import pi.uebung01.spec.ClassLiFo;
import pi.uebung01.spec.PushPopyException;
import pi.uebung01.spec.PushPopyInterface;

public class LifoTest {

	private PushPopyInterface<String> ppi;
	
	@Before
	public void beforeCreateStack() {
		ppi = new ClassLiFo<String>(5);
	
	}
	
	/**
	 * positiver push Test
	 */
	@Test
	public void testPush() {
		ppi.push("bla");
		ppi.push("bla");
		ppi.push("bla");
	}
	
	/**
	 * negativer push Test
	 */
	@Test(expected = IllegalArgumentException.class)
	public void testNullPush() {
		ppi.push(null);
	}
	
	/**
	 * Overflow Test
	 */
	@Test(expected = PushPopyException.class)
	public void testOverflow() {
		ppi.push("bla");
		ppi.push("bla");
		ppi.push("bla");
		ppi.push("bla");
		ppi.push("bla");		
		ppi.push("bla");
	}
	
	/**
	 * positiver pop Test
	 */
	@Test
	public void testPop() {
		ppi.push("bla");
		ppi.push("bli");
		ppi.push("blub");
		assertEquals("blub", ppi.pop());
		assertEquals("bli", ppi.pop());
	}
	
	/**
	 * negativer pop Test
	 */
	@Test(expected = PushPopyException.class)
	public void testEmptyPop(){
		assertEquals(null, ppi.pop());
	}
	
	/**
	 * positiver isFull test
	 */
	@Test
	public void testIsFull() {
		ppi.push("bla");
		ppi.push("bla");
		ppi.push("bla");
		ppi.push("bla");
		ppi.push("bla");
		equals(ppi.isFull());
	}
	
	/**
	 * negativer isFullTest
	 */
	@Test
	public void testIsNotFull(){
		assertEquals(false, ppi.isFull());
	}
	
	/**
	 * positiver isEmptyTest
	 */
	@Test
	public void testIsEmpty() {
		equals(ppi.isEmpty());
	}
	
	/**
	 * negativer isEmpty Test
	 */
	@Test
	public void testIsNotEmpty(){
		ppi.push("hi");
		assertEquals(false, ppi.isEmpty());
	}
	
}
