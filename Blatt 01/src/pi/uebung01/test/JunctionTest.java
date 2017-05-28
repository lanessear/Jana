package pi.uebung01.test;

import static org.junit.Assert.*;

import org.junit.Test;

import pi.uebung01.Junction;
import pi.uebung01.Direction;

public class JunctionTest {

	@Test
	public void testNorth() {
		Junction junction = new Junction(Direction.NORTH);
		assertEquals(junction.hasExit(Direction.NORTH), false);
		assertEquals(junction.hasExit(Direction.EAST), true);
		assertEquals(junction.hasExit(Direction.SOUTH), true);
		assertEquals(junction.hasExit(Direction.WEST), true);
	}
	
	@Test
	public void testEast() {
		Junction junction = new Junction(Direction.EAST);
		assertEquals(junction.hasExit(Direction.NORTH), true);
		assertEquals(junction.hasExit(Direction.EAST), false);
		assertEquals(junction.hasExit(Direction.SOUTH), true);
		assertEquals(junction.hasExit(Direction.WEST), true);
	}
	
	@Test
	public void testSouth() {
		Junction junction = new Junction(Direction.SOUTH);
		assertEquals(junction.hasExit(Direction.NORTH), true);
		assertEquals(junction.hasExit(Direction.EAST), true);
		assertEquals(junction.hasExit(Direction.SOUTH), false);
		assertEquals(junction.hasExit(Direction.WEST), true);
	}
	
	@Test
	public void testWest() {
		Junction junction = new Junction(Direction.WEST);
		assertEquals(junction.hasExit(Direction.NORTH), true);
		assertEquals(junction.hasExit(Direction.EAST), true);
		assertEquals(junction.hasExit(Direction.SOUTH), true);
		assertEquals(junction.hasExit(Direction.WEST), false);
	}
}
