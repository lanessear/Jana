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
 * Diese Klasse erbt von Tile und realisiert TPlättchen.
 * Die TPlättchen besitzen eine Orietierung, die die Ausrichtung
 * der TPlättchen angibt.
 */
public final class Junction extends Tile {

   /**
    * Erzeugt ein neues TPlättchen mit der gegebenen Orientierung.
    *
    * @param pOrientation
    *           Die Orientierung des neuen TPlättchens.
    */
   public Junction(final Direction pOrientation) {
      super(pOrientation);
   }
   /**
    * Überprüft, ob ein TPlättchen in der aktuellen Orientierung einen
    * Ausgang in die gegebene Richtung hat.
    * Falls dies der Fall ist, so wird {@code true} ausgegeben, sonst
    * {@code false}.
    * 
    * @param pDirection
    * 			Die Himmelsrichtung, bei der geprüft werden soll, ob
    * 			das TPlättchen einen Ausgang in diese Himmelsrichtung
    * 			besitzt
    * @return {@code false}, falls die gegebene Himmelsrichtung die
    * 			aktuelle Orientierung des TPlättchens ist, ansonsten
    * 			wird {@code true} züruckgegeben.
    */
   @Override
   public boolean hasExit(final Direction pDirection) {
      return (pDirection.getOrdinal() != getOrientation().getOrdinal());
   }

}
