/*
 * Copyright 2017 AG Softwaretechnik, University of Bremen, Germany
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package pi.uebung01;

/**
 * Diese Klasse erbt von Tile und realisiert Kurvenplättchen.
 * Die Kurvenplättchen besitzen eine Orietierung, die die Ausrichtung
 * der Kurvenplättchen angibt.
 */
public final class Curve extends Tile {

   /**
    * Erzeugt ein neues KurvenPlättchen mit der gegebenen Orientierung.
    *
    * @param pOrientation
    *           Die Orientierung des neuen Kurvenplättchens.
    */
   public Curve(final Direction pOrientation) {
      super(pOrientation);
   }
   /**
    * Überprüft, ob das Kurvenplättchen in der aktuellen Orientierung einen
    * Ausgang in die übergebene Himmelsrichtung hat.
    * Falls ja, wird (@code true} zurückegegeben, ansonsten {@code false}.
    * 
    * @param pDirection
    * 			Die Himmelsrichtung, auf der geprüft wird, ob das Plättchen
    * 			dorthin einen Ausgang hat
    * @return {@code true}, falls die angegebene Richtung entweder die nächste
    * 			oder übernächste Richtung nach der aktuellen Orientierung ist,
    * 			sonst {@code false}
    */
   @Override
   public boolean hasExit(final Direction pDirection) {
	   int orientation = getOrientation().getOrdinal();
	   if (pDirection.getOrdinal() == (orientation + 1) %4
			   || pDirection.getOrdinal() == (orientation + 2) %4) {
		   	return true;
	   }
	   return false;
   }

}
