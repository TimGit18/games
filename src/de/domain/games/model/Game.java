package de.domain.games.model;

import java.io.File;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.domain.games.base.GameReader;
import de.domain.games.base.GameWriter;

/**
 * Die Klasse Game repräsentiert das Modell einer Partie in dieser Anwendung.
 * Sie stellt verschiedene Methoden zur Verfügung, um Informationen zu einer
 * Partie zu erhalten und stellt auch die Verbindung zur Datenbasis her.
 *
 * @author Tim Schmitz
 */

public class Game {

  // Statischer Logger für diese Klasse
  public static Logger LOG = LogManager.getLogger(Game.class.getName());

  // Konstanten für diese Klasse
  public static final String CHESSBASE_PORTABLE_GAME_NOTATION = "^(.*)\\.cbpgn$";
  public static final String CHESSBASE_OPENING_KEY = "^(.*)\\.cko$";
  public static final String CHESSBASE_OPENING_PROGRAM = "^(.*)\\.cpo$";
  public static final String INITIALIZATION = "^(.*)\\.ini$";
  public static final String PORTABLE_GAME_INFORMATION = "^(.*)\\.pgi$";
  public static final String PORTABLE_GAME_NOTATION = "^(.*)\\.pgn$";
  public static final String OVERVIEW_PORTABLE_GAME_NOTATION = "^00(.*)\\.pgn$";
  public static final String ZIPPER = "^(.*)\\.zip$";
  public static final String EMPTY = "";

  // Statische Felder dieser Klasse
  private static String gamePath = null;
  private static String targetPath = null;
  // private static String generatedFilePath;
  private static String ecoCode = null;

  /**
   * Die Methode initializeReader initialisiert eine Partie mit ihrem
   * vollqualifizierten Dateipfad und dem dazugehörigen Partielesers.
   *
   * @param path
   */
  public static void initializeReader(String path) {
    LOG.trace("Initialisiere Partie-Leser zu " + path);
    ecoCode = EMPTY;
    gamePath = path;
    if (!gamePath.isEmpty()) {
      GameReader.initialize(gamePath); }
    else
      LOG.error("Keine Partie definiert");
  }

  /**
   * Die Methode initializeWriter initialisiert eine Partie mit ihrem
   * vollqualifizierten Dateipfad und dem dazugehörigen Partie-Schreibers. Damit
   * dieser aktiviert werden kann, muss die besehende Partie gelöscht werden.
   *
   * @param path
   */
  public static void initializeWriter(String path) {
    LOG.trace("Initialisiere Partie-Schreiber zu " + path);
    targetPath = path;
    GameWriter.initialize(targetPath);
    GameWriter.delete();
  }

  /**
   * Die Methode addGame fügt eine Partie der Liste der Partien hinzu.
   *
   * @param games
   * @return games
   */
  public static List<String> addGame(List<String> games) {
    String game = GameReader.read();
    games.add(game);
    return games;
  }

  /**
   * Die Methode writeGames schreibt alle Partien in die Zieldatenbank, die mit
   * dem Zielpfad verbunden ist. Dafür iteriert sie über die Liste aller Partien
   * und schreibt Zeile für Zeile in die Zieldatenbank. Zum Abschluß wird der
   * jeweilige Writer geschlossen.
   *
   * @param games
   */
  public static void writeGames(List<String> games) {

    // Check aller Hauptvarianten
    for (String line : games) {
      GameWriter.write(line);
    }

    // Abschluß
    GameWriter.close();
  }

  /**
   * Die Methode deleteFiles löscht zu einem übergebenem Zugriffspfad alle
   * Dateien, die einem bestimmten Dateimuster entsprechen. Dies sind Dateien, die
   * entweder PGN-Dateien im ChessBase-Format sind (cbpgn), die ein ChessBase
   * Opening Key verwalten (cko), die ein ChessBase Opening Program sind (cpo),
   * die PGN-Informationsdateien sind (pgi) oder Initialisierungsdateien (ini).
   *
   * @param accessPath
   */
  public static void deleteFiles(String accessPath) {

    // Dateien des Zugriffspfades initialisieren
    File directory = new File(accessPath);
    String[] files = directory.list();

    // Iteration über alle Dateien des aktuellen Zugriffspfades
    for (String file : files) {

      // Vollqualifizierter Dateinamen
      String activeDirectory = accessPath + File.separator + file;

      // Nur die Dateien löschen, auf die das oben beschreibene Kriterium zutrifft
      if (file.matches(CHESSBASE_PORTABLE_GAME_NOTATION) || file.matches(CHESSBASE_OPENING_KEY)
              || file.matches(CHESSBASE_OPENING_PROGRAM) || file.matches(PORTABLE_GAME_INFORMATION)
              || file.matches(INITIALIZATION) || file.matches(ZIPPER)) {
        File deletedFile = new File(activeDirectory);
        LOG.trace("Zugriffspfad:    " + accessPath);
        LOG.trace("1.Löschkandidat: " + activeDirectory);
        LOG.trace("Gelöschte Datei: " + deletedFile.getName());
        deletedFile.delete();
      }

      // Prüfe, ob wir schon am Boden der Verzeichnis-Hierarchie angelangt sind.
      // Falls nicht, dann ist eine Datei des aktuellen Verzeichnisses wieder selber
      // ein Verzeichnis. In diesem Fall können alle generierten PGN-Dateien des
      // aktuellen Verzeichnisses gelöscht werden. Außerdem verzweigt in diesem die
      // Routine rekursiv in das Unterverzeichnis.
      File activeFile = new File(activeDirectory);

      // Aktuelle Datei des Verzeichnisses ist wieder ein Verzeichnis
      if (activeFile.isDirectory()) {
        for (String generatedFile : files) {

          // Lösche generierte PGN-Dateien
          if (generatedFile.matches(PORTABLE_GAME_NOTATION) && !generatedFile.matches(OVERVIEW_PORTABLE_GAME_NOTATION)) {
            String generatedFilePath = accessPath + File.separator + generatedFile;
            File generatedFileToDelete = new File(generatedFilePath);
            LOG.trace("2.Löschkandidat: " + generatedFileToDelete.getName());
            generatedFileToDelete.delete();
          }

        }
        // Rekursiver Aufruf der Methode
        deleteFiles(activeDirectory);
      }
    }
  }

  /**
   * Die Funktion setEcoCode() setzt den Eröffnungsschlüssel für die Partie.
   * @param code
   */
  public static void setEcoCode(String code) {
    ecoCode = code;
  }

  public static String getEcoCode() {
    return ecoCode;
  }

}
