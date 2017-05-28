package pi.uebung01.spec;

public class ClassFiFo<E> implements FiFoInterface<E> {
	private E[] array;
	private int size;
	
	public ClassFiFo(final int bsize){
		
		size = bsize;
		/**@SuppressWarnings("unchecked")
        final E[] array = (E[]) Array.newInstance(null, bsize);
        this.array = array; **/
		
        @SuppressWarnings("unchecked")
        final E[] array = (E[])new Object[size];
        this.array = array;
	}
	 /**
	    * Fügt das übergebene Element zu dieser Sammlung hinzu, falls noch Platz ist.
	    *
	    * @param pElement
	    *           Das einzufügende Element.
	    * @throws PushPopyException
	    *            Falls kein Platz mehr in dieser Sammlung vorhanden ist.
	    * @throws IllegalArgumentException
	    *            Falls das gegebene Element den Wert {@code null} hat.
	    */

	@Override
	public void push(E pElement) {
		if(pElement == null){
			throw new IllegalArgumentException("Ungültige Eingabe.");
		}
		if(this.isFull())
		{
			throw new PushPopyException("Die Sammlung ist voll.");
		}
		for(int i=0; i < array.length; i++){
					if(array[i] == null){
						array[i] = pElement;
						return;
					}
		}
			
	}
				
	
	
	/**
	    * Gibt an, ob diese Sammlung leer ist.
	    *
	    * @return {@code true} falls diese Sammlung leer ist, ansonsten {@code false}.
	    */
	
	@Override
	public boolean isEmpty() {
		for(int x = 0; x < array.length; x++){
		   if(array[x]!= null){
			   return false;
		   }
		}
		return true;
	}

	/**
	    * Gibt an, ob sich in dieser Sammlung bereits maximal viele Elemente befinden.
	    *
	    * @return {@code true} falls diese Sammlung maximal viele Elemente enthält,
	    *         ansonsten {@code false}.
	    */
	
	@Override
	public boolean isFull() {
		int s =  array.length - 1;
			if(array[s]== null){
			return false;
			}
			else{
		return true;
			}
	}
    /**
     * Die Methode entfernt einzelne Elemente aus der Sammlung. In dieser Methode wird immer das 
     * jüngste Elemente zuerst wieder entfernt.
     * 
     * @return Das zu entfernende Element.
     * 
     * @param juengstesElement 
     *        Das zu entferndende Element.
     */
	@Override
	public E pop() {
		
		E aeltestesElement = (E) new Object();
		
	    if(this.isEmpty()){
	    	throw new PushPopyException("Die Sammlung ist leer!");
	    }
	    aeltestesElement = array[0];
	    for(int d = 1; d < array.length; d++ ){
	    	array[d-1] = array[d];
	    }
	    array[array.length - 1] = null;
	    return aeltestesElement;
	
	}
}