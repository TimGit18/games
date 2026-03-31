package de.domain.games.base;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Die Klasse GameWriter stellt eine (schreibende) Zugriffsschicht auf die
 * Datenbank dar. Sie umfasst alle Methoden, mit der Partiesammlungen
 * geschrieben, gelöcht oder verändert werden.
 * 
 * @author Tim Schmitz
 *
 */

public class GameWriter {

	// Statischer Logger für die Anwendung
	public static Logger LOG = LogManager.getLogger(GameWriter.class.getName());

	// Statische Variablen dieser Klasse
	private static OutputStream outputStream = null;
	private static Writer writer = null;
	private static String outputPath = null;

	/**
	 * Die Methode initialize initialisiert den Anwendungskontext der Klasse
	 * GameWriter. Sie setzt den Ausgabepfad über einen von außen übergegbenen
	 * Parameter und definiert einen Ausgabestrom sowie einen dazugehörigen Writer.
	 * 
	 * @param targetPath
	 */

	public static void initialize(String targetPath) {

		// Defnition des Ausgabepfads
		outputPath = targetPath;
		LOG.trace("Ausgabepfad: " + outputPath);

		// Definition von Ausgabestrom und dazugehörigen Writer
		try {
			LOG.trace("Erzeugen von Ausgabestrom ");
			outputStream = new FileOutputStream(outputPath);
			writer = new OutputStreamWriter(outputStream);
		} catch (IOException ioException) {
			System.err.println(ioException.getMessage());
		}

	}

	/**
	 * Die Methode write schreibt einen String in den Ausgabestrom. Wie bei allen
	 * IO-Methoden muss auch hier eine potentielle Exception abgefangen werden.
	 * 
	 * @param expression
	 */
	public static void write(String expression) {
		try {
			writer.write(expression);
		} catch (IOException ioException) {
			System.err.println(ioException.getMessage());
		}
	}

	/**
	 * Die Methode delete löscht das über den Ausgabepfad initialisierte File.
	 */
	public static void delete() {
		File file = new File(outputPath);
		file.delete();
	}

	/**
	 * Die Methode close schließt den Partie-Schreiber, der dann wieder neu
	 * aufgerufen werden kann
	 */

	public static void close() {
		try {
			writer.close();
			LOG.trace("Schlie\u00dfe Partie-Schreiber");
		} catch (IOException ioException) {
			System.err.println(ioException.getMessage());
		}
	}

}
