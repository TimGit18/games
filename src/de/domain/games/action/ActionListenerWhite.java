package de.domain.games.action;

import java.awt.event.ActionEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextArea;

import de.domain.games.model.Colour;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.domain.games.model.Opening;
import de.domain.games.util.Directory;

/**
 * Die Klasse ActionListenerWhite erweitert die abstrakte Oberklasse
 * ActionListenerButton, die das Interface ActionListener implementiert. Sie
 * verfügt über einen Konstruktor und über eine Methode actionPerformed(), mit
 * der das Action Event (Drücken des Buttons) getriggert wird.
 * 
 * @author Tim Schmitz
 *
 */

public class ActionListenerWhite extends ActionListenerButton {

	// Statischer Logger für die Anwendung
	public static Logger LOG = LogManager.getLogger(ActionListenerWhite.class.getName());

	// Statische Variablen für diesen ActionListener
	public static JTextArea activeTextArea = null;
	public static String activeAccessPath = null;
	public static String activeSelection = null;
	public static List<String> openings = null;
	public static Opening opening = null;
	public static Colour colour = null;

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
	public ActionListenerWhite(JTextArea textArea, String accessPath, String selection) {
		LOG.trace("Erzeuge Action Listener f\u00fcr " + selection);
		activeTextArea = textArea;
		activeAccessPath = accessPath;
		activeSelection = selection;
		openings = new ArrayList<>();
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
		LOG.trace("Aktueller Zugriffspfad: " + activeAccessPath);
		LOG.trace("Aktuelle Auswahl: " + activeSelection);

		// Durchführung des eigentlichen AktionEvents:
		// Iteration über die Farbe
		colour = new Colour(activeAccessPath, activeSelection);
		colour.process();

		// Ermittlung aller Eröffnungen und Iteration über jede Eröffnung
		openings = Directory.getDirectories(activeAccessPath);
		for (String openingItem : openings) {
			activeTextArea.append("Verarbeite " + openingItem + "...bitte warten\n");

			// Festlegung des Eröffnungspfades
			String openingPath = activeAccessPath + File.separator + openingItem;
			LOG.trace("OpeningPath:  " + openingPath);

			// Delegation der weiteren Verarbeitung an die Klasse Opening
			opening = new Opening(openingPath, openingItem, activeAccessPath);
			opening.process();

		}
		activeTextArea.append("\n FERTIG!");
	}

}
