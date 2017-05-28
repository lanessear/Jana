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
 * Diese Klasse erbt von Tile und realisiert GeradenPlättchen.
 * Die GeradenPlättchen besitzen eine Orietierung, die die Ausrichtung
 * der GeradenPlättchen angibt.
 */
public final class Straight extends Tile {

   /**
    * Erzeugt ein neues GeradenPlättchen mit der gegebenen Orientierung.
    *
    * @param pOrientation
    *           Die Orientierung des neuen GeradenPlättchens.
    */
   public Straight(final Direction pOrientation) {
      super(pOrientation);
   }
   
   /**
    * Überprüft, ob ein GeradenPlättchen in der aktuellen Orientierung einen
    * Ausgang in die gegebene Himmelsrichtung hat.
    * Falls dies der Fall ist, so wird {@code true} ausgegeben, sonst
    * {@code false}.
    * 
    * @param pDirection
    * 			Die Himmelsrichtung, bei der geprüft werden soll, ob
    * 			das GeradenPlättchen einen Ausgang in diese Himmelsrichtung
    * 			besitzt
    * @return {@code false}, falls die gegebene Himmelsrichtung die
    * 			aktuelle Orientierung oder die entgegen gesetzte Himmelsrichtung
    *  			des GeradenPlättchens ist, sonst {@code false}
    */
   @Override
   public boolean hasExit(final Direction pDirection) {
	   int orientation = getOrientation().getOrdinal();
	   return (pDirection.getOrdinal() == orientation ||
			   			pDirection.getOrdinal() == (orientation +2) %4);
   }

}
