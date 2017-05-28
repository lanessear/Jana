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
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Random;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import pi.uebung02.Studi;
import pi.uebung02.TheaPP;
import pi.uebung02.TheaPPParser;
import pi.uebung02.exceptions.TheaPPParseException;
import pi.uebung02.exceptions.TheaPPParseException.ParseError;

/**
 * Testet die nicht-trivialen Methoden der Klasse TheaPPParser.
 *
 * Da parseStudents die beiden anderen Methoden verwendet, wird nur ein erfolgreiches
 * Einlesen getestet. Damit erreichen wir dann trotzdem eine Anweisungsabdeckung von
 * 100%.
 *
 * @author Karsten Hölscher
 * @version 2017-04-21
 *
 */
public final class TheaPPParserTest {

	/**
	 * Erzeugt ein temporäres Verzeichnis für temporäre Dateien, die für Tests benötigt
	 * werden.
	 */
	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	@Test(expected = IllegalArgumentException.class)
	public void createParser_noOfTutorialsLow_Exception() {
		new TheaPPParser(-1);
	}

	@Test
	public void createParser_noOfTutorialLowerBound_newParser() {
		assertNotNull(new TheaPPParser(0));
	}

	@Test(expected = TheaPPParseException.class)
	public void parseStudents_noneExistingFile_Exception() throws Exception {
		String baseFilename = "Test";
		String extension = ".txt";
		int counter = 0;
		File testFile;
		String filename;
		do {
			filename = baseFilename + (counter++) + extension;
			testFile = new File(filename);
		} while (testFile.exists());
		// Datei mit filename existiert nicht
		TheaPPParser parser = new TheaPPParser(3);
		parser.parseStudents(filename);
	}

	@Test(expected = IllegalArgumentException.class)
	public void parseStudents_nullFilename_Exception() throws Exception {
		TheaPPParser parser = new TheaPPParser(4);
		parser.parseStudents(null);
	}

