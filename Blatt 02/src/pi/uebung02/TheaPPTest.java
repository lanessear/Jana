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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * Testet das Verhalten des Konstruktors und der Methoden
 * {@link TheaPP#distributeStudis(List, int)} und {@link TheaPP#getStudiAt(int, int)} im
 * Falle ungültiger Argumente.
 *
 * TODO: Beschreibung und Autorenschaft ergänzen, Version anpassen
 *
 * @author Karsten Hölscher
 * @version 2017-04-24
 *
 */
public final class TheaPPTest {

	/**
	 * Liste mit Studis, wird vor jedem Test gefüllt.
	 */
	private List<Studi> studis;

	// TODO: ggf. weitere Attribute einfügen

	@Before
	public void init() {
		studis = new ArrayList<>(9);
		// TODO: vmtl. Liste mit Studi-Objekten füllen
		// TODO: ggf. weitere Attribute initialisieren
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructor_NoOfTutorialsTooLow_Exception() {
		new TheaPP(-1, 5);
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructor_StudentsInTutorialTooLow_Exception() {
		new TheaPP(2, -1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void distributeStudis_StudiListNull_Exception() {
		final TheaPP theaPP = new TheaPP(3, 3);
		theaPP.distributeStudis(null, TheaPP.MAX_RATING);
	}

	@Test(expected = IllegalArgumentException.class)
	public void distributeStudis_MinRatingTooLow_Exception() {
		final TheaPP theaPP = new TheaPP(3, 3);
		theaPP.distributeStudis(studis, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void distributeStudis_MinRatingTooHigh_Exception() {
		final TheaPP theaPP = new TheaPP(3, 3);
		theaPP.distributeStudis(studis, TheaPP.MAX_RATING + 1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void distributeStudis_TooFewTutorials_Exception() {
		final TheaPP theaPP = new TheaPP(3, 2);
		theaPP.distributeStudis(studis, 3);
	}

	@Test
	public void nextConfiguration_ConfigEmpty_correctNextConfiguration() {
		/*
		 * TODO: Dieser Test funktioniert nur, wenn die Liste studis sinnvoll befüllt wird.
		 */
		/*
		 * 0: leer => 0: s1
		 *
		 * 1: leer => 1: leer
		 *
		 * 2: leer => 2: leer
		 *
		 */
		final List<List<Studi>> config = new ArrayList<List<Studi>>(3);
		for (int i = 0; i < 3; i++) {
			config.add(new ArrayList<Studi>(4));
		}

		final TheaPP theaPP = new TheaPP(3, 3);

		/*
		 * Vor dem Aufruf der Methode wird eine Kopie der Konfiguration erzeugt.
		 *
		 * Verwendung des Copy-Konstruktors erzeugt eine neue, flache Kopie (d. h. die
		 * Studi-Objekte werden nicht kopiert).
		 */
		final List<List<Studi>> controlConfig = new ArrayList<>(config);

		final int index = theaPP.nextConfiguration(config, studis, -1, 3);

		// Prüft, ob Studi 0 zuletzt bearbeitet wurde
		assertEquals(0, index);
		// Nun den Studi in das erste Tutorium der Konfigurationskopie verschieben
		controlConfig.get(0).add(studis.get(0)); // ergibt die erwartete Konfiguration
		// Prüfen, ob die errechnete Konfiguration der erwarteten Konfiguration entspricht
		assertEquals(controlConfig, config);
	}

	@Test
	public void nextConfiguration_ConfigEmpty_correctNextConfigurationAlternative() {
		/*
		 * TODO: Dieser Test funktioniert nur, wenn die Liste studis sinnvoll befüllt wird.
		 */
		/*
		 * 0: leer => 0: s1
		 *
		 * 1: leer => 1: leer
		 *
		 * 2: leer => 2: leer
		 *
		 */
		final List<List<Studi>> config = new ArrayList<List<Studi>>(3);
		for (int i = 0; i < 3; i++) {
			config.add(new ArrayList<Studi>(4));
		}

		final TheaPP theaPP = new TheaPP(3, 3);

		final int index = theaPP.nextConfiguration(config, studis, -1, 3);

		// Prüft, ob Studi 0 zuletzt bearbeitet wurde
		assertEquals(0, index);

		// Prüfen, ob Studi 0 in Tutorium 0 ist:
		assertTrue(config.get(0).contains(studis.get(0)));
		// Prüfen, ob Studi 0 einzige StudentIn in Tutorium 0 ist:
		assertTrue(config.get(0).size() == 1);
		// Prüfen, ob die anderen Tutorien leer sind
		assertTrue(config.get(1).isEmpty());
		assertTrue(config.get(2).isEmpty());
	}

	// TODO: weitere Tests hinzufügen

}
