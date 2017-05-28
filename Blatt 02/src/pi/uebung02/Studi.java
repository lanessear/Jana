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
package pi.uebung02;

/**
 * Realisiert eine StudentIn. Eine StudentIn hat einen Account und eine bestimmte Anzahl
 * von Bewertungen (zwischen 0 und {@link TheaPP#MAX_RATING} inklusive) für eine
 * bestimmte Anzahl von Tutorien. Zusätzlich hat eine StudentIn den Index des Tutoriums,
 * dem sie aktuell zugeordnet ist. Dieser Wert darf -1 sein um anzuzeigen, dass die
 * StudentIn aktuell keinem Tutorium zugeordnet ist.
 *
 * @author Karsten Hölscher
 * @version 2017-04-21
 */
public final class Studi {

	/**
	 * Der Account dieser StudentIn.
	 */
	private final String account;

	/**
	 * Der Index des Tutoriums, dem diese StudentIn aktuell zugeordnet ist. Der Wert -1
	 * bedeutet, dass diese StudentIn aktuell keinem Tutorium zugeordnet ist.
	 */
	private int tutorialIndex;

	/**
	 * Die Bewertungen der Tutorien.
	 */
	private int[] rating;

	/**
	 * Erzeugt eine neue StudentIn mit dem gegebenen Account und ein Array mit den
	 * Tutoriumsbewertungen.
	 *
	 * @param pAccount
	 *          Der Account der neuen StudentIn.
	 * @param pRatings
	 *          Die Tutoriums-Bewertungen des neuen StudentIn.
	 * @throws IllegalArgumentException
	 *           Falls mindestens einer der Parameterwerte {@code null} oder leer ist.
	 * @throws NumberFormatException
	 *           Falls mindestens eine Bewertungen außerhalb des erlaubten Bereichs liegt.
	 */
	public Studi(final String pAccount, final int[] pRatings) {
		if (pAccount == null || pAccount.trim().isEmpty()) {
			throw new IllegalArgumentException("The account is empty!");
		}
		if (pRatings == null || pRatings.length == 0) {
			throw new IllegalArgumentException("The number of ratings is not valid.");
		}
		account = pAccount;
		/*
		 * Den Array-Parameter kopieren wir, sonst ist das Bewertungsattribut nicht wirklich
		 * private. Dabei prüfen wir die Werte gleich mit.
		 */
		rating = new int[pRatings.length];
		for (int i = 0; i < pRatings.length; i++) {
			if (pRatings[i] < 0 || pRatings[i] > TheaPP.MAX_RATING) {
				throw new NumberFormatException(
					String.format("Value of ratings parameter with index %d is not valid.", i));
			}
			rating[i] = pRatings[i];
		}
		tutorialIndex = -1;
	}

	/**
	 * Gibt den Index des Tutoriums zurück, dem diese StudentIn aktuell zugeordnet ist.
	 *
	 * @return Den Index des Tutoriums, dem diese StudentIn aktuell zugeordnet ist oder
	 *         -1, falls sie aktuell keinem Tutorium zugeordnet ist.
	 */
	public int getTutorial() {
		return tutorialIndex;
	}

	/**
	 * Setzt den Index des aktuellen Tutoriums dieser StudentIn auf den übergebenen Index.
	 * Um eine StudentIn keinem Tutorium zuzuordnen, ist der Index -1 zu verwenden. .
	 *
	 * @param pTutorialIndex
	 *          Der Index des Tutoriums, dem diese StudentIn zugeordnet werden soll, oder
	 *          -1 falls diese StudentIn in keinem Tutorium sein soll.
	 * @throws IllegalArgumentException
	 *           Falls der Parameterwert kleiner als -1 und größer als die oder gleich der
	 *           zuvor festgelegten Anzahl von Tutorien ist.
	 */
	public void setTutorial(final int pTutorialIndex) {
		if (pTutorialIndex < -1 || pTutorialIndex >= rating.length) {
			throw new IllegalArgumentException(
				String.format("Parameter value is not in the range 0 - %d.", rating.length));
		}
		tutorialIndex = pTutorialIndex;
	}

	/**
	 * Gibt die Bewertung dieser StudentIn für das Tutorium mit dem gegebenen Index
	 * zurück.
	 *
	 * @param pTutorialIndex
	 *          Der Index des Tutoriums, dessen Bewertung gesucht ist.
	 * @return Die Bewertung dieser StudentIn für das Tutoriums mit dem gegebenen Index.
	 * @throws IllegalArgumentException
	 *           Falls der Tutoriumsindex kleiner als 0 oder größer oder gleich der Anzahl
	 *           von Bewertungen ist, die für diese StudentIn vorliegen.
	 */
	public int getRating(final int pTutorialIndex) {
		if (pTutorialIndex < 0 || pTutorialIndex >= rating.length) {
			throw new IllegalArgumentException(
				String.format("Parameter value is not in the range 0 - %d.", rating.length));
		}
		return rating[pTutorialIndex];
	}

	@Override
	public String toString() {
		int rate = -1;
		if (tutorialIndex > -1) {
			rate = rating[tutorialIndex];
		}
		return account + "[" + rate + "]";
	}

	@Override
	public boolean equals(final Object that) {
		if (this == that) {
			return true;
		}
		if (!(that instanceof Studi)) {
			return false;
		}
		final Studi other = (Studi) that;
		return account.equals(other.account);
	}

	@Override
	public int hashCode() {
		return account.hashCode();
	}

}
