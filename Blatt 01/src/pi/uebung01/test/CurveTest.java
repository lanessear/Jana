package pi.uebung01.test;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import pi.uebung01.Direction;
import pi.uebung01.Curve;

public class CurveTest {

	@Test
	public void testNorth() {
		Curve curve = new Curve(Direction.NORTH);
		assertEquals(curve.hasExit(Direction.NORTH), false);
		assertEquals(curve.hasExit(Direction.EAST), true);
		assertEquals(curve.hasExit(Direction.SOUTH), true);
		assertEquals(curve.hasExit(Direction.WEST), false);
	}
	
	@Test
	public void testEast() {
		Curve curve = new Curve(Direction.EAST);
		assertEquals(curve.hasExit(Direction.NORTH), false);
		assertEquals(curve.hasExit(Direction.EAST), false);
		assertEquals(curve.hasExit(Direction.SOUTH), true);
		assertEquals(curve.hasExit(Direction.WEST), true);
	}
	
	@Test
	public void testSouth() {
		Curve curve = new Curve(Direction.SOUTH);
		assertEquals(curve.hasExit(Direction.NORTH), true);
		assertEquals(curve.hasExit(Direction.EAST), false);
		assertEquals(curve.hasExit(Direction.SOUTH), false);
		assertEquals(curve.hasExit(Direction.WEST), true);
	}
	
	@Test
	public void testWest() {
		Curve curve = new Curve(Direction.WEST);
		assertEquals(curve.hasExit(Direction.NORTH), true);
		assertEquals(curve.hasExit(Direction.EAST), true);
		assertEquals(curve.hasExit(Direction.SOUTH), false);
		assertEquals(curve.hasExit(Direction.WEST), false);
	}
}
