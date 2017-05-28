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
package pi.uebung02.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import pi.uebung02.Studi;
import pi.uebung02.TheaPP;

/**
 * Testet die nicht-trivialen Methoden der Klasse Studi. Dabei werden die trivialen "aus
 * Versehen" mitgetestet, d. h. wir erreichen mit dieser Testklasse 100% Anweisungs- und
 * Zweigabdeckung und sogar 100% Mehrfachbedingungsüberdeckung.
 *
 * @author Karsten Hölscher
 * @version 2017-04-021
 *
 */
public final class StudiTest {

	/**
	 * Ein zu testendes Studi-Objekt.
	 */
	private Studi studi;

	/**
	 * Erzeugt ein zu testendes Studi-Objekt.
	 */
	private void createStudi() {
		/*
		 * Achtung: Hier wird angenommen, dass TheaPP#MAX_RATING einen sinnvollen Wert
		 * enthält.
		 */
		final int[] ratings = new int[4];
		for (int i = 1; i < ratings.length; i++) {
			ratings[i] = TheaPP.MAX_RATING;
		}
		ratings[0] = 0;
		studi = new Studi("account", ratings);
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructor_nullAccount_Exception() {
		final int[] ratings = {1, 2, 3};
		studi = new Studi(null, ratings);
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructor_nullRatings_Exception() {
		studi = new Studi("test", null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructor_emptyAccount_Exception() {
		final int[] ratings = {1, 2, 3};
		studi = new Studi("  ", ratings);
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructor_shortArray_Exception() {
		final int[] ratings = new int[0];
		studi = new Studi("account", ratings);
	}

	@Test(expected = NumberFormatException.class)
	public void constructor_ArrayEntryNegative_Exception() {
		/*
		 * Achtung: Hier wird angenommen, dass TheaPP#MAX_RATING einen sinnvollen Wert
		 * enthält.
		 */
		final int[] ratings = new int[4];
		for (int i = 1; i < ratings.length; i++) {
			ratings[i] = TheaPP.MAX_RATING;
		}
		ratings[0] = -1;
		studi = new Studi("account", ratings);
	}

	@Test(expected = NumberFormatException.class)
	public void constructor_ArrayEntryValueTooHigh_Exception() {
		/*
		 * Achtung: Hier wird angenommen, dass TheaPP#MAX_RATING einen sinnvollen Wert
		 * enthält.
		 */
		final int[] ratings = new int[3];
		for (int i = 1; i < ratings.length; i++) {
			ratings[i] = TheaPP.MAX_RATING;
		}
		ratings[0] = TheaPP.MAX_RATING + 1;
		studi = new Studi("account", ratings);
	}

	@Test
	public void constructor_maxArrayLengthValidArrayEntryValues_Object() {
		/*
		 * Achtung: Hier wird angenommen, dass TheaPP#MAX_RATING einen sinnvollen Wert
		 * enthält.
		 */
		final int[] ratings = new int[7];
		for (int i = 1; i < ratings.length; i++) {
			ratings[i] = TheaPP.MAX_RATING;
		}
		ratings[0] = 0;
		studi = new Studi("account", ratings);
	}

	@Test
	public void constructor_minArrayLengthValidArrayEntryValues_Object() {
		/*
		 * Achtung: Hier wird angenommen, dass TheaPP#MAX_RATING einen sinnvollen Wert
		 * enthält.
		 */
		final int[] ratings = new int[5];
		for (int i = 1; i < ratings.length; i++) {
			ratings[i] = TheaPP.MAX_RATING;
		}
		ratings[0] = 0;
		studi = new Studi("account", ratings);
	}

	@Test
	public void setTutorial_lowestPossibleValue_noException() {
		createStudi();
		studi.setTutorial(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setTutorial_IndexTooLow_Exception() {
		createStudi();
		studi.setTutorial(-2);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getRating_negativeValue_Exception() {
		createStudi();
		studi.getRating(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void getRating_ValueTooHigh_Exception() {
		/*
		 * Achtung: Hier wird angenommen, dass TheaPP#MAX_NO_OF_TUTORIALS einen sinnvollen
		 * Wert enthält.
		 */
		createStudi();
		studi.getRating(4);
	}

	@Test
	public void getRating_firstAndLastTutorial_correctRatings() {
		/*
		 * Achtung: Hier wird angenommen, dass TheaPP#MAX_NO_OF_TUTORIALS einen sinnvollen
		 * Wert enthält.
		 */
		createStudi();
		int rating = studi.getRating(0);
		assertEquals(rating, 0);
		rating = studi.getRating(3);
		assertEquals(rating, TheaPP.MAX_RATING);
	}

	@Test
	public void toString_noTutorial_correctString() {
		createStudi();
		assertEquals("account[-1]", studi.toString());
	}

	@Test
	public void toString_tutorial_correctString() {
		/*
		 * Achtung: Hier wird angenommen, dass TheaPP#MAX_NO_OF_TUTORIALS einen sinnvollen
		 * Wert enthält.
		 */
		createStudi();
		studi.setTutorial(1);
		assertEquals(String.format("account[%d]", TheaPP.MAX_RATING), studi.toString());
	}

	@Test
	public void equals_differentAccount_false() {
		final int[] ratings = new int[3];
		for (int i = 1; i < ratings.length; i++) {
			ratings[i] = TheaPP.MAX_RATING;
		}
		ratings[0] = 0;
		Studi s1 = new Studi("studi1", ratings);
		Studi s2 = new Studi("studi2", ratings);
		assertFalse(s1.equals(s2));
	}

	@Test
	public void equals_nullParameter_false() {
		createStudi();
		assertFalse(studi.equals(null));
	}

	@Test
	public void equals_identicObjects_true() {
		createStudi();
		assertTrue(studi.equals(studi));
	}

	@Test
	public void equals_identicAccoutDifferentRating_true() {
		final int[] ratings1 = new int[4];
		for (int i = 0; i < ratings1.length; i++) {
			ratings1[i] = TheaPP.MAX_RATING;
		}
		final int[] ratings2 = new int[4];
		for (int i = 0; i < ratings2.length; i++) {
			ratings2[i] = TheaPP.MAX_RATING;
		}
		Studi s1 = new Studi("account", ratings1);
		Studi s2 = new Studi("account", ratings2);
		assertTrue(s1.equals(s2));
	}

	@Test
	public void hashCode_equalObjects_sameHashCode() {
		final int[] ratings1 = new int[5];
		for (int i = 0; i < ratings1.length; i++) {
			ratings1[i] = TheaPP.MAX_RATING;
		}
		final int[] ratings2 = new int[5];
		for (int i = 0; i < ratings2.length; i++) {
			ratings2[i] = TheaPP.MAX_RATING;
		}
		Studi s1 = new Studi("account", ratings1);
		Studi s2 = new Studi("account", ratings2);
		assertEquals(s1.hashCode(), s2.hashCode());
	}
}
