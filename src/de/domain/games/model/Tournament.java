package de.domain.games.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.domain.games.util.Directory;

/**
 * Die Klasse Tournament repräsentiert das Modell des Turniers in der Anwendung.
 * Dazu gehört die zentrale Methode process, die drei Aufgaben durchführt.
 * Erstens organisiert sie die Zusammenstellung aller Turnierpartien eines
 * Jahres in einer Kompilation. Zweitens erstellt sie für jedes Turnier eine
 * eigene Datenbank in dem Jahr, in dem das Turnier gespielt wurde. Drittens
 * stellt die Klasse auch alle Turnierpartien, die jemals erfasst wurden, in
 * einer großen Datenbank zusammen. Für alle Zugriffe (lesend oder schreibend)
 * wird die Modellklasse der Partie verwendet.
 * 
 * Die Hierarchie des Modells ist: Turnier ==> Jahr ==> Turniername ==> Game
 * 
 * Beispiel: Jahr = 2018 , Turnier = 04 Faschingsopen Baden-Baden, Game =
 * 2018-02-10 Schmitz-Erhardt.pgn
 * 
 * 
 * @author Tim Schmitz
 *
 */

public class Tournament {

	// Konstanten für die Klasse Opening
	private static final String PORTABLE_GAME_NOTATION = ".pgn";
	private static final String TOURNAMENT_ALL_IDENTIFIER = "Turniere";

	// Statischer Logger
	public static Logger LOG = LogManager.getLogger(Tournament.class.getName());

	// Felder
	public static String tournamentBasePath = null;
	public static String tournamentAllTargetPath = null;
	public static String tournamentPath = null;
	public static String tournamentTargetPath = null;
	public static String tournamentYearPath = null;
	public static String tournamentYearTargetPath = null;

	public static List<String> tournamentYearList = null;
	public static List<String> tournamentList = null;
	public static List<String> tournamentGames = null;
	public static List<String> tournamentGamesList = null;

