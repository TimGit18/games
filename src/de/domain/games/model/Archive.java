package de.domain.games.model;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Die Klasse Archive repräsentiert das Modell eines Archivs für die
 * Games-Anwendung. Sie stellt die Attribute und Methoden zur Verfügung, mit
 * deren Hilfe verschiedenste Archvie (Weiß, Schwarz, Turnier) im ZIP-Format
 * erstellt und gespeichert werden können. Hierbei wird das Archiv mit dem
 * entsprechenden Partienkreis und einem aktuellen Datum in reziproker Form
 * gekennzeichnet.
 * 
 * @author Tim Schmitz
 *
 */

public class Archive {

	// Statischer Logger für die Anwendung
	public static Logger LOG = LogManager.getLogger(Archive.class.getName());

	// Statische Konstanten für die Archivierung
	private static final int BUFFER = 2048;
	private static final String WHITE = "Wei\u00df";
	private static final String BLACK = "Schwarz";
	private static final String TIM_WHITE = "TimWei\u00df";
	private static final String TIM_BLACK = "TimSchwarz";
	private static final String TOURNAMENT = "Turnier";

	// Statische Felder für die Anwendung
	private static String archiveDirectory = null;
	private static String whiteDirectory = null;
	private static String blackDirectory = null;
	private static String timWhiteDirectory = null;
	private static String timBlackDirectory = null;
	private static String tournamentDirectory = null;
	private static String currentDate = null;

	/**
	 * Die Methode initialize initialisiert die Felder, die für die Archivierung der
	 * PGN-Datenbanen erforderlich sind.
	 * 
	 * @param activeAccessPath
	 * @param whiteAccessPath
	 * @param blackAccesPath
	 * @param tournamentAcessPath
	 */
	public static void initialize(String activeAccessPath, String whiteAccessPath, String blackAccesPath,
		String timWhiteAccessPath, String timBlackAccessPath, String tournamentAcessPath) {
		archiveDirectory = activeAccessPath;
		whiteDirectory = whiteAccessPath;
		blackDirectory = blackAccesPath;
		timWhiteDirectory = timWhiteAccessPath;
		timBlackDirectory = timBlackAccessPath;
		tournamentDirectory = tournamentAcessPath;
		currentDate = LocalDate.now().toString();
	}

	/**
	 * Die Methode process archiviert die gesamte Partie-Sammlung im Archiv.
	 * 
	 * @param activeAccessPath
	 * @param whiteAccessPath
	 * @param blackAccesPath
	 * @param tournamentAcessPath
	 */
	public static void process(String activeAccessPath, String whiteAccessPath, String blackAccesPath,
			String timWhiteAccessPath, String timBlackAccessPath, String tournamentAcessPath) {

		// Vorbereitung der Archivierung
		initialize(activeAccessPath, whiteAccessPath, blackAccesPath, timWhiteAccessPath, timBlackAccessPath,
						tournamentAcessPath);
		String whiteArchivePath = setArchivePath(WHITE);
		String blackArchivePath = setArchivePath(BLACK);
 		String timWhiteArchivePath = setArchivePath(TIM_WHITE);
 		String timBlackArchivePath = setArchivePath(TIM_BLACK);
		String tournamentArchivePath = setArchivePath(TOURNAMENT);

		// Definition der Archive
		List<String> archivedWhiteGames = new ArrayList<String>();
		List<String> archivedBlackGames = new ArrayList<String>();
		List<String> archivedTimWhiteGames = new ArrayList<String>();
		List<String> archivedTimBlackGames = new ArrayList<String>();
		List<String> archivedTournamentGames = new ArrayList<String>();

		// Durchführung
		// 1. Aufsammlung aller zu archivierenden Partien
		archivedWhiteGames = addGames(whiteDirectory, archivedWhiteGames);
		archivedBlackGames = addGames(blackDirectory, archivedBlackGames);
		archivedTimWhiteGames = addGames(timWhiteDirectory, archivedTimWhiteGames);
		archivedTimBlackGames = addGames(timBlackDirectory, archivedTimBlackGames);
		archivedTournamentGames = addGames(tournamentDirectory, archivedTournamentGames);

		// 2. Archivierung der aufgesammelten Partien
		archive(whiteArchivePath, archivedWhiteGames);
		archive(blackArchivePath, archivedBlackGames);
		archive(timWhiteArchivePath, archivedTimWhiteGames);
		archive(timBlackArchivePath, archivedTimBlackGames);
		archive(tournamentArchivePath, archivedTournamentGames);

	}

