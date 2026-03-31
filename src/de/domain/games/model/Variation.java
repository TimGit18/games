package de.domain.games.model;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Die Klasse Variation repräsentiert das Modell eines Varianten-Systems in der
 * Anwendung. Sie stellt verschiedene Methoden zur Ermittlung und Verarbeitung
 * von Partien der Variante zur Verfügung
 * Beispiel
 * Farbe:         		Weiß
 * Eröffnung:     		01 Sizilianische Verteidigung
 * Varianten-System: 	01 Najdorf-System
 * Hauptvariante:		00 Najdorf-System.pgn
 * @author Tim Schmitz
 *
 */

public class Variation {

	// Konstanten für diese Klasse
	public static final String PORTABLE_GAME_NOTATION = "^(.*)\\.pgn$";
	public static final String MAIN_LINE = "^00(.*)$";

	// Statischer Logger für diese Klasse
	public static Logger LOG = LogManager.getLogger(Variation.class.getName());

	/**
	 * Die Methode getMainLine ermittelt zu einem übergebenem System-Pfad die
	 * aktuelle Hauptvariante. Die Hauptvariante ist eigentlich die Übersicht und beginnt immer mit der 00
	 * vor dem Namen der Eröffnung oder des Eröffnungssystems.
	 * 
	 * @param variationPath
	 * @return mainLinePath
	 */

	public static String getMainLine(String variationPath) {

		// Deklaration des Rückgabewerts
		LOG.trace("Ermitteln der Hauptvariante zu " + variationPath);
		String mainLinePath = new String();

		// System, das die Hauptvariante beinhaltet
		File variation = new File(variationPath);
		String[] lines = variation.list();

		// Iteration über alle Varianten dieses Systems
		for (String line : lines) {

			// Nur echte (.pgn) Hauptvarianten (00) zurückgeben
			if (line.matches(PORTABLE_GAME_NOTATION) && line.matches(MAIN_LINE)) {
				mainLinePath = variationPath + File.separator + line;
			}
		}
		return mainLinePath;
	}

	/**
	 * Die Methode getOrdinaryLines ermittelt zu einem übergebenen Systempfad die
	 * Liste der Varianten. Dabei werden nur echte Varianten zurückgegeben, aber
	 * nicht die Hauptvariante.
	 * 
	 * @param variationPath
	 * @return variationLineList
	 */
	public static List<String> getOrdinaryLines(String variationPath) {

		// Deklaration des Rückgabewertes
		LOG.trace("Ermittlung der gew\u00f6hnlichen Varianten zu " + variationPath);
		List<String> variationLineList = new ArrayList<String>();
		String variationLinePath = new String();

		// System, das die Hauptvariante beinhaltet
		File variation = new File(variationPath);
		String[] lines = variation.list();

		// Iteration über alle Varianten dieses Systems
		for (String line : lines) {

			// Nur echte (.pgn) Varianten zurückgeben, aber nicht die Hauptvariante
			// Diese werden in der Liste gesammelt
			if (line.matches(PORTABLE_GAME_NOTATION) && !line.matches(MAIN_LINE)) {
				variationLinePath = variationPath + File.separator + line;
				variationLineList.add(variationLinePath);
			}
		}

		return variationLineList;
	}
}
