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

import static pi.uebung01.Direction.EAST;
import static pi.uebung01.Direction.NORTH;
import static pi.uebung01.Direction.SOUTH;
import static pi.uebung01.Direction.WEST;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pi.uebung01.spec.BoardInterface;
import pi.uebung01.spec.ClassFiFo;
import pi.uebung01.spec.ClassLiFo;
import pi.uebung01.spec.LabyInterface;
//import pi.uebung01.spec.LiFoInterface;
import pi.uebung01.spec.PieceInterface;
import pi.uebung01.spec.PushPopyInterface;

/**
 * Realisiert das Modell des Spielfelds. Ein Spielfeld enthält ein Gitter von 7x7
 * Plättchen, angeordnet in Zeilen und Spalten. Zusätzlich enthält das Spielfeld ein
 * SchiebePlättchen, das nicht Teil der GitterPlättchen ist, sondern zum Einschub
 * verwendet wird. Dieses Modell berechnet, ob es einen gültigen Weg vom Plättchen einer
 * gegebenen Spielfigur zu einem angegebenen ZielPlättchen gibt.
 *
 * @author K. Hölscher
 * @version 2017-04-07
 */
public class Board implements BoardInterface {

   /**
    * Das PlättchenGitter dieses Spielfeldes.
    */
   private final Tile[][] tiles;

   /**
    * Enthält das SpielModell für dieses Spielfeld.
    */
   private final LabyInterface laby;

   /**
    * Das SchiebePlättchen.
    */
   private Tile insertionTile;

   /**
    * Erzeugt ein neues Spielfeld für das gegebene SpielModell. Erzeugt ein leeres
    * PlättchenGitter. Falls der Parameterwert {@code null} ist, wird eine
    * {@link IllegalArgumentException} ausgelöst.
    *
    * @param pLaby
    *           Das SpielModell für das neue Spielfeld.
    */
   public Board(final LabyInterface pLaby) {
      if (pLaby == null) {
         throw new IllegalArgumentException("Parameter für SpielModell ist null!");
      }
      tiles = new Tile[PI2Laby.NUMBER_OF_CELLS][PI2Laby.NUMBER_OF_CELLS];
      laby = pLaby;
   }

   @Override
   public final void configure() {
      final Tile upperLeft = new Curve(NORTH);
      final Tile upperRight = new Curve(EAST);
      final Tile lowerLeft = new Curve(WEST);
      final Tile lowerRight = new Curve(SOUTH);

      for (int row = 0; row < PI2Laby.NUMBER_OF_CELLS; row++) {
         for (int column = 0; column < PI2Laby.NUMBER_OF_CELLS; column++) {
            if (row == 0 && column == 0) {
               setTile(0, 0, upperLeft);
            } else if (row == 0 && column == PI2Laby.LAST_CELL_INDEX) {
               setTile(0, PI2Laby.LAST_CELL_INDEX, upperRight);
            } else if (row == PI2Laby.LAST_CELL_INDEX && column == 0) {
               setTile(PI2Laby.LAST_CELL_INDEX, 0, lowerLeft);
            } else if (row == PI2Laby.LAST_CELL_INDEX
                  && column == PI2Laby.LAST_CELL_INDEX) {
               setTile(PI2Laby.LAST_CELL_INDEX, PI2Laby.LAST_CELL_INDEX, lowerRight);
            } else {
               final Tile tile = randomTile();
               setTile(row, column, tile);
            }
         }
      }

      insertionTile = randomTile();
   }

   @Override
   public final Tile getTile(final int pRow, final int pColumn) {
      checkCoordinates(pRow, pColumn);
      return tiles[pRow][pColumn];
   }

