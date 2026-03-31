package de.domain.games.model;

import de.domain.games.util.Directory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse Book repräsentiert das Modell des Buchs in der Anwendung.
 * Dazu gehört die zentrale Methode process, die zwei Aufgaben durchführt.
 * Sie erstellt sie für jedes Kapitel eine eigene Datenbank in dem Buch, in dem
 * erstellt wurde und eine Datenbank für jedes Buch. Für alle Zugriffe
 * (lesend oder schreibend) wird die Modellklasse der Partie verwendet.
 * 
 * Die Hierarchie des Modells ist: Buch ==> Buchtitel ==> Kapitel ==> Game
 * 
 * Beispiel: Buch = Better Chess For Average Players,
 * 					 Kapitel = 01 Grundlagen Material,
 * 					 Game = 02 Schlechter Abtausch.pgn
 * @author Tim Schmitz
 */

public class Book {

	// Konstanten für die Klasse Opening
	private static final String PORTABLE_GAME_NOTATION = ".pgn";
	// private static final String TOURNAMENT_ALL_IDENTIFIER = "Turniere";

	// Statischer Logger
	public static Logger LOG = LogManager.getLogger(Book.class.getName());

	// Felder
	public static String bookBasePath = null;
	public static String bookPath = null;
	public static String bookTargetPath = null;
	public static String chapterPath = null;
	public static String chapterTargetPath = null;

	public static List<String> bookTitleList = null;
	public static List<String> bookList = null;
	public static List<String> bookGames = null;
	public static List<String> bookGamesList = null;

	public static void process(String basePath) {
		bookBasePath = basePath;

		// Vorbereitung der Verarbeitung
		prepare();

		// Verarbeitung der Turniere
		processChapters();
		processBooks();
	}

	/**
	 * Die Methode prepare bereitet die Verarbeitung aller Turnierpartien in den
	 * drei Modi vor (Einzelturnier, Jahresturnier, Gesamt). Zuerst wird ausgehend
	 * vom Basispfad die Liste der Turnierjahre erzeugt. Anschließend wird der
	 * Zielpfad für die Gesamtturnier-Datenbank definiert.
	 */

	public static void prepare() {

		LOG.trace("*****************************");
		LOG.trace("Vorbereitung der Verarbeitung");
		LOG.trace("*****************************");

		// Ermittlung der Systeme
		bookTitleList = new ArrayList<>();
		bookTitleList = Directory.getDirectories(bookBasePath);

	}

	/**
	 * Die Methode processTournamentYears erstellt zu jedem Turnierjahr die
	 * entsprechende Zieldatenbank im übergeordneten Turnierordner. Zunächst
	 * iteriert die Methode über die Liste der Turnierjahre und ermittelt zu jedem
	 * Jahr die Liste der Turniere. Zu jedem Turnierjahr wird der Pfad für die
	 * Zieldatenbank gesetzt, der Schreiber initialisiert und alle Partien des
	 * jeweiligen Turnierjahres ermittelt. Diese werdeen aus der Datenbasis
	 * herausgelesen und in eine Zielliste geschrieben. Daraus wird die
	 * Zieldatenbank geschrieben.
	 */
	public static void processBooks() {

		LOG.trace("*******************************");
		LOG.trace("Verarbeitung der Turnierjahre  ");
		LOG.trace("*******************************");

		// Iteration über alle Turnierjahre
		for (String tournamentYear : bookTitleList) {

			// Pfad für die Quell- und für die Zieldatenbank setzen
			chapterPath = bookBasePath + File.separator + tournamentYear;
			chapterTargetPath = bookBasePath + File.separator + tournamentYear + PORTABLE_GAME_NOTATION;
			LOG.trace("Erstelle Turnierjahr: " + chapterTargetPath);

			// Schreiber mit dem Pfad und Container für die Jahres-Partien initialisieren
			Game.initializeWriter(chapterTargetPath);
			bookList = Directory.getDirectories(chapterPath);

			// Die Liste aller Partien und ihre Notation müssen außerhalb der Iteration
			// über alle Turniere dieses Jahres initialtisiert werden
			bookGamesList = new ArrayList<>();
			bookGames = new ArrayList<>();

			// Iteration über alle Turniere dieses Jahres
			for (String tournament : bookList) {

				// Ermittlung der Partien (nur echte Partien)
				bookPath = chapterPath + File.separator + tournament;
				bookGamesList = Variation.getOrdinaryLines(bookPath);

				// Partien aufsammeln
				collectGames();
			}
			// Verarbeitung der Partien außerhalb der Iteration
			Game.writeGames(bookGames);
		}
	}

	/**
	 * Die Methode processTournaments erstellt zu jedem Einzelturnier die
	 * entsprechende Zieldatenbank im übergeordneten Jahresordner. Zunächst iteriert
	 * die Methode über die Liste der Turnierjahre und ermittelt zu jedem Jahr die
	 * Liste der Turniere. Anschließend wird über alle Turniere iteriert und der
	 * Pfad für die Zieldatenbank gesetzt, der Schreiber initialisiert und alle
	 * Partien des jeweiligen Turniers ermittelt. Diese werdeen aus der Datenbasis
	 * herausgelesen und in eine Zielliste geschrieben. Daraus wird die
	 * Zieldatenbank geschrieben.
	 */
	public static void processChapters() {

		LOG.trace("*******************************");
		LOG.trace("Verarbeitung der Einzelturniere");
		LOG.trace("*******************************");

		// Iteration über alle Turnierjahre
		for (String bookTitle : bookTitleList) {

			chapterPath = bookBasePath + File.separator + bookTitle;
			bookList = Directory.getDirectories(chapterPath);

			// Iteration über alle Turniere dieses Jahres
			for (String tournament : bookList) {

				// Pfad für die Zieldatenbank setzen
				bookTargetPath = chapterPath + File.separator + tournament + PORTABLE_GAME_NOTATION;
				LOG.trace("Erstelle Einzelturnier: " + bookTargetPath);

				// Schreiber mit dem Pfad und Container für die Partien initialisieren
				Game.initializeWriter(bookTargetPath);
				bookGames = new ArrayList<>();

				// Ermittlung der Partien (nur echte Partien)
				bookPath = chapterPath + File.separator + tournament;
				bookGamesList = new ArrayList<>();
				bookGamesList = Variation.getOrdinaryLines(bookPath);

				// Partien aufsammeln und verarbeiten
				collectGames();
				Game.writeGames(bookGames);
			}
		}
	}

	/**
	 * Die Methode collectGames iteriert über eine Liste von Partien (mit ihrem
	 * vollqualifizierten Pfad) und initialisiert für jede Partie den Partieleser.
	 * Anschließend wird die Notation jeder Partie ausgelesen und der Liste der
	 * Turnier-Partien hinzugefügt.
	 */
	public static void collectGames() {

		// Partien aufsammeln
		for (String tournamentGame : bookGamesList) {
			LOG.trace("*************************************");
			LOG.trace("Verarbeite Partie f\u00fcr Jahr : " + tournamentGame);
			LOG.trace("*************************************");
			Game.initializeReader(tournamentGame);
			bookGames = Game.addGame(bookGames);
		}

	}

}