	@Test(expected = IllegalArgumentException.class)
	public void parseStudents_emptyFilename_Exception() throws Exception {
		TheaPPParser parser = new TheaPPParser(3);
		parser.parseStudents("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void parseStudents_BlankFilename_Exception() throws Exception {
		TheaPPParser parser = new TheaPPParser(5);
		parser.parseStudents("   ");
	}

	@Test
	public void parseStudents_emptyFile_emptyList() throws Exception {
		/**
		 * Erzeugt eine temporäre Datei (im temporären Verzeichnis (s. o.), die nach
		 * Ausführung des Tests wieder gelöscht wird.
		 */
		final File testFile = tempFolder.newFile("Empty.csv");
		TheaPPParser parser = new TheaPPParser(5);
		List<Studi> list = parser.parseStudents(testFile.getAbsolutePath());
		assertEquals(0, list.size());
	}

	@Test
	public void parseStudents_emptyAccount_Exception() throws Exception {
		/*
		 * Achtung: Hier wird angenommen, dass TheaPP#MAX_RATING einen sinnvollen Wert
		 * enthält.
		 */
		final File testFile = tempFolder.newFile("Test.csv");

		StringBuilder lineBuilder = new StringBuilder("");
		for (int i = 0; i < 4; i++) {
			lineBuilder.append(";" + TheaPP.MAX_RATING);
		}
		final String output = String.format("%s%n", lineBuilder.toString());

		Files.write(testFile.toPath(), output.getBytes(), StandardOpenOption.APPEND);

		TheaPPParser parser = new TheaPPParser(4);
		try {
			parser.parseStudents(testFile.getAbsolutePath());
			fail("No Exception for invalid line format");
		} catch (final TheaPPParseException ex) {
			if (ex.getParseError() != ParseError.INVALID_ACCOUNT) {
				fail("Wrong exception with cause: " + ex.getParseError());
			}
		}

	}

	@Test
	public void parseStudents_tooManyEntries_Exception() throws Exception {
		/*
		 * Achtung: Hier wird angenommen, dass TheaPP#MAX_RATING einen sinnvollen Wert
		 * enthält.
		 */
		final File testFile = tempFolder.newFile("Test.csv");

		StringBuilder lineBuilder = new StringBuilder("account");
		for (int i = 0; i < 5; i++) {
			lineBuilder.append(";" + TheaPP.MAX_RATING);
		}
		final String output = String.format("%s%n", lineBuilder.toString());

		Files.write(testFile.toPath(), output.getBytes(), StandardOpenOption.APPEND);

		TheaPPParser parser = new TheaPPParser(4);
		try {
			parser.parseStudents(testFile.getAbsolutePath());
			fail("No Exception for invalid line format");
		} catch (final TheaPPParseException ex) {
			if (ex.getParseError() != ParseError.WRONG_NO_OF_ENTRIES) {
				fail("Wrong exception with cause: " + ex.getParseError());
			}
		}
	}

	@Test
	public void parseStudents_tooFewEntries_Exception() throws Exception {
		/*
		 * Achtung: Hier wird angenommen, dass TheaPP#MAX_RATING einen sinnvollen Wert
		 * enthält.
		 */
		final File testFile = tempFolder.newFile("Test.csv");

		StringBuilder lineBuilder = new StringBuilder("account");
		for (int i = 0; i < 4; i++) {
			lineBuilder.append(";" + TheaPP.MAX_RATING);
		}
		final String output = String.format("%s%n", lineBuilder.toString());

		Files.write(testFile.toPath(), output.getBytes(), StandardOpenOption.APPEND);

		TheaPPParser parser = new TheaPPParser(5);
		try {
			parser.parseStudents(testFile.getAbsolutePath());
			fail("No Exception for invalid line format");
		} catch (final TheaPPParseException ex) {
			if (ex.getParseError() != ParseError.WRONG_NO_OF_ENTRIES) {
				fail("Wrong exception with cause: " + ex.getParseError());
			}
		}
	}

	@Test
	public void parseStudents_negativeRating_Exception() throws Exception {
		/*
		 * Achtung: Hier wird angenommen, dass TheaPP#MAX_RATING einen sinnvollen Wert
		 * enthält.
		 */
		final File testFile = tempFolder.newFile("Test.csv");

		StringBuilder lineBuilder = new StringBuilder("account");
		lineBuilder.append(";-1");
		for (int i = 1; i < 4; i++) {
			lineBuilder.append(";" + TheaPP.MAX_RATING);
		}
		final String output = String.format("%s%n", lineBuilder.toString());

		Files.write(testFile.toPath(), output.getBytes(), StandardOpenOption.APPEND);

		TheaPPParser parser = new TheaPPParser(4);
		try {
			parser.parseStudents(testFile.getAbsolutePath());
			fail("No Exception for invalid line format");
		} catch (final TheaPPParseException ex) {
			if (ex.getParseError() != ParseError.INVALID_RATING) {
				fail("Wrong exception with cause: " + ex.getParseError());
			}
		}
	}

	@Test
	public void parseStudents_RatingTooHigh_Exception() throws Exception {
		/*
		 * Achtung: Hier wird angenommen, dass TheaPP#MAX_RATING einen sinnvollen Wert
		 * enthält.
		 */
		final File testFile = tempFolder.newFile("Test.csv");

		StringBuilder lineBuilder = new StringBuilder("account");
		lineBuilder.append(";" + TheaPP.MAX_RATING + 1);
		for (int i = 1; i < 5; i++) {
			lineBuilder.append(";" + TheaPP.MAX_RATING);
		}
		final String output = String.format("%s%n", lineBuilder.toString());

		Files.write(testFile.toPath(), output.getBytes(), StandardOpenOption.APPEND);

		TheaPPParser parser = new TheaPPParser(5);
		try {
			parser.parseStudents(testFile.getAbsolutePath());
			fail("No Exception for invalid line format");
		} catch (final TheaPPParseException ex) {
			if (ex.getParseError() != ParseError.INVALID_RATING) {
				fail("Wrong exception with cause: " + ex.getParseError());
			}
		}
	}

	@Test
	public void parseStudent_RatingNotANumber_Exception() throws Exception {
		/*
		 * Achtung: Hier wird angenommen, dass TheaPP#MAX_RATING einen sinnvollen Wert
		 * enthält.
		 */
		final File testFile = tempFolder.newFile("Test.csv");

		StringBuilder lineBuilder = new StringBuilder("account;a");
		for (int i = 1; i < 5; i++) {
			lineBuilder.append(";" + TheaPP.MAX_RATING);
		}
		final String output = String.format("%s%n", lineBuilder.toString());

		Files.write(testFile.toPath(), output.getBytes(), StandardOpenOption.APPEND);

		TheaPPParser parser = new TheaPPParser(5);
		try {
			parser.parseStudents(testFile.getAbsolutePath());
			fail("No Exception for invalid line format");
		} catch (final TheaPPParseException ex) {
			if (ex.getParseError() != ParseError.INVALID_RATING) {
				fail("Wrong exception with cause: " + ex.getParseError());
			}
		}
	}

	@Test
	public void parseStudents_correctRating_correctStudiObject() throws Exception {
		/*
		 * Achtung: Hier wird angenommen, dass TheaPP#MAX_RATING einen sinnvollen Wert
		 * enthält.
		 */
		final File testFile = tempFolder.newFile("Test.csv");

		final int[] ratings = new int[7];
		Random rand = new Random();
		for (int i = 0; i < ratings.length; i++) {
			ratings[i] = rand.nextInt(TheaPP.MAX_RATING + 1);
		}
		StringBuilder lineBuilder = new StringBuilder("account");
		for (int r : ratings) {
			lineBuilder.append(";" + r);
		}
		final String output = String.format("%s%n", lineBuilder.toString());

		Files.write(testFile.toPath(), output.getBytes(), StandardOpenOption.APPEND);

		TheaPPParser parser = new TheaPPParser(7);
		List<Studi> list = parser.parseStudents(testFile.getAbsolutePath());
		assertEquals(1, list.size());
		for (final Studi studi : list) {
			for (int i = 0; i < ratings.length; i++) {
				assertEquals(ratings[i], studi.getRating(i));
			}
		}

	}

}
