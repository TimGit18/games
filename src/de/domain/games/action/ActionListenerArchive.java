package de.domain.games.action;

import java.awt.event.ActionEvent;

import javax.swing.JTextArea;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.domain.games.model.Archive;

/**
 * Die Klasse ActionListenerArchive erweitert die abstrakte Oberklasse
 * ActionListenerButton, die das Interface ActionListener implementiert. Sie
 * verfügt über einen Konstruktor und über eine Methode actionPerformed(), mit
 * der das Action Event (Drücken des Buttons) getriggert wird.
 * 
 * @author Tim Schmitz
 */

public class ActionListenerArchive extends ActionListenerButton {

	// Statischer Logger für die Anwendung
	public static Logger LOG = LogManager.getLogger(ActionListenerArchive.class.getName());

	// Statische Variablen für diesen ActionListener
	public static JTextArea activeTextArea = null;
	public static String activeAccessPath = null;
	public static String whiteAccessPath = null;
	public static String blackAccessPath = null;
	public static String timWhiteAccessPath = null;
	public static String timBlackAccessPath = null;
	public static String tournamentAccessPath = null;
	public static String activeSelection = null;

	/**
	 * Konstruktor für diesen ActionListener, der über eine TextArea, einen
	 * Zugriffspfad, mehrere weitere Zugriffspfade und eine Auswahl definiert wird.
	 * Die TextArea ist ein Ausgabefeld für die Ergebnisse der Aktion und
	 * protokolliert für den Benutzer das Geschehen. Der Zugriffspfad indiziert,
	 * wohin der Zugriff der Aktion gehen soll. Die weiteren Zugriffspfade beziehen
	 * sich auf Aktionen, die mit dem ersten Zugriff gemacht werden können. Die
	 * Auswahl kommt von der Dialogsteuerung. Weiterhin wird noch eine Liste
	 * definiert, in der die angezeigten Ergebnisse gesammelt werden.
	 *
	 * @param textArea
	 * @param accessPath
	 * @param selection
	 */
	public ActionListenerArchive(JTextArea textArea, String accessPath, String accessWhitePath, String accessBlackPath,
			String accessTimWhitePath, String accessTimBlackPath, String accessTournamentPath, String selection) {
		LOG.trace("Erzeuge Action Listener f\u00fcr " + selection);
		activeTextArea = textArea;
		activeAccessPath = accessPath;
		whiteAccessPath = accessWhitePath;
		blackAccessPath = accessBlackPath;
		timWhiteAccessPath = accessTimWhitePath;
		timBlackAccessPath = accessTimBlackPath;
		tournamentAccessPath = accessTournamentPath;
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
		LOG.trace("Action Performed f\u00fcr " + event.getActionCommand());
		activeTextArea.setText(null);
		activeTextArea.append("Aktueller Zugriffspfad:          	" + activeAccessPath + "\n");
		activeTextArea.append("Aktueller Zugriffspfad Wei\u00df:    " + whiteAccessPath + "\n");
		activeTextArea.append("Aktueller Zugriffspfad Schwarz:  " + blackAccessPath + "\n");
		activeTextArea.append("Aktueller Zugriffspfad TimWei\u00df:     " + timWhiteAccessPath + "\n");
		activeTextArea.append("Aktueller Zugriffspfad TimSchwarz:  " + timBlackAccessPath + "\n");
		activeTextArea.append("Aktueller Zugriffspfad Turnier:  " + tournamentAccessPath + "\n");
		activeTextArea.append("Aktuelle Auswahl: " + activeSelection + "\n");
		Archive.process(activeAccessPath, whiteAccessPath, blackAccessPath, timWhiteAccessPath, timBlackAccessPath,
					tournamentAccessPath);
	}
}
