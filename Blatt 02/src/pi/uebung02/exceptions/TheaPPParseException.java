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
package pi.uebung02.exceptions;

/**
 * Zeigt an, dass ein Problem beim Parsen einer Zeichenkette aufgetreten ist. Der Grund
 * für das Problem wird als Konstante vom Typ {@link ParseError} gespeichert und kann
 * abgefragt werden.
 *
 * @author K. Hölscher
 * @version 2017-04-24
 */
public class TheaPPParseException extends Exception {

	/**
	 * Konstanten, die den konkreten Parserfehler, der diese Ausnahme ausgelöst hat,
	 * anzeigen.
	 *
	 * @author Karsten Hölscher
	 * @version 2017-04-24
	 *
	 */
	public enum ParseError {
		/**
		 * Zeigt eine falsche Anzahl von Einträgen an.
		 */
		WRONG_NO_OF_ENTRIES,
		/**
		 * Zeigt eine ungültige Bewertung an (keine Zahl oder eine Zahl außerhalb des
		 * erlaubten Bereichs).
		 */
		INVALID_RATING,
		/**
		 * Der Account ist ungültig.
		 */
		INVALID_ACCOUNT,
		/**
		 * Zeigt einen IO-Fehler beim Parsen an.
		 */
		IO_ERROR
	}

	/**
	 * Eineindeutige ID für Serialisierung.
	 */
	private static final long serialVersionUID = 4950204249547935257L;

	/**
	 * Der Parserfehler, der diese Ausnahme ausgelöst hat.
	 */
	private final ParseError parseError;

	/**
	 * Erzeugt eine neue Ausnahme mit der gegebenen Nachricht.
	 *
	 * @param pMessage
	 *          Die Nachricht zu der neuen Ausnahme.
	 *
	 * @param pError
	 *          Der Parserfehler, der die neue Ausnahme ausgelöst hat.
	 */
	public TheaPPParseException(final String pMessage, final ParseError pError) {
		super(pMessage);
		parseError = pError;
	}

	/**
	 * Erzeugt eine neue Ausnahme mit der gegebenen Nachricht und der gegebenen
	 * auslösenden Ausnahme.
	 *
	 * @param pMessage
	 *          Die Nachricht zu der neuen Ausnahme.
	 * @param pError
	 *          Der Parserfehler, der die neue Ausnahme ausgelöst hat.
	 * @param pCause
	 *          Die auslösende Ausnahme.
	 */
	public TheaPPParseException(final String pMessage, final ParseError pError,
		final Throwable pCause) {
		super(pMessage, pCause);
		parseError = pError;
	}

	/**
	 * Gibt den Parserfehler zurück, der diese Ausnahme ausgelöst hat.
	 *
	 * @return Den Parserfehler, der diese Ausnahme ausgelöst hat.
	 */
	public ParseError getParseError() {
		return parseError;
	}
}
