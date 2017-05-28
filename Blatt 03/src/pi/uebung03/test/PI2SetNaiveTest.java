package pi.uebung03.test;

import org.junit.Test;

import pi.uebung03.PI2Set;
import pi.uebung03.PI2SetNaive;

/**
 * Eine spezialisierte Testklasse für eine Menge mit naiver Selbstanordnung. Diese kann
 * erweitert werden. Bitte beachtet, dass in der Superklasse alle Tests als
 * {@code final} deklariert wurden, damit ihr sie hier nicht versehentlich überschreibt.
 *
 * @author Thomas Röfer
 * @version 2017-05-10
 */
public class PI2SetNaiveTest extends PI2SetTest {

	/**
	 * Liefert eine neue, leere Menge des getesteten Typs für Integer-Werte.
	 *
	 * @return Eine neue Menge mit naiver Selbstanordnung.
	 */
	@Override
	public PI2Set<Integer> emptySet() {
		return new PI2SetNaive<Integer>();
	}

	/**
	 * Diese Methode kann gelöscht werden, sobald eigene Testmethoden eingefügt werden.
	 * Ohne eine Testmethode wird diese Klasse nicht als Testklasse erkannt, obwohl ihre
	 * Basisklasse Testmethoden enthält.
	 */
	@Test
	public void testNothing() {
	}

}
