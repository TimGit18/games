package de.domain.games.action;

import de.domain.games.model.Book;
import de.domain.games.model.Tournament;
import de.domain.games.util.Directory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.util.List;

/**
 * Die Klasse ActionListenerBook erweitert die abstrakte Oberklasse
 * ActionListenerButton, die das Interface ActionListener implementiert. Sie
 * verfügt über einen Konstruktor und über eine Methode actionPerformed(), mit
 * der das Action Event (Drücken des Buttons) getriggert wird.
 * 
 * @author Tim Schmitz
 *
 */

public class ActionListenerBook extends ActionListenerButton {

	// Statischer Logger für die Anwendung
	public static Logger LOG = LogManager.getLogger(ActionListenerBook.class.getName());

	// Statische Variablen für diesen ActionListener
	public static JTextArea activeTextArea = null;
	public static String activeAccessPath = null;
	public static String activeSelection = null;
	public static List<String> books = null;
	public static List<String> chapters = null;

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

	public ActionListenerBook(JTextArea textArea, String accessPath, String selection) {
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
		Book.process(activeAccessPath);
		
		// Anzeige des eigentlichen Action Events: dient nur der Information
		// Ermittlung aller Turnierjahre und zugehörigen Turniere
		books = Directory.getDirectories(activeAccessPath);

		for (String book : books) {
			activeTextArea.append("Verarbeite " + book + "...bitte warten\n");

			// Ermittlung des Turnierpfades
			String bookPath = activeAccessPath + File.separator + book;
			chapters = Directory.getDirectories(bookPath);

			// Iteration über alle Turniere
			for (String chapter : chapters) {
				activeTextArea.append("Verarbeite in " + book + ": "+ chapter + "...bitte warten\n");
			}

		}
		activeTextArea.append("\n FERTIG!");
	}

}
