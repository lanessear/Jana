/** Copyright 2017 AG Softwaretechnik, University of Bremen, Germany
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
package pi.uebung02;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;

import pi.uebung02.exceptions.TheaPPParseException;

/**
 * Realisiert die nötige Logik, um eine gegebene Liste von Studierenden mit ihren
 * Bewertungen gemäß einer konfigurierbaren Beschränkung auf Tutorien zu verteilen.
 *
 * Stellt Konstanten als Rahmenbedingung zur Verfügung.
 *
 * Die Beschränkung besteht in der maximalen Anzahl der Studierenden pro Tutorium, der
 * Anzahl der Tutorien selbst und der Mindestbewertung, die eine StudentIn dem Tutorium
 * gegeben haben muss. Die Anzahl der Tutorien mal der Anzahl der maximal pro Tutorium
 * zugelassenen Studierenden muss größer oder gleich der Gesamtzahl der zu verteilenden
 * Studierenden sein.
 *
 * Die Verteilung erfolgt mit simplem Backtracking unter Angabe der obigen
 * Beschränkungen. Eine Konfiguration besteht dabei aus der aktuellen Verteilung der
 * Studierenden in die Tutorien. Die Startkonfiguration besteht also aus leeren
 * Tutorien. Initial sind also alle Studierenden keinem Tutorium zugeordnet.
 *
 * Eine Konfiguration ist ungültig, wenn in einem Tutorium mehr Studierende zugeordnet
 * sind, als maximal erlaubt oder wenn es Studierende gibt, die einem Tutorium
 * zugeordnet ist, dass sie mit einer Zahl bewertet haben, die kleiner als die
 * konfigurierte Mindestbewertung ist.
 *
 * Es sei s die Anzahl der Studierenden und t die Anzahl der Tutorien. Die Tutorien sind
 * von 1 bis t nummeriert, die Studierenden analog von 1 bis s.
 *
 * Die Folgekonfiguration der Anfangskonfiguration errechnet sich, in dem StudentIn S_1
 * dem Tutorium T_1 zugeordnet wird.
 *
 * Sei K_i eine Konfiguration, in der n Studierende bereits in Tutorien verteilt sind.
 * (1 <= n <= s). Die letzte zugeordnete StudentIn sei S_n. Sie ist dem Tutorium T_j
 * zugeordnet. Die Folgekonfiguration errechnet sich dann wie folgt:
 *
 * Fall 1: Die Konfiguration ist gültig und n=s:
 *
 * Die Konfiguration ist eine gültige Lösung. Es gibt keine Folgekonfiguration.
 *
 * Fall 2: Die Konfiguration ist gültig und n < s:
 *
 * Die Folgekonfiguration K_{i+1} errechnet sich, in dem S_{n+1} dem Tutorium T_1
 * zugeordnet wird.
 *
 * Fall 3: Die Konfiguration ist nicht gültig und j < t:
 *
 * Die Folgekonfiguration K_{i+1} errechnet sich, in dem S_n aus T_j entfernt und dem
 * Tutorium t_{j+1} zugeordnet wird.
 *
 * Fall 4: Die Konfiguration ist ungültig und j = t:
 *
 * Es gibt dann ein eine größte natürliche Zahl k', so dass S_{k'} einem Tutorium T_v
 * mit v < t zugeordnet ist und alle S_k für k' < k <= n dem Tutorium T_t zugeordnet
 * sind. Die Studis S_k werden jetzt für k' < k <= n aus dem Tutorium T_t entfernt. Die
 * StudentIn S_{k'} wird aus ihrem Tutorium T_v entfernt und dem Tutorium T_{v+1}
 * zugeordnet. Falls es kein solches k' gibt, gibt es keine Folgekonfiguration.
 *
 *
 * @author Karsten Hölscher
 * @version 2017-04-28
 */
public class TheaPP {

	/**
	 * Die maximal mögliche Bewertung von Studierenden für ein Tutorium. Die möglichen
	 * Werte für die Bewertung liegen damit zwischen 0 und diesem Wert (beide Werte
	 * inklusive).
	 */
	public static final int MAX_RATING = 5;

	/**
	 * Die aktuelle Konfiguration als Liste von Tutorien. Ein Tutorium wird hier als Liste
	 * von Studi-Objekten realisiert.
	 */
	private List<List<Studi>> tutorials;

	/**
	 * Die Anzahl der Tutorien.
	 */
	private final int noOfTutorials;

	/**
	 * Die maximale Anzahl von Studierenden in einem Tutorium.
	 */
	private final int maxTutorialSize;
	
	/**
	 * Speichert den letzten zu einem Tutorium hinzugefügten Studenten.
	 */
	private Studi lastStudi;

	// TODO: evtl. eigene Attribute hinzufügen

