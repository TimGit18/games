package de.domain.games.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

import javax.swing.JTextArea;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.domain.games.model.Tournament;
import de.domain.games.util.Directory;

/**
 * Die Klasse ActionListenerTournament erweitert die abstrakte Oberklasse
 * ActionListenerButton, die das Interface ActionListener implementiert. Sie
 * verfügt über einen Konstruktor und über eine Methode actionPerformed(), mit
 * der das Action Event (Drücken des Buttons) getriggert wird.
 * 
 * @author Tim Schmitz
 *
 */

public class ActionListenerTournament extends ActionListenerButton {

	// Statischer Logger für die Anwendung
	public static Logger LOG = LogManager.getLogger(ActionListenerTournament.class.getName());

	// Statische Variablen für diesen ActionListener
	public static JTextArea activeTextArea = null;
	public static String activeAccessPath = null;
	public static String activeSelection = null;
	public static List<String> tournamentYears = null;
	public static List<String> tournaments = null;
	public static Tournament tournament = null;

	/**
	 * Konstruktor für diesen ActionListener, der über eine TextArea, einen
	 * Zugriffspfad und eine Auswahl definiert wird. Die TextArea ist ein
	 * Ausgabefeld für die Ergebnisse der Aktion und protokolliert für den Benutzer
	 * das Geschehen. Der Zugriffspfad indiziert, wohin der Zugriff der Aktion gehen
	 * soll. Die Auswahl kommt von der Dialogsteuerung. Weiterhin wird noch eine
	 * Liste definiert, in der die angezeigten Ergebnisse gesammelt werden.
	 * 
	 * @param textArea
	 * @param accessPath
	 * @param selection
	 */

	public ActionListenerTournament(JTextArea textArea, String accessPath, String selection) {
		LOG.trace("Erzeuge Action Listener f\u00fcr " + selection);
		activeTextArea = textArea;
		activeAccessPath = accessPath;
		activeSelection = selection;
	}

	/**
	 * Die Methode actionPerformed wird von dem Interface definiert und in dieser
	 * Klasse implementiert. Sie legt fest, was im Falle eines ActionEvents (z.B.
	 * Drücken des Buttons) geschieht. In diesem Fall wird zunächst die (eventuell
	 * gefüllte) TextArea geleert und der aktuelle Zugriffspfad sowie die Auswahl
	 * dort ausgegeben. Anschließend findet die eigentliche Event-Verarabeitung
	 * statt, z.B. das Traversieren über sämtliche Elemente der Liste.
	 * 
	 */
	public void actionPerformed(ActionEvent event) {

		// Check der durchgeführten Aktion
		LOG.trace("Action Performed f\u00fcr " + event.getActionCommand());
		activeTextArea.setText(null);
		activeTextArea.append("Aktueller Zugriffspfad: " + activeAccessPath + "\n");
		activeTextArea.append("Aktuelle Auswahl: " + activeSelection + "\n");

		// Durchführung des eigentlichen ActionEvents:
		activeTextArea.append("Verarbeitung beginnt, bitte warten... " + activeSelection + "\n");
		Tournament.process(activeAccessPath);
		
		// Anzeige des eigentlichen Action Events: dient nur der Information
		// Ermittlung aller Turnierjahre und zugehörigen Turniere
		tournamentYears = Directory.getDirectories(activeAccessPath);

		for (String tournamentYear : tournamentYears) {
			activeTextArea.append("Verarbeite " + tournamentYear + "...bitte warten\n");

			// Ermittlung des Turnierpfades
			String tournamentYearPath = activeAccessPath + File.separator + tournamentYear;
			tournaments = Directory.getDirectories(tournamentYearPath);

			// Iteration über alle Turniere
			for (String tournament : tournaments) {
				activeTextArea.append("Verarbeite in " + tournamentYear + ": "+ tournament + "...bitte warten\n");
			}

		}
		activeTextArea.append("\n FERTIG!");
	}

}