   /**
    * Erzeugt ein zufälliges Plättchen, d.h. eine Kurve, eine Gerade oder ein TPlättchen
    * mit zufälliger Orientierung und gibt es zurück.
    *
    * @return Das zufällig erzeugte Plättchen mit zufälliger Orientierung.
    */
   private Tile randomTile() {
      final Random random = new Random();
      final int type = random.nextInt(PI2Laby.NO_OF_TILETYPES);
      final int directionOrdinal = random.nextInt(Direction.COUNT);
      final Direction direction = Direction.values()[directionOrdinal];
      switch (type) {
      case 0:
         return new Curve(direction);
      case 1:
         return new Straight(direction);
      case 2:
         return new Junction(direction);
      default:
         // dieser Fall sollte eigentlich nicht auftreten
         return randomTile(); // falls doch: nochmal bis Wert zwischen 0 und 2
      }
   }

   @Override
   public final void insert(final int pRow, final int pColumn) {
      checkCoordinates(pRow, pColumn);
      final Tile actualInsertion = insertionTile;
      if (pRow == 0) { // Einschub von oben
         insertionTile = tiles[PI2Laby.LAST_CELL_INDEX][pColumn];
         // nach unten verschieben
         for (int i = PI2Laby.LAST_CELL_INDEX; i > 0; i--) {
            setTile(i, pColumn, tiles[i - 1][pColumn]);
         }
         setTile(0, pColumn, actualInsertion);
      } else if (pRow == PI2Laby.LAST_CELL_INDEX) { // Einschub von unten
         insertionTile = tiles[0][pColumn];
         for (int i = 0; i < PI2Laby.LAST_CELL_INDEX; i++) {
            setTile(i, pColumn, tiles[i + 1][pColumn]);
         }
         setTile(PI2Laby.LAST_CELL_INDEX, pColumn, actualInsertion);
      } else if (pColumn == 0) { // Einschub von links
         insertionTile = tiles[pRow][PI2Laby.LAST_CELL_INDEX];
         for (int i = PI2Laby.LAST_CELL_INDEX; i > 0; i--) {
            setTile(pRow, i, tiles[pRow][i - 1]);
         }
         setTile(pRow, 0, actualInsertion);
      } else if (pColumn == PI2Laby.LAST_CELL_INDEX) { // Einschub von rechts
         insertionTile = tiles[pRow][0];
         for (int i = 0; i < PI2Laby.LAST_CELL_INDEX; i++) {
            setTile(pRow, i, tiles[pRow][i + 1]);
         }
         setTile(pRow, PI2Laby.LAST_CELL_INDEX, actualInsertion);
      }
      for (int playerNo = 0; playerNo < 2; playerNo++) {
         final PieceInterface piece = laby.getPiece(playerNo);
         final Tile playerTile = piece.getTile();
         if (playerTile.equals(insertionTile)) {
            piece.setTile(actualInsertion);
         }
      }
   }

   @Override
   public final Tile getInsertionTile() {
      return insertionTile;
   }

   @Override
   public final void turnInsertionTileRight() {
      if (insertionTile == null) {
         return; // Wird erst in "configure" gesetzt, könnte also noch
                 // null sein!
      }
      int newOrientation = insertionTile.getOrientation().getOrdinal() + 1;
      newOrientation = newOrientation % Direction.COUNT;
      insertionTile.setOrientation(Direction.values()[newOrientation]);
   }

   @Override
   public final void turnInsertionTileLeft() {
      if (insertionTile == null) {
         return; // Wird erst in "configure" gesetzt, könnte also noch
                 // null sein!
      }
      int newOrientation =
            insertionTile.getOrientation().getOrdinal() + PI2Laby.NUMBER_OF_CELLS;
      newOrientation = newOrientation % Direction.COUNT;
      insertionTile.setOrientation(Direction.values()[newOrientation]);
   }

