package de.domain.games.base;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.commons.io.input.BOMInputStream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Die Klasse GameReader liest zu einem übergebenem Partiepfad eine Partie aus
 * der Datenbasis und gibt den Partietext als String zurück. Dazu wird zunächst
 * ein gepufferter Leser initialisiert.
 * 
 * @author Tim Schmitz
 *
 */

public class GameReader {

	// Statischer Logger für die Anwendung
	private static Logger LOG = LogManager.getLogger(GameReader.class.getName());

	// Statische Variablen für diese Anwendung
	private static String gameText = null;
	private static BufferedReader bufferedReader = null;
	private static InputStreamReader bomInputStreamReader;
	private static final String LINE_FEED = "\r\n";

	/**
	 * Die Methode initialize initialialisiert den gepuffertern Leser, indem sie ihn
	 * erzeugt.
	 * 
	 * @param gamePath
	 */
	public static void initialize(String gamePath) {
		LOG.trace("Initialisierung des gepufferten Lesers");
		LOG.trace("GamePath: " + gamePath);
		bufferedReader = createBuffer(gamePath);
	}

	/**
	 * Die Methode createBuffer erzeugt einen gepufferten Leser, indem ein Dateipfad
	 * übergeben wird und mit diesem ein Eingabestrom definiert wird. Anschließend
	 * wird dieser Eingabestrom verwendet, indem ein Eingabestrom-Leser erzeugt wird
	 * und aus diesem schließlich der gepufferte Leser.
	 * 
	 * @param filePath
	 * @return input
	 */
	public static BufferedReader createBuffer(String filePath) {
		LOG.trace("Erzeuge gepufferten Leser zu " + filePath);
		BufferedReader input = null;

		// Erzeugen des gepufferten Lesers und seine Rückgabe
		try {
			FileInputStream inputStream = new FileInputStream(filePath);
			bomInputStreamReader = new InputStreamReader(new BOMInputStream(inputStream));
			input = new BufferedReader(bomInputStreamReader);
		} catch (IOException ioException) {
			System.err.println(ioException.getMessage());
		}
		return input;
	}

	/**
	 * Die Methode read liest eine Partie aus dem gepufferten Leser und erzeugt als
	 * Resultat einen Ausgabestring, der die Partie beinhaltet. Falls keine Zeile
	 * mehr gelesen werden kann, weil das Dateiende erreicht ist, entsteht
	 * normalerweise eine NullPointer-Exception, die in diesem Fall mit der Methode
	 * closeBuffer beantwortet wird.
	 * 
	 * @return
	 */
	public static String read() {

		// Es treten Exceptions auf, die abgefangen werden müssen
		try {

			// Definition der String Variablen (aktuelle Zeile und Partietext)
			String inputLine;
			gameText = new String();

			// Die folgende Schleife iteriert über alle Zeilen des gepufferten Lesers und
			// verlängert den Partietext eben um diese.
			while (!(inputLine = bufferedReader.readLine()).equals(null)) {
				// LOG.trace("Lese Partiezeile: " + inputLine);
				//
				gameText = gameText + inputLine + LINE_FEED;
			}

		// Die IO-Exception muss immer abgefangen werden
		} catch (IOException ioException) {
			System.err.println(ioException.getMessage());
			
		// Die NullPointer-Exception beendet die Verarbeitung	
		} catch (NullPointerException nullPointerException) {
			closeBuffer();
		}
		return gameText;
	}

	/**
	 * Die Methode closeBuffer schließt den Eingabepuffer
	 */
	public static void closeBuffer() {
		LOG.trace("Schlie\u00dfe puffer...");
		try {
			bufferedReader.close();
		} catch (IOException ioException) {
			System.err.println(ioException.getMessage());
		}
	}

	/**
	 * Die Methode extractEco extrahiert den Eco-Code der Partie
	 */

}
