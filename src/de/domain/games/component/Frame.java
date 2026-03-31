package de.domain.games.component;

import javax.swing.JFrame;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Die Klasse Frame stellt alle notwendigen Daten und Methoden zur Verfügung, um
 * einen Rahmen für die Anwendung bereitzustellen.
 * 
 * @author schmi
 *
 */

public class Frame {

	// Logger für diese Klasse
	private static final Logger LOG = LogManager.getLogger(Frame.class.getName());

	// Variablen für den Rahmen der Anwendung
	private static JFrame dialogFrame = null;

	/**
	 * Die Methode displayFrame() richtet die Größe des Rahmens anhand der
	 * Komponenten und deren bevorzugten Größe aus und macht das Fenster sichtbar
	 */
	public static void displayFrame(JFrame frame) {
		LOG.trace("Anzeige des Dialogs");
		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Die Methode setFrame() setzt den Rahmen für den Dialog der Anwendung. Der
	 * Titel der Anwendung wird als Parameter übergeben
	 * 
	 * @param title
	 */

	public static void setFrame(String title) {
		LOG.trace("Setzen des Dialog-Rahmens");
		dialogFrame = new JFrame(title);
		dialogFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Die Methode getFrame() liefert den Rahmen der Anwendung. Da er eine private
	 * Komponente ist, kann auf diese nicht direkt zugegriffen werden.
	 * 
	 * @return dialogFrame
	 */
	public static JFrame getFrame() {
		LOG.trace("Holen des Dialog-Rahmens");
		return dialogFrame;
	}

}