   /**
    * {@inheritDoc} Diese Methode führt die Wegprüfung zwei Mal mit verschiedenen
    * Datenstrukturen durch. Im ersten Durchlauf wird eine {@linkplain PI2Queue}
    * verwendet, im zweiten Durchlauf ein {@linkplain PI2Stack}. Dabei wird jeweils
    * mitgezählt, wieviele Schleifendurchläufe bei der Wegsuche nötig sind. Nach dem
    * beide Durchläufe beendet wurden, wird der aktuelle Statistikstand auf der Konsole
    * ausgegeben, zusammen mit der Statistik für diese eine Wegsuche.
    *
    * @throws IllegalStateException
    *            Falls die beiden Durchläufe mit den verschiedenen Datenstrukturen
    *            unterschiedliche Resultate liefern.
    *
    */
	   
   @Override
   public final boolean existsPath(final PieceInterface pPiece, final int pDestRow,
         final int pDestCol) {
      ClassLiFo<Tile> stack = new ClassLiFo<Tile>(49);
      ClassFiFo<Tile> queue = new ClassFiFo<Tile>(49);
      boolean a = this.algorithmus(stack, pPiece, pDestRow, pDestCol);
      boolean b = this.algorithmus(queue, pPiece, pDestRow, pDestCol);
      if(a != b){
    	  throw new IllegalStateException();
      }
      else{
    	  return a;
      }
     
   }
   
   /**
    * Die Methode erzeugt zunächst eine Sammlung vom Typ PushPopyInterface für die noch zu bearbeitenden Plättchen.
    * Jetzt wird das Plättchen, auf dem sich die gegebene Spielfigur befindet, in die Sammlung Rand eingefügt. 
    * Nun beginnt eine Schleife, die so oft wiederholt wird, wie sich noch Elemente in der Sammlung Rand befinden.
    * Innerhalb der Schleife wird mittels pop() ein Plättchen P aus dem Rand entnommen.
    * Wenn dieses Plättchen bereits das Ziel ist, wird true zurückgegeben.
    * Anderenfalls werden für P alle Nachbarplättchen, zu denen es einen Weg gibt, in die Sammlung Rand eingefügt.
    * Falls das Zielplüttchen nicht erreichbar ist, wird false zurückgegeben.
    * Außerdem zählt ein Zähler die Schleifendurchläufe mit.
    * @param rand die Sammlung Rand
    * @param p das Feld, auf dem die Spielfigur zurzeit steht
    * @param reihe die Reihe, in der sich das Zielfeld befindet
    * @param spalte die Spalte, in der sich das Zielfeld befindet
    * @return der boolsche Wert, der besagt, ob es einen Pfad von dem aktuellen Plättchen bis zum Zielfeld gibt
    */
   public boolean algorithmus(PushPopyInterface<Tile> rand, PieceInterface p, int reihe, int spalte){
	   rand.push(p.getTile());
	   int zaehler = 0;
	   
	   List<Tile> checkedAlready = new ArrayList<>();
	   
	   while(rand.isEmpty() == false){
		   ++zaehler;
		   Tile gepopt = rand.pop();
		   if((gepopt.getRow() == reihe) && (gepopt.getColumn()== spalte)) {
			   System.out.println("zaehler = " + zaehler);
			   return true;
		   }
		   else{
			   if((gepopt.getRow() != 0) && (this.hasPath(gepopt, tiles[gepopt.getRow() -1][gepopt.getColumn()]))){
				   if(!checkedAlready.contains(tiles[gepopt.getRow() -1][gepopt.getColumn()])){
					   rand.push(tiles[gepopt.getRow() -1][gepopt.getColumn()]);
				   }
			   }
			   if((gepopt.getColumn() != 0) && (this.hasPath(gepopt, tiles[gepopt.getRow() ][gepopt.getColumn() -1]))){
				   
				   if(!checkedAlready.contains(tiles[gepopt.getRow() ][gepopt.getColumn() -1])){
					   rand.push(tiles[gepopt.getRow() ][gepopt.getColumn() -1]);
				   }
			   }
			   if((gepopt.getRow() != 6) && (this.hasPath(gepopt, tiles[gepopt.getRow() +1][gepopt.getColumn()]))){
				   if(!checkedAlready.contains(tiles[gepopt.getRow() +1][gepopt.getColumn()])){
					   rand.push(tiles[gepopt.getRow() +1][gepopt.getColumn()]);
				   }
			   }
			   if((gepopt.getColumn() != 6) && (this.hasPath(gepopt, tiles[gepopt.getRow() ][gepopt.getColumn() +1]))){
				   if(!checkedAlready.contains(tiles[gepopt.getRow() ][gepopt.getColumn() +1])){
					   rand.push(tiles[gepopt.getRow() ][gepopt.getColumn() +1]);
				   }
			   }
			   
			   checkedAlready.add(gepopt);
		   }
	   }
	   System.out.println("zaehler = " + zaehler);
	   return false;
   }

