package pi.uebung03;

/**
 * Die Klasse repräsentiert Strings, deren Aufrufe von {@code equals} global gezählt
 * werden. Die Klasse ist kein echter Ersatz für Strings, da sie keine der Methoden der
 * Klasse String unterstützt.
 *
 * @author Thomas Röfer, Karsten Hölscher
 * @version 2017-05-10
 */
public final class CountingString {

	/**
	 * Der globale Zähler für die Aufrufe von {@code equals}.
	 */
	private static int counter = 0;

	/**
	 * Der in einem Objekt gespeicherte String.
	 */
	private final String string;

	/**
	 * Erzeugt ein neues Objekt.
	 *
	 * @param pString
	 *          Der in dem Objekt gespeicherte String. Darf nicht {@code null} sein.
	 * @throws NullPointerException
	 *           Falls der übergebene String den Wert {@code null} hat.
	 */
	public CountingString(final String pString) {
		if (pString == null) {
			throw new NullPointerException();
		} else {
			string = pString;
		}
	}

	/**
	 * Setzt den globalen Zähler der {@code equals}-Aufrufe zurück.
	 */
	public static void resetCounter() {
		counter = 0;
	}

	/**
	 * Liefert die Anzahl der {@code equals}-Aufrufe seit Programmstart oder dem letzten
	 * Aufruf von {@link CountingString#resetCounter()}.
	 *
	 * @return Anzahl der Aufrufe der {@code equals}-Methode.
	 */
	public static int getCounter() {
		return counter;
	}

	/**
	 * Vergleicht diesen String mit einem anderen Objekt und gibt das Ergebnis des
	 * Vergleichs zurück. Die Aufrufe dieser Methode werden global gezählt.
	 *
	 * @param pOther
	 *          Das Objekt, mit dem verglichen wird.
	 * @return {@code true} Falls dieses Objekt gleich dem gegebenen Objekt ist, ansonsten
	 *         {@code false}.
	 */
	@Override
	public boolean equals(final Object pOther) {
		++counter;
		return pOther instanceof CountingString
			&& string.equals(((CountingString) pOther).string);
	}

	/**
	 * Liefert den in diesem Objekt gespeicherten String.
	 *
	 * @return Der String.
	 */
	@Override
	public String toString() {
		return string;
	}

	/**
	 * Gibt den Hashcode dieses Objektes zurück. Der Hashcode ist identisch zum Hashcode
	 * des im Objekt gespeicherten Strings.
	 *
	 * @return Den Hashcode dieses Objektes.
	 */
	@Override
	public int hashCode() {
		return string.hashCode();
	}

}
