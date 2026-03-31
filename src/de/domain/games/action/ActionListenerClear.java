package de.domain.games.action;

import java.awt.event.ActionEvent;

import javax.swing.JTextArea;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.domain.games.model.Game;

/**
 * Die Klasse ActionListenerTournament erweitert die abstrakte Oberklasse
 * ActionListenerButton, die das Interface ActionListener implementiert. Sie
 * verfügt über einen Konstruktor und über eine Methode actionPerformed(), mit
 * der das Action Event (Drücken des Buttons) getriggert wird.
 * 
 * @author Tim Schmitz
 *
 */

public class ActionListenerClear extends ActionListenerButton {

	// Statischer Logger für die Anwendung
	public static Logger LOG = LogManager.getLogger(ActionListenerClear.class.getName());

	// Statische Variablen für diesen ActionListener
	public static JTextArea activeTextArea = null;
	public static String activeAccessPath = null;
	public static String activeSelection = null;

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
	public ActionListenerClear(JTextArea textArea, String accessPath, String selection) {
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
	 */
	public void actionPerformed(ActionEvent event) {
		LOG.trace("Action Performed f\u00fcr " + event.getActionCommand());
		activeTextArea.setText(null);
		activeTextArea.append("Aktueller Zugriffspfad: " + activeAccessPath + "\n");
		activeTextArea.append("Aktuelle Auswahl: " + activeSelection + "\n");
		activeTextArea.append("L\u00f6sche Partien...\n");
		Game.deleteFiles(activeAccessPath);
		activeTextArea.append("FERTIG\n");
	}

}
