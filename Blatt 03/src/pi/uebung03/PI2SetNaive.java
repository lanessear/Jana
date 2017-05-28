package pi.uebung03;

/**
 * Eine Menge mit naiver Anordnungsstrategie für die Elemente. Sie muss erweitert
 * werden. Für die anderen Strategien sollen ähnliche Klassen erzeugt werden.
 *
 * @param <E>
 *          Der Typ der Elemente der Menge. Dieser muss {@code equals} sinnvoll
 *          implementieren.
 *
 * @author Thomas Röfer, Karsten Hölscher
 * @version 2017-05-10
 */
public class PI2SetNaive<E> extends PI2Set<E> {
	/**
	 * Liefert eine neue, leere Menge.
	 *
	 * @return Eine neue Menge mit naiver Selbstanordnung.
	 */
	@Override
	protected PI2Set<E> emptySet() {
		return new PI2SetNaive<>();
	}

	/**
	 * Testet, ob ein bestimmtes Element in der Menge gespeichert ist.
	 *
	 * @param pElement
	 *          Das Element, nach dem gesucht wird. Das Element darf auch {@code null}
	 *          sein, wäre dann aber garantiert nicht enthalten.
	 * @return {@code true} falls das gesuchte Element in der Menge enthalten ist,
	 *         ansonsten {@code false}.
	 */
	@Override
	public boolean contains(final E pElement) {
		if(isEmpty()){
			throw new NullPointerException("Gibts nicht!");
		} else {
			  
		head = current;
	    if(head == pElement){
	    return true;
			   } 
		while(current != null){
		if(current.next == pElement) 
				return true;		
			}
		current = current.next;
		}
	   return false;
		
	}

}
