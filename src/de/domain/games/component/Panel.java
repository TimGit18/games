package de.domain.games.component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.TitledBorder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Die Klasse Panel verwaltet alle Panel-Komponenten der Anwendung. Insgesamt
 * existieren innerhalb der Anwendung drei Panels:
 * 
 * Das Dialog-Panel bildet das Basis-Panel, das Control-Panel ist das Panel für
 * die Steuerungselemente und das Display-Panel ist das Panel für die Anzeige
 * der Ergebnisse.
 * 
 * 
 * @author Tim Schmitz
 *
 */

public class Panel {

	// Statischer Logger für diese Klasse
	private static final Logger LOG = LogManager.getLogger(Panel.class.getName());

	// Variablen für das Dialog-Panel
	private static JPanel dialogPanel = null;

	// Variablen für das Control-Panel
	private static JPanel controlPanel = null;
	private static Border controlBorder = null;
	private static CompoundBorder controlCompoundBorder = null;
	private static TitledBorder controlTitledBorder = null;
	private static LayoutManager controlLayoutManager = null;

	// Variablen für das Display-Panel
	private static JPanel displayPanel = null;
	private static Border displayBorder = null;
	private static CompoundBorder displayCompoundBorder = null;
	private static TitledBorder displayTitledBorder = null;
	private static LayoutManager displayLayoutManager = null;

	// Variablen für die Scroll Pane
	private static JScrollPane scrollPane = null;
	private static Dimension scrollDimension = null;
	private static JTextArea scrollTextArea = null;

	/**
	 * Die Methode getControlPanel() liefert das Control-Panel zurück
	 * 
	 * @return controlPanel
	 */
	public static JPanel getControlPanel() {
		LOG.trace("Holen des Dialog-Panels");
		return controlPanel;
	}

	/**
	 * Die Methode getDialogPanel() liefert das Dialog-Panel zurück
	 * 
	 * @return dialogPanel
	 */
	public static JPanel getDialogPanel() {
		LOG.trace("Holen des Dialog-Panels");
		return dialogPanel;
	}

	/**
	 * Die Methode getDisplayPanel() liefert das Display-Panel zurück
	 * 
	 * @return displayPanel
	 */
	public static JPanel getDisplayPanel() {
		LOG.trace("Holen des Display-Panels");
		return displayPanel;
	}

	/**
	 * Die Methode getScrollPane() holt die Scroll Pane
	 * 
	 * @return scrollPane
	 */

	public static JScrollPane getScrollPane() {
		LOG.trace("Holen der ScrollPane");
		return scrollPane;
	}

	/**
	 * Die Methode getScrollTextArea() holt die Text Area der Scroll Pane
	 * 
	 * @return scrollTextArea
	 */

	public static JTextArea getScrollTextArea() {
		LOG.trace("Holen der ScrollTextArea");
		return scrollTextArea;
	}

	/**
	 * Die Methode setControlPanel() setzt das Steuerung-Panel fest. Zunächst wird
	 * die Border als Kombination einer gitelten und einer leeren Border erzeugt.
	 * Anschließend wird für das noch leere Panel die Border gesetzt und das Layout.
	 * 
	 * @param controlTitle
	 */

	public static void setControlPanel(String controlTitle) {
		LOG.trace("Setzen des Display-Panels");
		controlPanel = new JPanel();

		LOG.trace("Erzeugen der Border des Display-Panels");
		controlTitledBorder = BorderFactory.createTitledBorder(controlTitle);
		controlBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		controlCompoundBorder = BorderFactory.createCompoundBorder(controlTitledBorder, controlBorder);

		LOG.trace("Setzen der Border des Control-Panels");
		controlPanel.setBorder(controlCompoundBorder);

		LOG.trace("Setzen des Layouts des Control-Panels");
		controlLayoutManager = new BoxLayout(controlPanel, BoxLayout.LINE_AXIS);
		controlPanel.setLayout(controlLayoutManager);

	}

	/**
	 * Die Methode addButtonToControlPanel fügt einen Button, der als Parameter
	 * übergeben wird, dem Steuerungspanel hinzu. Zurückgegeben wird das Panel.
	 * 
	 * @param controlButton
	 * @return controlPanel
	 */
	public static JPanel addButtonToControlPanel(JButton controlButton) {
		LOG.trace("Hinzuf\u00fcgen des Buttons " + controlButton.getText() + " zu Steuerung-Panel");
		controlPanel.add(controlButton);
		return controlPanel;
	}

	/**
	 * Die Methode setDialogPanel() erzeugt ein statisches Panel für den Dialog. Sie
	 * setzt die Größe dieses Panels in Pixel fest und legt die Farbe des
	 * Hintergrunds fest.
	 */
	public static void setDialogPanel() {
		LOG.trace("Setzen des Dialog-Panels");
		dialogPanel = new JPanel();
		dialogPanel.setBackground(new Color(245, 245, 245));
	}

	/**
	 * Die Methode setDisplayPanel() erzeugt ein statisches Panel für den
	 * Anzeigebereich. Zunächst wird die Border als Kombination einer gitelten und
	 * einer leeren Border erzeugt. Anschließend wird für das noch leere Panel die
	 * Border gesetzt, das Layout und eine Scroll Pane hinzugefügt.
	 * 
	 * @param displayTitle
	 * @param scrollPane
	 */
	public static void setDisplayPanel(String displayTitle, JScrollPane scrollPane) {
		LOG.trace("Setzen des Display-Panels");
		displayPanel = new JPanel();

		LOG.trace("Erzeugen der Border des Display-Panels");
		displayTitledBorder = BorderFactory.createTitledBorder(displayTitle);
		displayBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		displayCompoundBorder = BorderFactory.createCompoundBorder(displayTitledBorder, displayBorder);

		LOG.trace("Setzen der Border des Display-Panels");
		displayPanel.setBorder(displayCompoundBorder);

		LOG.trace("Setzen des Layout des Display-Panels");
		displayLayoutManager = new BoxLayout(displayPanel, BoxLayout.LINE_AXIS);
		displayPanel.setLayout(displayLayoutManager);

		LOG.trace("Hinzuf\u00fcgen der Scroll Pane zum Display-Panel");
		displayPanel.add(scrollPane);
	}

	/**
	 * Die Methode setScrollPane() erzeugt ein Rollbalken-Feld, was nicht editierbar
	 * ist und einen Text anzeigt. Die Größe des Felds orientiert sich an der des
	 * übergeordneten Dialog-Panels
	 */

	public static void setScrollPane() {
		LOG.trace("Setzen der Scroll Pane");
		scrollDimension = new Dimension(500, 200);
		setScrollTextArea();
		scrollPane = new JScrollPane(scrollTextArea);
		scrollPane.setPreferredSize(scrollDimension);
	}

	/**
	 * Die Methode setScrollTextArea setzt eine nicht-editierbare TextArea, die dann
	 * dem ScrollPane hinzugefügt wird.
	 */

	public static void setScrollTextArea() {
		LOG.trace("Setzen der Scroll Text Area");
		scrollTextArea = new JTextArea();
		scrollTextArea.setEditable(false);
		scrollTextArea.setFont(java.awt.Font.getFont(Font.DIALOG));
	}

}
