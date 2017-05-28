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

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import pi.uebung02.exceptions.TheaPPParseException;
import pi.uebung02.exceptions.TheaPPParseException.ParseError;

/**
 * Realisiert eine Hilfsklasse, die das Einlesen der Tutoriumsbewertungen realisiert. Es
 * gibt Methode, die eine Textdatei im geforderten Format einliest und eine dazu
 * passende Liste von Studi-Objekten erzeugt und zurück gibt.
 *
 * @author Karsten Hölscher
 * @version 2017-04-24
 */
public final class TheaPPParser {

	/**
	 * Die Anzahl der Tutorien, also die Anzahl der Bewertungen pro Zeile.
	 */
	private final int noOfTutorials;

	/**
	 * Erzeugt einen neuen Parser die gegebene Anzahl an Tutorien.
	 *
	 * @param pNoOfTutorials
	 *          Die Anzahl der Tutorien.
	 * @throws IllegalArgumentException
	 * @throws IllegalArgumentException
	 *           Falls die Anzahl der Tutorien negativ ist.
	 *
	 */
	public TheaPPParser(final int pNoOfTutorials) {
		if (pNoOfTutorials < 0) {
			throw new IllegalArgumentException("Number of tutorials must not be negative!");
		}
		noOfTutorials = pNoOfTutorials;
	}

	/**
	 * Erstellt aus dem gegebenen Parameterwert ein entsprechendes Studi-Objekt. Der
	 * Parameterwert muss dabei wie folgt aufgebaut sein:
	 *
	 * <account>;<sterne1>;....;<sterneN>
	 *
	 * Die einzelnen Einträge sind jeweils durch ein Semikolon getrennt. Das erste Element
	 * ist der Account des Studierenden. Er darf kein Semikolon enthalten. Es folgen die
	 * Bewertungen des Studierenden für die Tutorien 1 bis N. N muss dabei der Anzahl der
	 * Tutorien entsprechen, die dem Konstruktor dieses Parsers als Argument übergeben
	 * wurden.
	 *
	 * Eine Bewertung ist eine ganze Zahl zwischen 0 (inklusive) und
	 * {@link TheaPP#MAX_RATING} (inklusive). 0 bedeutet dabei die niedrigste Bewertung.
	 *
	 * Da es sich um eine private Methode handelt, wird das Argument nicht auf
	 * Plausibilität geprüft (keine Prüfung auf {@code null} oder leere Zeichenkette).
	 *
	 * @param pLine
	 *          Zeile mit den Daten des Studierenden.
	 * @return Ein Studi-Objekt gemäß der gegebenen Zeile.
	 * @throws TheaPPParseException
	 *           Falls die Zeile nicht dem geforderten Format entspricht.
	 */
	private Studi parseStudent(final String pLine) throws TheaPPParseException {
		final String[] entries = pLine.split(";");
		final int expectedNoOfEntries = noOfTutorials + 1;
		if (entries.length != expectedNoOfEntries) {
			throw new TheaPPParseException(
				String.format("Wrong number %d of entries (expected %d)", entries.length,
					expectedNoOfEntries),
				ParseError.WRONG_NO_OF_ENTRIES);
		}
		int j = 0;
		try {
			final int[] ratings = new int[noOfTutorials];
			for (j = 1; j < entries.length; j++) {
				int rating = Integer.parseInt(entries[j]);
				ratings[j - 1] = rating;
			}
			return new Studi(entries[0], ratings);
		} catch (final NumberFormatException ex) {
			throw new TheaPPParseException("Invalid rating", ParseError.INVALID_RATING);
		} catch (final IllegalArgumentException ex) {
			/*
			 * Exception aus dem Studi-Konstruktor. Entweder ist der Account ungültig (null
			 * oder leer), oder mindestens eine Bewertung ist ungültig.
			 */
			throw new TheaPPParseException("Invalid account", ParseError.INVALID_ACCOUNT, ex);
		}
	}

	/**
	 * Liest die UTF-8 kodierte Textdatei, deren Pfadname durch den Parameter beschrieben
	 * wird, zeilenweise ein und erzeugt aus jeder Zeile jeweils ein Objekt vom Typ
	 * {@link Studi}. Eine Liste dieser Objekte wird dann zurückgegeben.
	 *
	 * @param pFilename
	 *          Pfadname der einzulesenden Textdatei.
	 * @return Eine Liste mit den eingelesenen Studierenden.
	 * @throws TheaPPParseException
	 *           Falls die Textdatei nicht dem geforderten Format entspricht oder beim
	 *           Lesen der Datei ein Fehler auftritt.
	 * @throws IllegalArgumentException
	 *           Falls der Parameterwert {@code null} oder eine leere Zeichenkette ist
	 *           oder nur aus Leerzeichen besteht.
	 *
	 */
	public List<Studi> parseStudents(final String pFilename) throws TheaPPParseException {
		if (pFilename == null || pFilename.trim().isEmpty()) {
			throw new IllegalArgumentException("Parameter filename is null or empty!");
		}
		final Path path = Paths.get(pFilename);
		final List<Studi> result = new ArrayList<>();
		int lineNo = 0;
		try {
			final List<String> lines = Files.readAllLines(path, Charset.forName("UTF-8"));
			for (final String line : lines) {
				lineNo++;
				final Studi studi = parseStudent(line);
				result.add(studi);
			}
			return result;
		} catch (final TheaPPParseException ex) {
			String message = String.format("Parse error in line %d in file %s: %s", lineNo,
				pFilename, ex.getMessage());
			if (ex.getCause() != null) {
				message += String.format(" (%s)", ex.getCause().getMessage());
			}
			message += ".";
			throw new TheaPPParseException(message, ex.getParseError(), ex);
		} catch (final IOException ex) {
			throw new TheaPPParseException(
				String.format("I/O-Error reading file %s (%s.", pFilename, ex.getMessage()),
				ParseError.IO_ERROR, ex);
		}
	}

}