	/**
	 * Die Methode setArchivePath setzt den vollqualifizierten Namen für das Archiv.
	 * 
	 * @param name
	 * @return
	 */
	public static String setArchivePath(String name) {
		String archivePath = archiveDirectory + File.separator + name + "_" + currentDate + ".zip";
		return archivePath;
	}

	/**
	 * Die Methode addGames sammelt rekurisve alle Partien eines Verzeichnisses auf
	 * und fügt sie der bisherigen Liste aller Partien hinzu.
	 * 
	 * @param directory
	 * @param list
	 * @return
	 */
	public static List<String> addGames(String directory, List<String> list) {
		// Liste der bereits gesammelten Partien
		List<String> gamesList = list;

		// Übergebenes Verzeichnis mitsamt der dort enthaltenenen Partien (= Dateien)
		File directoryFile = new File(directory);
		String[] games = directoryFile.list();

		// Es wird über die Liste der Partien iteriert.
		for (String gameCandidate : games) {

			// Für jede Partie wird der entsprechende Pfad ermittelt und das Dateiformat
			// gesetzt.
			String gamePath = directory + File.separator + gameCandidate;
			File gameFile = new File(gamePath);

			// Falls die Datei wiederum ein Verzeichnis ist, dann wird die Rekursion mit dem
			// aktuellen Pfad und der aktuellen Liste fortgesetzt.
			if (gameFile.isDirectory())
				addGames(gamePath, gamesList);

			// Falls die Datei eine echte Datei ist, dann wird sie der Liste aller
			// archivierten Partien hinzugefügt.
			if (gameFile.isFile())
				gamesList.add(gamePath);
		}
		return gamesList;
	}

	/**
	 * Die Methode archive schreibt ein Archiv aus einer Liste von kompilierten
	 * Dateien.
	 * 
	 * @param archivePath
	 * @param archivedGames
	 */
	public static void archive(String archivePath, List<String> archivedGames) {

		try {

			// Definition der Eingabe- und Ausgabeströme
			BufferedInputStream bufferedInputStream = null;
			FileOutputStream fileOutputStream = new FileOutputStream(archivePath);
			ZipOutputStream zipOutputStream = new ZipOutputStream(new BufferedOutputStream(fileOutputStream));

			// Byte-String für die Archivierung
			byte data[] = new byte[BUFFER];

			// Iteration über alle gesammelten Partien einer Archiv-Liste
			for (String archivedGame : archivedGames) {
				LOG.trace("Archiviere: " + archivedGame);

				// Für jede Partie wird ein eigener Eingabestrom definiert
				FileInputStream fileInputStream = new FileInputStream(archivedGame);
				bufferedInputStream = new BufferedInputStream(fileInputStream, BUFFER);

				// Für jede Partie wird ein eigener ZIP-Eintrag generiert, der dem Ausgabestrom
				// hinzugefügt wird.
				ZipEntry entry = new ZipEntry(archivedGame);
				zipOutputStream.putNextEntry(entry);
				int count;

				// Auslesen des gepufferten Eingabestroms und Schreiben des ZIP-Ausgabestroms
				while ((count = bufferedInputStream.read(data, 0, BUFFER)) != -1) {
					zipOutputStream.write(data, 0, count);
				}
				bufferedInputStream.close();
			}
			zipOutputStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
