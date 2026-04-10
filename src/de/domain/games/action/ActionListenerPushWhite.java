package de.domain.games.action;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.swing.JTextArea;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

/**
 * Die Klasse ActionListenerPushWhite erweitert die abstrakte Oberklasse ActionListenerButton, die das Interface
 * ActionListener implementiert. Sie verfügt über einen Konstruktor und über eine Methode actionPerformed(), mit
 * der das Action Event (Drücken des Buttons) getriggert wird.
 * 
 * @author schmi
 *
 */

public class ActionListenerPushWhite extends ActionListenerButton {

	// Statischer Logger für die Anwendung
	public static Logger LOG = LogManager.getLogger(ActionListenerPushWhite.class.getName());

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
	public ActionListenerPushWhite(JTextArea textArea, String selection) {
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

		/*
		ProcessBuilder pb = new ProcessBuilder("C:\\Path\\To\\MyApp.exe", "arg1", "arg2");
		pb.directory(new File("C:\\Path\\To\\")); // Optional: Set working directory
        Process p = null;
        try {
            p = pb.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            int exitCode = p.waitFor(); // Wait for completion
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }*/

        activeTextArea.append("Push " + activeSelection + "...bitte warten\n");
		activeTextArea.append("\n FERTIG!");
	}

}
