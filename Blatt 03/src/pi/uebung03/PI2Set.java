package pi.uebung03;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Die Klasse implementiert eine Menge mit typischen Operationen. Als unterliegende
 * Speicherstruktur wird eine einfach verkettete Liste verwendet.
 *
 * TODO: Hier müssen Dinge hinzugefügt werden, die für die Implementierung aller
 * Selbstanordnungsstrategien von Listen gleich sind.
 *
 * @param <E>
 *          Der Typ der Elemente der Menge. Dieser muss {@code equals} sinnvoll
 *          implementieren.
 *
 * @author Thomas Röfer, Karsten Hölscher
 * @version 2017-05-10
 */
public abstract class PI2Set<E> implements Iterable<E> {

	/**
	 * Liefert eine leere Menge vom selben Typ wie dieses Objekt.
	 *
	 * @return Eine leere Menge.
	 */
	protected abstract PI2Set<E> emptySet();

	/**
	 * Testet, ob ein bestimmtes Element in der Menge gespeichert ist.
	 *
	 * @param pElement
	 *          Das Element, nach dem gesucht wird. Das Element darf auch {@code null}
	 *          sein, wäre dann aber garantiert nicht enthalten.
	 * @return {@code true} falls das gesuchte Element in der Menge enthalten ist,
	 *         ansonsten {@code false}.
	 */
	public abstract boolean contains(E pElement);

	/**
	 * Fügt der Menge ein neues Element hinzu. Die Methode wird von
	 * {@link PI2Set#add(Object)} aufgerufen, wenn das Element noch nicht vorhanden ist.
	 *
	 * @param pElement
	 *          Das Element, das hinzugefügt wird.
	 */
	protected void addNew(final E pElement) {
		// TODO: implementieren
	}

	/**
	 * Hinzufügen eines Elements zu der Menge.
	 *
	 * @param pElement
	 *          Das neue Element. Es darf nicht {@code null} sein.
	 * @throws NullPointerException
	 *           Falls das gegebene Element den Wert {@code null} hat.
	 */
	public void add(final E pElement) {
		if (pElement == null) {
			throw new NullPointerException("Element must not be null!");
		} else if (!contains(pElement)) {
			addNew(pElement);
		}
	}

	/**
	 * Liefert die Größe der Menge.
	 *
	 * @return Die Anzahl der Elemente, die in der Menge gespeichert sind.
	 */
	public int size() {
		int size = 0;
		
        for(E i : list){
        	if(i != null) {
        		size += size;
        	}
        }
        return size;
	}

	/**
	 * Bestimmt die Schnittmenge mit einer anderen Menge. Die aktuelle Menge ändert sich
	 * dabei nicht.
	 *
	 * @param pOther
	 *          Die andere Menge. Auch sie wird nicht verändert. Darf nicht {@code null}
	 *          sein.
	 * @return Die Schnittmenge der beiden Mengen.
	 * @throws NullPointerException
	 *           Falls die andere Menge {@code null} ist.
	 */
	public PI2Set<E> intersect(final PI2Set<E> pOther) {
		final PI2Set<E> result = emptySet();
		for (final E e : pOther) {
			if (contains(e)) {
				result.add(e);
			}
		}
		return result;
	}

	/**
	 * Bestimmt die Vereinigung mit einer anderen Menge. Die aktuelle Menge ändert sich
	 * dabei nicht.
	 *
	 * @param pOther
	 *          Die andere Menge. Auch sie wird nicht verändert. Darf nicht {@code null}
	 *          sein.
	 * @return Die Vereinigung der beiden Mengen.
	 * @throws NullPointerException
	 *           Falls die andere Menge {@code null} ist.
	 */
	public PI2Set<E> union(final PI2Set<E> pOther) {
		final PI2Set<E> result = emptySet();
		for (final E e : this) {
			result.add(e);
		}
		for (final E e : pOther) {
			result.add(e);
		}
		return result;
	}

	/**
	 * Bestimmt die Differenz zu einer anderen Menge. Die aktuelle Menge ändert sich dabei
	 * nicht.
	 *
	 * @param pOther
	 *          Die andere Menge. Auch sie wird nicht verändert. Darf nicht {@code null}
	 *          sein.
	 * @return Die aktuelle Menge ohne die Elemente der anderen Menge.
	 * @throws NullPointerException
	 *           Falls die andere Menge {@code null} ist.
	 */
	public PI2Set<E> diff(final PI2Set<E> pOther) {
		if (pOther == null) {
			throw new NullPointerException();
		} else {
			final PI2Set<E> result = emptySet();
			for (final E e : this) {
				if (!pOther.contains(e)) {
					result.add(e);
				}
			}
			return result;
		}
	}

	/**
	 * Liefert einen Iterator, mit dem die Menge durchlaufen werden kann. Das Verhalten
	 * des Iterators ist undefiniert, wenn die Menge während des Durchlaufens geändert
	 * wird.
	 *
	 * @return Ein Iterator zum Durchlaufen dieser Menge.
	 */
	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {

			/**
			 * Die Menge hat noch ein Element, wenn ...
			 *
			 * @return Noch weitere Elemente?
			 */
			@Override
			public boolean hasNext() {
				return false; // TODO: Ersetzen
			}

			/**
			 * Wechselt zum nächsten Element der Menge und liefert dieses zurück.
			 *
			 * @return Das nächste Element der Menge.
			 * @throws NoSuchElementException
			 *           Es gab kein weiteres Element.
			 */
			@Override
			public E next() {
				if (!hasNext()) {
					throw new NoSuchElementException();
				} else {
					return null; // TODO: Ersetzen
				}
			}

			/**
			 * Nicht implementiert.
			 *
			 * @throws UnsupportedOperationException
			 *           Löschen wird nicht unterstützt.
			 */
			@Override
			public void remove() {
				throw new UnsupportedOperationException();
			}
		};

	}

}