	public static void process(String basePath) {
		tournamentBasePath = basePath;

		// Vorbereitung der Verarbeitung
		prepare();

		// Verarbeitung der Turniere
		processTournaments();
		processTournamentYears();
		processAll();
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
		tournamentYearList = new ArrayList<String>();
		tournamentYearList = Directory.getDirectories(tournamentBasePath);

		// Definition des Zielpfads für die Datenbank mit den Hauptvarianten und ihren
		// Schreiber initialisieren
		tournamentAllTargetPath = tournamentBasePath + File.separator + TOURNAMENT_ALL_IDENTIFIER
				+ PORTABLE_GAME_NOTATION;
		LOG.trace("Zielpfad f\u00fcr alle Turniere: " + tournamentAllTargetPath);
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
	public static void processTournamentYears() {

		LOG.trace("*******************************");
		LOG.trace("Verarbeitung der Turnierjahre  ");
		LOG.trace("*******************************");

		// Iteration über alle Turnierjahre
		for (String tournamentYear : tournamentYearList) {

			// Pfad für die Quell- und für die Zieldatenbank setzen
			tournamentYearPath = tournamentBasePath + File.separator + tournamentYear;
			tournamentYearTargetPath = tournamentBasePath + File.separator + tournamentYear + PORTABLE_GAME_NOTATION;
			LOG.trace("Erstelle Turnierjahr: " + tournamentYearTargetPath);

			// Schreiber mit dem Pfad und Container für die Jahres-Partien initialisieren
			Game.initializeWriter(tournamentYearTargetPath);
			tournamentList = Directory.getDirectories(tournamentYearPath);

			// Die Liste aller Partien und ihre Notation müssen außerhalb der Iteration
			// über alle Turniere dieses Jahres initialtisiert werden
			tournamentGamesList = new ArrayList<String>();
			tournamentGames = new ArrayList<String>();

			// Iteration über alle Turniere dieses Jahres
			for (String tournament : tournamentList) {

				// Ermittlung der Partien (nur echte Partien)
				tournamentPath = tournamentYearPath + File.separator + tournament;
				tournamentGamesList = Variation.getOrdinaryLines(tournamentPath);

				// Partien aufsammeln
				collectGames();
			}
			// Verarbeitung der Partien außerhalb der Iteration
			Game.writeGames(tournamentGames);
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
	public static void processTournaments() {

		LOG.trace("*******************************");
		LOG.trace("Verarbeitung der Einzelturniere");
		LOG.trace("*******************************");

		// Iteration über alle Turnierjahre
		for (String tournamentYear : tournamentYearList) {

			tournamentYearPath = tournamentBasePath + File.separator + tournamentYear;
			tournamentList = Directory.getDirectories(tournamentYearPath);

			// Iteration über alle Turniere dieses Jahres
			for (String tournament : tournamentList) {

				// Pfad für die Zieldatenbank setzen
				tournamentTargetPath = tournamentYearPath + File.separator + tournament + PORTABLE_GAME_NOTATION;
				LOG.trace("Erstelle Einzelturnier: " + tournamentTargetPath);

				// Schreiber mit dem Pfad und Container für die Partien initialisieren
				Game.initializeWriter(tournamentTargetPath);
				tournamentGames = new ArrayList<>();

				// Ermittlung der Partien (nur echte Partien)
				tournamentPath = tournamentYearPath + File.separator + tournament;
				tournamentGamesList = new ArrayList<String>();
				tournamentGamesList = Variation.getOrdinaryLines(tournamentPath);

				// Partien aufsammeln und verarbeiten
				collectGames();
				Game.writeGames(tournamentGames);
			}
		}
	}

	public static void processAll() {

		LOG.trace("*******************************");
		LOG.trace("Verarbeitung aller Turniere    ");
		LOG.trace("*******************************");
		
		// Zieldatenbank festlegen
		tournamentAllTargetPath = tournamentBasePath + File.separator + TOURNAMENT_ALL_IDENTIFIER + PORTABLE_GAME_NOTATION;
		LOG.trace("Erstelle Turnierjahr: " + tournamentAllTargetPath);
		
		// Schreiber mit dem Pfad und Container für die Partien initialisieren
		Game.initializeWriter(tournamentAllTargetPath);
		
		// Initialisierung 
		tournamentGames = new ArrayList<>();
		tournamentGamesList = new ArrayList<String>();
		
		// Iteration über alle Turnierjahre
		for (String tournamentYear : tournamentYearList) {

			tournamentYearPath = tournamentBasePath + File.separator + tournamentYear;
			tournamentList = Directory.getDirectories(tournamentYearPath);

			// Iteration über alle Turniere dieses Jahres
			for (String tournament : tournamentList) {

				// Pfad für die Zieldatenbank setzen
				tournamentTargetPath = tournamentYearPath + File.separator + tournament + PORTABLE_GAME_NOTATION;
				LOG.trace("Erstelle Einzelturnier: " + tournamentTargetPath);

				// Ermittlung der Partien (nur echte Partien)
				tournamentPath = tournamentYearPath + File.separator + tournament;
				tournamentGamesList = new ArrayList<String>();
				tournamentGamesList = Variation.getOrdinaryLines(tournamentPath);

				// Partien aufsammeln und verarbeiten
				collectGames();
			}
		}
		
		// Verarbeitung außerhalb aller Iterationen
		Game.writeGames(tournamentGames);		
		
	}
	

	/**
	 * Die Methode collectGames iteriert über eine Liste von Partien (mit ihrem
	 * vollqualifizierten Pfad) und initialisiert für jede Partie den Partieleser.
	 * Anschließend wird die Notation jeder Partie ausgelesen und der Liste der
	 * Turnier-Partien hinzugefügt.
	 */
	public static void collectGames() {

		// Partien aufsammeln
		for (String tournamentGame : tournamentGamesList) {
			LOG.trace("*************************************");
			LOG.trace("Verarbeite Partie f\u00fcr Jahr : " + tournamentGame);
			LOG.trace("*************************************");
			Game.initializeReader(tournamentGame);
			tournamentGames = Game.addGame(tournamentGames);
		}

	}

}