	/**
	 * Erzeugt ein neues Objekt zur Verteilung von Studierenden auf die gegebene Anzahl
	 * von Tutorien mit der gegebenen maximalen TeilnehmerInnenzahl pro Tutorium.
	 *
	 * @param pNoOfTutorials
	 *          Die Anzahl der Tutorien.
	 * @param pMaxTutorialSize
	 *          Die maximale Anzahl an Studierenden pro Tutorium.
	 * @throws IllegalArgumentException
	 *           Falls die Anzahl der Tutorien oder die maximale Anzahl der Studierenden
	 *           pro Tutorium negativ ist.
	 */
	public TheaPP(final int pNoOfTutorials, final int pMaxTutorialSize) {
		// TODO: evtl. Code ergänzen
		if (pNoOfTutorials < 0) {
			throw new IllegalArgumentException("Number of tutorials must not be negative!");
		}
		if (pMaxTutorialSize < 0) {
			throw new IllegalArgumentException(
				"Maximum number of students in tutorial must not be negative!");
		}
		noOfTutorials = pNoOfTutorials;
		maxTutorialSize = pMaxTutorialSize;

		tutorials = createInitialConfiguration();
	}

	/**
	 * Erzeugt die Startkonfiguration, d.h. eine Liste von Tutorien mit der korrekten
	 * Größe. Ein Tutorium ist dabei eine Liste von Studi-Objekten mit der maximalen
	 * Anzahl an Studierenden plus Eins als Größe.
	 *
	 * @return die Startkonfiguration als Liste von Studi-Listen
	 */
	private List<List<Studi>> createInitialConfiguration() {
		List<List<Studi>> btutorials = new ArrayList<>(noOfTutorials);
		List<Studi> tutorial = new ArrayList<Studi>(maxTutorialSize+1);
		
		for(int i = 0; i<noOfTutorials; i++){
			btutorials.add(tutorial);
		}
		
		tutorials = btutorials;
		
		return tutorials;
		
	}

	/**
	 * Verteilt die Studierenden aus der gegebenen Studi-Liste auf die Tutorien unter
	 * Berücksichtigung des gegebenen Wertes für die Mindestbewertung. Das bedeutet, dass
	 * Studierende niemals einem Tutorium zugeordnet werden dürfen, das sie mit einer
	 * Bewertung niedriger als die Mindestbewertung bewertet haben. Gleichzeitig dürfen
	 * pro Tutorium höchstens so viele Studierende zugeordnet werden, wie zuvor durch den
	 * Konstruktorparameter festgelegt (allgemein auf keinen Fall mehr als
	 * {@link TheaPP#MAX_STUDENTS_IN_TUTORIAL}).
	 *
	 * Wenn eine Verteilung der gegebenen Studierenden auf die Tutorien mit der gegebenen
	 * Mindestbewertung möglich und durchgeführt worden ist, wird {@code true}
	 * zurückgegeben, ansonsten {@code false}.
	 *
	 * @param pStudis
	 *          Die Liste mit den zu verteilenden Studis.
	 * @param pMinRating
	 *          Die Mindestbewertung für die Verteilung auf die Tutorien.
	 * @return {@code true} falls eine Verteilung möglich war, ansonsten {@code false}.
	 * @throws IllegalArgumentException
	 *           Falls die gegebene Mindestbewertung kleiner als 1 oder größer als
	 *           {@link TheaPP#MAX_RATING} ist, die Liste der zu verteilenden Studis
	 *           {@code null} ist oder nicht genug Platz in den Tutorien für die Studis
	 *           ist.
	 */
	public final boolean distributeStudis(final List<Studi> pStudis,
		final int pMinRating) {
		if (pMinRating < 1 || pMinRating > TheaPP.MAX_RATING) {
    		throw new IllegalArgumentException("invalid minimum rating");
    	}
    	lastStudi =pStudis.get(0);
    	tutorials.get(0).add(lastStudi);
    	lastStudi.setTutorial(0);
    	int j = 0;
    	while (!(constraintsSatisfied(tutorials, pMinRating))) {
    		tutorials.get(j).remove(lastStudi);
    		tutorials.get(j+1).add(lastStudi);
    		lastStudi.setTutorial(j+1);
    		j++;
    	}
    	int counter = 0;
    	while (counter != -1) {
    		counter = nextConfiguration(tutorials, pStudis, counter, pMinRating);
    		if (counter == pStudis.size()) {
    			return true;
    		}
    	}
        resetConfiguration();
        return false;
	}

