package de.domain.games.action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

/**
 * Die Klasse ActionListenerPushWhite erweitert die abstrakte Oberklasse ActionListenerButton, die das Interface
 * ActionListener implementiert. Sie verfügt über einen Konstruktor und über eine Methode actionPerformed(), mit
 * der das Action Event (Drücken des Buttons) getriggert wird.
 * 
 * @author schmi
 *
 */

public class ActionListenerPushBlack extends ActionListenerButton {

	// Statischer Logger für die Anwendung
	public static Logger LOG = LogManager.getLogger(ActionListenerPushBlack.class.getName());

	// Statische Variablen für diesen ActionListener
	public static JTextArea activeTextArea = null;
	public static String activeSelection = null;

	/**
	 * Konstruktor für diesen ActionListener, der über eine TextArea und eine Auswahl definiert wird.
	 * Die TextArea ist ein Ausgabefeld für die Ergebnisse der Aktion und zeigt für den Benutzer das Geschehen an.
	 * Die Auswahl kommt von der Dialogsteuerung.
	 *
	 * @param textArea
	 * @param selection
	 */
	public ActionListenerPushBlack(JTextArea textArea, String selection) {
		LOG.trace("Erzeuge Action Listener Push f\u00fcr " + selection);
		activeTextArea = textArea;
		activeSelection = selection;
	}

	/**
	 * Die Methode actionPerformed wird von dem Interface definiert und in dieser Klasse implementiert.
	 * Sie legt fest, was im Falle eines ActionEvents (Drücken des Buttons) geschieht.
	 * In diesem Fall wird zunächst die (eventuell gefüllte) TextArea geleert und die Auswahl dort ausgegeben.
	 * Anschließend findet die eigentliche Event-Verarabeitung statt.
	 *
	 * @param event
	 */
	public void actionPerformed(ActionEvent event) {
		LOG.trace("actionPerformed() für PushWhite.... " + event.getActionCommand());
		activeTextArea.setText(null);
		LOG.trace("Aktuelle Auswahl: " + activeSelection);

		activeTextArea.append("Push " + activeSelection + "...bitte warten\n");
		ProcessBuilder processBuilder = new ProcessBuilder("C:\\Users\\schmi\\Daten\\Text\\Github\\Proc.bat");
		try {
			Process process = processBuilder.start();
			InputStream processInputStream = process.getInputStream();
			String output = new String(processInputStream.readAllBytes(), StandardCharsets.UTF_8);
			activeTextArea.append(output + "\n");
			// LOG.trace(output);
			} catch (IOException exception) {
				throw new RuntimeException(exception);
			}
		activeTextArea.append("\n FERTIG!");
		}

    }