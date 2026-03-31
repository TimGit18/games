package de.domain.games.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import de.domain.games.util.Directory;

/**
 * Die Klasse Opening repräsentiert das Modell der Eröffnung. Dazu gehört die
 * zentrale Methode process, die zwei Aufgaben durchführt. Erstens organisiert
 * sie die Zusammenstellung aller Hauptvarianten einer Eröffnung, die in einer
 * zentralen PGN-Datenbank gesammelt werden. Zweitens erstellt sie für jedes
 * System der Eröffnung eine lokale systemspezifische PGN-Datenbank. Dafür
 * verwendet sie die Modellklasse der Partie.
 * 
 * Die Hierarchie des Modells ist: Color ==> Opening ==> Variation ==> Game
 * 
 * Beispiel: Color = Weiß, Opening = 01 Sizilianische Verteidigung, Variation =
 * 0101 Najdorf-System, Game = 01 Moderne Hauptvariante.pgn
 * 
 * @author Tim Schmitz
 *
 */

public class Opening {

	// Konstanten für die Klasse Opening
	private static final String PORTABLE_GAME_NOTATION = ".pgn";

	// Statischer Logger für diese Klasse
	public static Logger LOG = LogManager.getLogger(Opening.class.getName());

	// Statische Variablen für die Klasse Opening
	private static String colorPath;
	private static String openingPath;
	private static String openingName;
	private static String mainLineTargetPath;
	private static String variationLineTargetPath;
	private static List<String> mainLineList = null;
	private static List<String> mainGames = null;
	private static List<String> variationList = null;
	private static List<String> variationLineList = null;
	private static List<String> variationGames = null;

	/**
	 * Der Konstruktor für die Klasse Opening wird durch den Pfad der Eröffnung, den
	 * Namen der Eröffnung und den Pfad des übergeordneten Verzeichnisses, der
	 * Farbe, initialisiert.
	 * 
	 * @param path
	 */
	public Opening(String path, String name, String color) {
		openingPath = path;
		openingName = name;
		colorPath = color;
	}

	/**
	 * Die Methode process verarbeitet eine die aktuelle Eröffnung und leitet dazu
	 * in die Systeme dieser Eröffnung über.
	 */
	public void process() {

		// Vorbereitung
		prepare();

		// Verarbeitung der Hauptvarianten
		retrieveMainLines();
		processMainLines();

		// Verarbeitung der Systemvarianten
		processVariationLines();
	}

	/**
	 * Die Methode prepare hat zwei Aufgaben: Sie ermittelt alle Systeme, die zu der
	 * aktuellen Eröffnung gehören, und sie definiert den Zielpfad für die Datenbank
	 * der Hauptvarianten.
	 */
	public static void prepare() {

		LOG.trace("*****************************");
		LOG.trace("Vorbereitung der Verarbeitung");
		LOG.trace("*****************************");

		// Ermittlung der Systeme
		variationList = new ArrayList<>();
		variationList = Directory.getDirectories(openingPath);

		// Definition des Zielpfads für die Datenbank mit den Hauptvarianten und ihren
		// Schreiber initialisieren
		mainLineList = new ArrayList<>();
		mainLineTargetPath = colorPath + File.separator + openingName + PORTABLE_GAME_NOTATION;
		LOG.trace("Hauptvarianten-Zielpfad: " + mainLineTargetPath);
		Game.initializeWriter(mainLineTargetPath);

		// Die Datei mit den Hauptvarianten initialisieren
		mainGames = new ArrayList<>();

	}

	/**
	 * Die Methode processMainLines stellt die Hauptvarianten-Datenbank für die
	 * aktuelle Eröffnung bereit. Dazu iteriert sie über jedes zugehörige System der
	 * Eröffnung und ermittelt aus diesem die Hauptvariante. Anschließend wird diese
	 * Hauptvariante der bereits bestehenden Liste von Hauptvarianten hinzugefügt.
	 */
	public static void retrieveMainLines() {

		// Iteration über alle Systeme dieser Eröffnung
		for (String variationItem : variationList) {

			// System-Pfad setzen
			LOG.trace("***********************************************");
			LOG.trace("Hole Hauptvariante f\u00fcr System: " + variationItem);
			LOG.trace("***********************************************");
			String variationPath = openingPath + File.separator + variationItem;
			LOG.trace("System-Pfad:   " + variationPath);

			// Hauptvariante ermitteln
			String mainLinePath = Variation.getMainLine(variationPath);
			LOG.trace("Hauptvariante: " + mainLinePath);

			// Hauptvariante hinzufügen
			mainLineList.add(mainLinePath);

		}
	}

	/**
	 * Die Methode processMainLines verarbeitet die Hauptvarianten der Systeme einer
	 * Eröffnung. Zunächst wird die Zugriffsschicht mit dem Zielpfad initialisiert.
	 * Anschließend wird die vorhandene Ziel-Datenbasis entfernt. Danach werden alle
	 * Hauptvarianten über die Zugriffsschicht aus der restlichen Datenbasis
	 * ermittelt und in eine Listenstruktur geschrieben. Anschließend wird wiederum
	 * die Zugriffsschicht aufgerufen und die neue Ziel-Datenbasis aufgebaut.
	 */

	public static void processMainLines() {

		// Aufsammeln aller Hauptvarianten
		for (String line : mainLineList) {
			LOG.trace("*********************************");
			LOG.trace("Verarbeite Hauptvariante: " + line);
			LOG.trace("*********************************");
			Game.initializeReader(line);
			mainGames = Game.addGame(mainGames);
		}

		// Verarbeitung der Hauptvarianten
		Game.writeGames(mainGames);

	}

	/**
	 * Die Methode processVariationLines verarbeitet alle Varianten eines Systems
	 * einer Eröffnung - außer der Hauptvariante. Diese Varianten werden in einer
	 * systemspezifischen Datenbank zusammengefasst.
	 */
	public static void processVariationLines() {

		// Iteration über alle Systeme der Eröffnung
		for (String variationItem : variationList) {

			// System-Pfad setzen
			LOG.trace("*************************************************");
			LOG.trace("Hole alle Varianten f\u00fcr System: " + variationItem);
			LOG.trace("*************************************************");
			String variationPath = openingPath + File.separator + variationItem;
			LOG.trace("System-Pfad:   " + variationPath);

			// Target-Pfad setzen und System-Notationen initialisieren
			variationLineTargetPath = variationPath + PORTABLE_GAME_NOTATION;
			Game.initializeWriter(variationLineTargetPath);
			variationGames = new ArrayList<>();
		
			// Ermittlung der Systemvarianten (nur echte Varianten)
			variationLineList = new ArrayList<String>();
			variationLineList = Variation.getOrdinaryLines(variationPath);
			
			// Check der Systemvarianten
			for (String variationLine : variationLineList) {
				LOG.trace("**************************************");
				LOG.trace("Verarbeite Variante: " +  variationLine);
				LOG.trace("**************************************");
				Game.initializeReader(variationLine);
				variationGames = Game.addGame(variationGames);
			}

			// Verarbeitung der Systemvarianten
			Game.writeGames(variationGames);
		}
	}
}