	/**
	 * Berechnet die Folgekonfiguration der gegebenen Konfiguration für die gegebene Liste
	 * von Studierenden, den Index des zuletzt zugeordneten Studis der gegebenen
	 * Konfiguration und die Mindestbewertung. Die Berechnung der Folgekonfiguration
	 * geschieht "in-place", d.h. die gegebene Konfiguration wird direkt durch diese
	 * Methode verändert. Gibt den Index des bei der Berechnung der Folgekonfiguration
	 * zuletzt zugeordneten Studis zurück.
	 *
	 * Die Methode ist nicht private, sondern package-private, da sie durch JUnit-Tests
	 * getestet werden soll. Da das Paket vor einer Auslieferung versiegelt wird, ist sie
	 * damit von außen nicht aufrufbar. Aus diesem Grund werden die Parameterwerte auch
	 * nicht auf sinnvolle Werte überprüft. Insbesondere darf diese Methode nicht mit
	 * einer Konfiguration aufgerufen werden, für die es keine Folgekonfiguration gibt.
	 *
	 * @param pConfiguration
	 *          Die Konfiguration, deren Folgekonfiguration errechnet werden soll.
	 * @param pStudents
	 *          Die Liste der Studierenden.
	 * @param pLastStudiIndex
	 *          Der Index der zuletzt zugeordneten StudentIn der gegebenen Konfiguration.
	 * @param pMinRating
	 *          Die Mindestbewertung.
	 * @return Den Index des zuletzt zugeordneten Studis oder -1 wenn es keine
	 *         Folgekonfiguration gibt und nicht die letzte StudentIn zugeordnet wurde
	 *         oder die Anzahl der StudentInnen wenn alle StudentInnen erfolgreich
	 *         zugeordnet wurden.
	 */
	final int nextConfiguration(final List<List<Studi>> pConfiguration,
		final List<Studi> pStudents, final int pLastStudiIndex, final int pMinRating) {		
		
		//Fall 1
		if(constraintsSatisfied(pConfiguration, pMinRating) && (pLastStudiIndex == pStudents.size())){
			return pStudents.size();
		}
		//Fall 2
		if(constraintsSatisfied(pConfiguration, pMinRating) && (pLastStudiIndex < pStudents.size())){
			pConfiguration.get(1).add(pStudents.get(pLastStudiIndex+1));
			return pLastStudiIndex;
		}
		//Fall 3 ???
		if(!this.constraintsSatisfied(pConfiguration, pMinRating) && (pConfiguration.indexOf(pLastStudiIndex) < noOfTutorials)) { // && j < t 
	         int j = 0;
	          
	          for(int i = noOfTutorials; i>-1; i--){
					if( (pConfiguration.get(i)).contains(pStudents.get(pLastStudiIndex)) ){
						 j = i;
					}
	          }
	          
			 pConfiguration.get(j).remove(pStudents.get(pLastStudiIndex));
			 pConfiguration.get(j+1).add(pStudents.get(pLastStudiIndex));
			 
	          return pLastStudiIndex;
	          
		}
		//Fall 4
		else {
				 int k = searchK(pStudents, pConfiguration, pLastStudiIndex);
				if(k < 0){
					this.resetConfiguration();
					return -1;
				}
				else{
					for(int i = k+1; i <= pLastStudiIndex; i++){
						(pConfiguration.get(noOfTutorials-2)).remove(pStudents.get(i));
					}
					for(int i = noOfTutorials-2; i > -1 ; i--){
					
							if( (pConfiguration.get(i)).contains(pStudents.get(k)) ){
								(pConfiguration.get(i)).remove(pStudents.get(k));
								(pConfiguration.get(i+1)).add(pStudents.get(k));
							}
					}
				
				tutorials = pConfiguration;
				return k;
				}
		}
	}

	/**
	 * Prüft, ob die gegebene Konfiguration hinsichtlich der gegebenen Mindestbewertung
	 * gültig ist. Wenn das so ist, wird {@code true} zurückgegeben, sonst {@code false}.
	 *
	 * Die Methode ist nicht private, sondern package-private, da sie durch JUnit-Tests
	 * getestet werden soll. Da das Paket vor einer Auslieferung versiegelt wird, ist sie
	 * damit von außen nicht aufrufbar.
	 *
	 * @param pConfiguration
	 *          Die zu prüfende Konfiguration als Liste von Studi-Listen.
	 * @param pMinRating
	 *          Die Mindestbewertung der Studis für ihre Tutorien.
	 * @return {@code true} falls kein Tutorium mehr als die erlaubte Maximalzahl von
	 *         Studis enthält und kein Studi einem Tutorium zugeordnet ist, für das sie
	 *         oder er eine geringere Bewertung als die gegebene Mindestbewertung vergeben
	 *         hat, ansonsten {@code false}.
	 */
	final boolean constraintsSatisfied(final List<List<Studi>> pConfiguration,
		final int pMinRating) {
		
       
		for(int i= 0; i<noOfTutorials; i++) {
		 for(int j = 0; j<maxTutorialSize; j++){
			 
		  Studi student = pConfiguration.get(i).get(j);
			 if(student.getRating(i) < pMinRating){
				 return false;
			 }
		 }
		}
		return true;
	}

