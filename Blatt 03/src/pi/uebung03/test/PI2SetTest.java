package pi.uebung03.test;

import org.junit.Before;
import org.junit.Test;

import pi.uebung03.PI2Set;

import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.NoSuchElementException;

/**
 * Test-Klasse für die Klasse {@link PI2Set}. Diese Klasse darf nicht verändert werden.
 * Eigene Tests können in die abgeleiteten Klassen eingetragen werden.
 *
 * @author Thomas Röfer
 * @version 2017-05-10
 */
public abstract class PI2SetTest {

	/**
	 * Wird vor jedem Test mit einer leeren Menge initialisiert und kann in den Tests
	 * verwendet werden.
	 */
	protected PI2Set<Integer> set;

	/**
	 * Wird vor jedem Test mit einer leeren Menge initialisiert und kann in den Tests
	 * verwendet werden.
	 */
	protected PI2Set<Integer> other;

	/**
	 * Diese Methode muss überschrieben werden und eine neue, leere Menge des getesteten
	 * Typs für Integer-Werte zurückliefern.
	 *
	 * @return Leere Menge des getesteten Typs.
	 */
	public abstract PI2Set<Integer> emptySet();

	/**
	 * Wird vor jeder Testfall-Methode aufgerufen. Initialisiert Attribute, die von den
	 * Tests benutzt werden können. Die Methode soll eigentlich nicht mehr überschrieben
	 * werden.
	 */
	@Before
	public final void setUp() {
		set = emptySet();
		other = emptySet();
	}

	/**
	 * {@code null} wird nicht als Element akzeptiert.
	 */
	@Test(expected = NullPointerException.class)
	public final void testAddNull() {
		set.add(null);
	}

	/**
	 * Hinzufügen ändert die Größe, wenn ein Element noch nicht enthalten ist.
	 */
	@Test
	public final void testAddSize() {
		assertEquals(0, set.size());
		set.add(1);
		assertEquals(1, set.size());
		set.add(2);
		assertEquals(2, set.size());
		set.add(2);
		assertEquals(2, set.size());
	}

	/**
	 * {@code null} ist niemals enthalten, aber man darf danach fragen.
	 */
	@Test
	public final void testContainsNull() {
		set.add(1);
		assertFalse(set.contains(null));
	}

	/**
	 * Testet, ob {@code contains} richtig funktioniert, insbesondere, ob es
	 * {@code equals} verwendet.
	 */
	@Test
	public final void testContains() {
		assertFalse(set.contains(1));
		set.add(1);
		assertFalse(set.contains(0));
		assertTrue(set.contains(new Integer(1)));
		assertFalse(set.contains(2));
		set.add(2);
		set.add(3);
		assertTrue(set.contains(1));
		assertTrue(set.contains(2));
		assertTrue(set.contains(3));
	}

	/**
	 * Die Schnittmenge mit {@code null} ist ein Fehler.
	 */
	@Test(expected = NullPointerException.class)
	public final void testIntersectNull() {
		set.intersect(null);
	}

	/**
	 * Schnittmenge testen: Leer, keine Überlappung, vollständige Überlappung, teilweise
	 * Überlappung.
	 */
	@Test
	public final void testIntersect() {
		assertEquals(0, set.intersect(other).size());
		set.add(1);
		other.add(2);
		assertEquals(0, set.intersect(other).size());
		set.add(2);
		other.add(1);
		assertEquals(2, set.intersect(other).size());
		set.add(4);
		set.add(6);
		set.add(9);
		other.add(7);
		other.add(6);
		other.add(8);
		assertEquals(3, set.intersect(other).size());
	}

	/**
	 * Die Vereinigung mit {@code null} ist ein Fehler.
	 */
	@Test(expected = NullPointerException.class)
	public final void testUnionNull() {
		set.union(null);
	}

	/**
	 * Vereinigung testen: Leer, keine Überlappung, vollständige Überlappung, teilweise
	 * Überlappung.
	 */
	@Test
	public final void testUnion() {
		assertEquals(0, set.union(other).size());
		set.add(1);
		other.add(2);
		assertEquals(2, set.union(other).size());
		set.add(2);
		other.add(1);
		assertEquals(2, set.union(other).size());
		set.add(4);
		set.add(6);
		set.add(9);
		other.add(7);
		other.add(6);
		other.add(8);
		assertEquals(7, set.union(other).size());
	}

	/**
	 * Die Differenz zu {@code null} ist ein Fehler.
	 */
	@Test(expected = NullPointerException.class)
	public final void testDiffNull() {
		set.diff(null);
	}

	/**
	 * Differenz testen: Leer, keine Überlappung, vollständige Überlappung, teilweise
	 * Überlappung.
	 */
	@Test
	public final void testDiff() {
		assertEquals(0, set.diff(other).size());
		set.add(1);
		other.add(2);
		assertEquals(1, set.diff(other).size());
		set.add(2);
		other.add(1);
		assertEquals(0, set.diff(other).size());
		set.add(4);
		set.add(6);
		set.add(9);
		other.add(7);
		other.add(6);
		other.add(8);
		assertEquals(2, set.diff(other).size());
	}

	/**
	 * Teste Iterator für leere, einelementige und dreielementige Menge. Da bei letzterer
	 * nicht definiert ist, in welcher Reihenfolge die Elemente geliefert werden, werden
	 * diese gesammelt, um am Ende zu prüfen, ob alle enthalten waren.
	 */
	@Test
	public final void testIterator() {
		final Iterator<Integer> iter1 = set.iterator();
		assertFalse(iter1.hasNext());
		set.add(1);
		final Iterator<Integer> iter2 = set.iterator();
		assertTrue(iter2.hasNext());
		assertEquals(new Integer(1), iter2.next());
		assertFalse(iter2.hasNext());
		set.add(2);
		set.add(3);
		set.add(2);
		HashSet<Integer> reference = new HashSet<Integer>();
		final Iterator<Integer> iter3 = set.iterator();
		assertTrue(iter3.hasNext());
		reference.add(iter3.next());
		assertTrue(iter3.hasNext());
		reference.add(iter3.next());
		assertTrue(iter3.hasNext());
		reference.add(iter3.next());
		assertFalse(iter3.hasNext());
		assertTrue(reference.contains(1));
		assertTrue(reference.contains(2));
		assertTrue(reference.contains(3));
	}

	/**
	 * Es ist ein Fehler, mit dem Iterator über das Ende der Menge hinauszulesen.
	 */
	@Test(expected = NoSuchElementException.class)
	public final void testIteratorError() {
		final Iterator<Integer> iter = set.iterator();
		iter.next();
	}

	/**
	 * {@code remove} wird nicht unterstützt.
	 */
	@Test(expected = UnsupportedOperationException.class)
	public final void testRemove() {
		set.add(1);
		final Iterator<Integer> iter = set.iterator();
		assertTrue(iter.hasNext());
		assertEquals(new Integer(1), iter.next());
		iter.remove();
	}
}
