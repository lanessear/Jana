package pi.uebung01.test;

import static org.junit.Assert.*;

import org.junit.Test;

import pi.uebung01.Straight;
import pi.uebung01.Direction;

public class StraightTest {
	
	@Test
	public void testNorth() {
		Straight straight = new Straight(Direction.NORTH);
		assertEquals(straight.hasExit(Direction.NORTH), true);
		assertEquals(straight.hasExit(Direction.EAST), false);
		assertEquals(straight.hasExit(Direction.SOUTH), true);
		assertEquals(straight.hasExit(Direction.WEST), false);
	}
	
	@Test
	public void testEast() {
		Straight straight = new Straight(Direction.EAST);
		assertEquals(straight.hasExit(Direction.NORTH), false);
		assertEquals(straight.hasExit(Direction.EAST), true);
		assertEquals(straight.hasExit(Direction.SOUTH), false);
		assertEquals(straight.hasExit(Direction.WEST), true);
	}
	
	@Test
	public void testSouth() {
		Straight straight = new Straight(Direction.SOUTH);
		assertEquals(straight.hasExit(Direction.NORTH), true);
		assertEquals(straight.hasExit(Direction.EAST), false);
		assertEquals(straight.hasExit(Direction.SOUTH), true);
		assertEquals(straight.hasExit(Direction.WEST), false);
	}
	
	@Test
	public void testWest() {
		Straight straight = new Straight(Direction.WEST);
		assertEquals(straight.hasExit(Direction.NORTH), false);
		assertEquals(straight.hasExit(Direction.EAST), true);
		assertEquals(straight.hasExit(Direction.SOUTH), false);
		assertEquals(straight.hasExit(Direction.WEST), true);
	}
}