	/**
	 * Setzt die Konfiguration auf die Startkonfiguration zurück.
	 */
	private void resetConfiguration() {
		for (final List<Studi> tutorial : tutorials) {
			tutorial.clear();
		}
	}

	/**
	 * Gibt die aktuelle Verteilung der Studierenden in der Konsole aus. Dabei steht in
	 * jeder Zeile ein Tutorium und die Teilnehmenden werden mit ihrem Account und der
	 * Bewertung für ihr aktuell zugeordnetes Tutorium durch Komma getrennt aufgeführt.
	 */
	private void printDistribution() {
		int tutNo = 1;
		for (final List<Studi> tutorial : tutorials) {
			System.out.print(String.format("Tut %02d:", tutNo));
			System.out.println(Arrays.toString(tutorial.toArray()));
			tutNo++;
		}
	}
	
	/**
	 * Hilfsmethode
	 * Sucht das k' aus Fall 4 und gibt dieses zur�ck, falls es eines gibt.
	 * Ansonsten wird -1 zur�ckgegeben.
	 */
	private int searchK(List<Studi> pStudents, List<List<Studi>> pConfiguration, int pLastStudiIndex){
		int k = -1;
		//TODO: Fehler beheben
		
		for(int i = noOfTutorials-2; i > -1; i--){
			for(int j = 0; j < maxTutorialSize; j++){
				if(
					pConfiguration.get(i)).get(j) != null
					&& k < pStudents.indexOf( ((pConfiguration.get(i)).get(j))
					&& for( int l = k+1, l <= pLastStudiIndex, l++) {
							(pConfiguration.get(noOfTutorials-1)).contains(pStudents.get(l));
					   }
					){
						k = pStudents.indexOf( ((pConfiguration.get(i)).get(j)) );
				}
			}
		}
		return k;
	}

	/**
	 * Hilfmethode. Dient zur Überpruefung der Parameter, also ob
	 * es ausreichend viele Plätze in den Tutorien für alle Studenten gibt und ob jeder
	 * Student mindestens ein Tutorium mit dem Minimum-Rating bewertet hat. In diesem Fall
	 * wird {@code true} zurueckgegeben. 
	 * Ist eine dieser beiden Bedingungen verletzt, wird {@code false} zurueckgegeben.
	 * 
	 * @param pStudis
	 * @param pNoOfTutorials
	 * @param pMaxTutorialSize
	 * @param pMinRating
	 * @return
	 */

	private boolean validSetup(final List<Studi> pStudis, final int pNoOfTutorials,
			final int pMaxTutorialSize, final int pMinRating) {
			if (pStudis.size() > pNoOfTutorials * pMaxTutorialSize) {
			return false; }
			boolean hasMinRat;
			for (Studi studi :pStudis) {
			hasMinRat = false;
			for (int k = 0 ; k < pNoOfTutorials ; k++) {
				if (studi.getRating(k) >= pMinRating) {
    				hasMinRat = true;
    				break;
    				}
    			}
    		if (hasMinRat) {
    			continue;
    		}
    		return false;
		}
    	return true;
    	}
 	 
 	
    	//TODO JavaDoc
				
	/**
	 * Liest die Dateien FiveTutorials.csv, SevenTutorials.csv und
	 * TenTutorials.csv ein, parst sie und versucht die Studierenden auf die Tutorien zu
	 * verteilen. Wenn die Verteilung gelingt, wird sie auf der Konsole ausgegeben.
	 *
	 * @param args
	 *          Werden ignoriert.
	 * @throws TheaPPParseException
	 *           Falls eine der Dateien nicht eingelesen werden kann.
	 */
	public static void main(final String[] args)  {
		final String filename = args[0];
    	final int noOfTutorials = Integer.parseInt(args[1]);
    	final int maxTutorialSize = Integer.parseInt(args[2]);
    	final int minRating = Integer.parseInt(args[3]);
    	final TheaPP thea = new TheaPP(noOfTutorials, maxTutorialSize);
    	try {
    		final List<Studi> studiList = (new TheaPPParser(noOfTutorials )).parseStudents(filename);
    		if(!(thea.validSetup(studiList, noOfTutorials, maxTutorialSize, minRating))) {
    			throw new IllegalArgumentException("invalid setup");
    		}
    		if (thea.distributeStudis(studiList, minRating)) {
    			thea.printDistribution();
    		}
    		else {
    			throw new IllegalArgumentException("ivalid setup");
    		}
    	}
    		catch (Exception e) {
    			System.out.println(e);
    		}
    	}
	}

