package pi.uebung03;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse definiert eine Methode, die die Anzahl der notwendigen Vergleiche für den
 * Aufbau einer Menge aus einer Textdatei testet.
 *
 * @author Thomas Röfer, Karsten Hölscher
 * @version 2017-05-11
 */
public final class StrategyCheck {

	/**
	 * Der Dateiname für die einzulesende Datei.
	 */
	private static final String FILENAME = "ingenieur.txt";

	/**
	 * Private Konstruktor, um eine sinnlose Instanziierung dieser zustandslosen
	 * Utility-Klasse zu verhindern.
	 */
	private StrategyCheck() {
		System.err.println("Someone managed to call the ultra-secret constructor. Weird.");
		System.exit(-1); // Das lassen wir uns nicht bieten!
	}

	/**
	 * Erzeugt pro Selbstanordnungsstrategie je eine Menge von Wörtern aus der Datei mit
	 * dem gegebenen Dateinamen und gibt jeweils auf der Konsole aus, wieviele Vergleiche
	 * mit {@code equals} beim Erzeugen der Menge nötig waren.
	 *
	 * @param pFilename
	 *          Der Dateiname für die Datei.
	 * @throws IOException
	 *           Falls beim Einlesen der Datei ein Fehler aufgetreten ist.
	 * @throws java.io.FileNotFoundException
	 *           Wenn die Datei nicht gefunden wird.
	 *
	 */
	public static void check(final String pFilename) throws IOException {
		CountingString.resetCounter();
		makeSet(pFilename, new PI2SetNaive<CountingString>());
		System.out.println("Naiv:     " + CountingString.getCounter());
		CountingString.resetCounter();

		// TODO: analog für die Mengen der anderen Strategien

	}

	/**
	 * Die Methode liest eine Datei ein, zerlegt diese in Worte und fügt diese Worte in
	 * die gegebene, leere Menge ein.
	 *
	 * @param pFilename
	 *          Der Name der einzulesenden Datei.
	 * @param pSet
	 *          Die leere Menge, die mit den Wörtern befüllt werden soll.
	 * @throws IOException
	 *           Falls beim Einlesen der Datei ein Fehler aufgetreten ist.
	 * @throws java.io.FileNotFoundException
	 *           Wenn die Datei nicht gefunden wird.
	 */
	private static void makeSet(final String pFilename, final PI2Set<CountingString> pSet)
		throws IOException {

		// TODO: Hier ergänzen

	}

	/**
	 * Liest die Datei mit dem gegebenen Dateinamen ein, extrahiert alle Wörter aus dieser
	 * Datei und gibt sie in einer Liste zurück. Wenn der gegebene Dateiname den Wert
	 * {@code null} hat oder leer ist oder nur aus Leerzeichen besteht, wird eine leere
	 * Liste zurückgegeben.
	 *
	 * @param pFilename
	 *          Der Dateiname der einzulesenden Datei.
	 * @return Eine Liste mit Wörtern aus der Datei mit dem gegebenen Dateinamen.
	 *
	 * @throws IOException
	 *           Falls beim Einlesen der Datei ein Fehler aufgetreten ist.
	 * @throws java.io.FileNotFoundException
	 *           Wenn die Datei nicht gefunden wird.
	 */
	private static List<String> getWordsFromFile(final String pFilename)
		throws IOException {
		final List<String> words = new ArrayList<>();

		if (pFilename == null || pFilename.trim().isEmpty()) {
			return words;
		}

		final Path path = Paths.get(pFilename);
		List<String> lines = Files.readAllLines(path, Charset.forName("UTF-8"));

		final String text = String.join(" ", lines);

		final StringBuilder builder = new StringBuilder("");

		for (final char c : text.toCharArray()) {
			if (Character.isLetter(c)) {
				builder.append(Character.toLowerCase(c));
			} else if (builder.length() > 0) {
				words.add(builder.toString());
				builder.delete(0, builder.length());
			}
		}

		// Letzes Wort einfügen
		if (builder.length() > 0) {
			words.add(builder.toString());
		}
		return words;

	}

	/**
	 * Ruft die Methode {@link StrategyCheck#check(String)} mit dem Dateinamen in
	 * {@link StrategyCheck#FILENAME} auf.
	 *
	 * @param args
	 *          Werden ignoriert.
	 */
	public static void main(final String[] args) {
		try {
			check(FILENAME);
		} catch (final IOException ex) {
			System.out.println("An exception occured while reading file " + FILENAME);
			ex.printStackTrace();
		}
	}

}
