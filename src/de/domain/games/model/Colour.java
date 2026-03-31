package de.domain.games.model;

import de.domain.games.util.Directory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Die Klasse Colour repräsentiert das Modell der Farbe. Es sammelt zu jeder Farbe
 * die Haupteröffnungen auf. Beispiel:
 * Farbe:         Weiß
 * Eröffnung:     01 Sizilianische Verteidigung
 * Hauptvariante: 00 Sizilianische Verteidigung.pgn
 */


public class Colour {
  private static String colourPath;
  private static String colourTargetPath;
  private static String colour;
  private static List<String> openingList;
  private static List<String> openingLineList;
  private static List<String> openingGames;

  // Konstanten für die Klasse Colour
  private static final String PORTABLE_GAME_NOTATION = ".pgn";
  public static Logger LOG = LogManager.getLogger(Colour.class.getName());

  public Colour(String activeAccessPath, String activeSelection) {
    colourPath = activeAccessPath;
    colour = activeSelection;
  }

  public static void prepare() {
    openingList = Directory.getDirectories(colourPath);
    openingList.remove(".git");
    openingLineList = new ArrayList<>();
    LOG.trace("Auswahl: " + colour);

    String openingBasePath = colourPath.replaceAll(colour, "");
    colourTargetPath = openingBasePath + colour + PORTABLE_GAME_NOTATION;
    LOG.trace("Farbe-Zielpfad: " + colourTargetPath);
    Game.initializeWriter(colourTargetPath);

    openingGames = new ArrayList<>();
  }


  public static void process() {
    prepare();
    retrieveOpeningLines();
    processOpeningLines();
  }

  public static void retrieveOpeningLines() {
    for (String opening : openingList) {
      LOG.trace("Eröffnung: " + opening);
      String openingPath = colourPath + File.separator + opening;
      LOG.trace("Eröffnungs-Pfad:   " + openingPath);

      String openingLinePath = Variation.getMainLine(openingPath);
      openingLineList.add(openingLinePath);

    }
  }

  public static void processOpeningLines() {
    for (String line : openingLineList) {
      LOG.trace("*********************************");
      LOG.trace("Verarbeite Eröffnung: " + line);
      LOG.trace("*********************************");
      Game.initializeReader(line);
      openingGames = Game.addGame(openingGames);
    }
    Game.writeGames(openingGames);

  }



}