   /** 
    * die Methode prüft, ob 2 Teile a und b eine Verbindung zueinander haben, indem sie die vier Fälle durchgeht,
    * dass Plättchen b oberhalb, rechts von, unterhalb oder links von dem Plättchen a liegt.
    * @param a ein beliebiges Plättchen
    * @param b ein Nachbarplättchen von a
    * @return der boolsche Wert, der besagt, ob es eine Verbindung von a nach b gibt
    */
    
   public boolean hasPath(Tile a, Tile b){
	   if ((a.getRow()  == b.getRow()) && (a.getColumn() +1 == b.getColumn()) && a.hasExit(EAST) && b.hasExit(WEST)){
		   return true;
	   }
	   if ((a.getRow() +1 == b.getRow()) && (a.getColumn() == b.getColumn()) && a.hasExit(SOUTH) && b.hasExit(NORTH)){
		   return true;
	   }
	   if ((a.getRow()  == b.getRow()) && (a.getColumn() == b.getColumn() +1) && a.hasExit(WEST) && b.hasExit(EAST)){
		   return true;
	   }
	   if ((a.getRow()  == b.getRow() +1) && (a.getColumn() == b.getColumn()) && a.hasExit(NORTH) && b.hasExit(SOUTH)){
		   return true;
	   }
	   else{
		   return false;
	   }
   }
   /**
    * Setzt das gegebene Plättchen in die Zelle in der gegebenen Zeile und der gegebenen
    * Spalte in dem PlättchenGitter. Die Zeile und Spalte des Plättchens selbst werden
    * ebenfalls auf die gegebenen Werte gesetzt.
    *
    * Die Parameterwerte werden nicht auf Plausibilität geprüft, da es sich nicht um
    * eine öffentliche Methode handelt.
    *
    * @param pRow
    *           die Zeile, in die das Plättchen platziert werden soll
    * @param pColumn
    *           die Spalte, in die das Plättchen platziert werden soll
    * @param pTile
    *           das zu platzierende Plättchen
    */
   private void setTile(final int pRow, final int pColumn, final Tile pTile) {
      tiles[pRow][pColumn] = pTile;
      pTile.setRow(pRow);
      pTile.setColumn(pColumn);
   }

   /**
    * Prüft, ob die gegebenen Werte für Zeile und Spalte innerhalb der Grenzen des
    * Spielfeldes liegen. Falls nicht, wird eine {@link IllegalArgumentException}
    * ausgelöst.
    *
    * @param pRow
    *           die zu überprüfende Zeile
    * @param pColumn
    *           die zu überprüfende Spalte
    */
   private void checkCoordinates(final int pRow, final int pColumn) {
      if (pRow < 0 || pRow >= PI2Laby.NUMBER_OF_CELLS) {
         throw new IllegalArgumentException(String.format(
               "Zeile ist nicht im Bereich 0 bis %d!", PI2Laby.NUMBER_OF_CELLS));
      }
      if (pColumn < 0 || pColumn >= PI2Laby.NUMBER_OF_CELLS) {
         throw new IllegalArgumentException(String.format(
               "Spalte ist nicht im Bereich 0 bis %d!", PI2Laby.NUMBER_OF_CELLS));
      }
   }

}
