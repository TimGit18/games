package de.domain.games.component;

import java.util.List;

import javax.swing.JButton;
import javax.swing.JTextArea;

import de.domain.games.action.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.domain.games.util.Property;

/**
 * Die Klasse Button stellt alle Daten und Methoden zur Verfügung, um einen
 * Steuerung-Button für die Anwendung bereitszustellen.
 *
 * @author schmi
 */

public class Button {

    // Logger für diese Klasse
    private static final Logger LOG = LogManager.getLogger(Button.class.getName());

    // Variablen für den Dialog der Anwendung
    private static JButton button = null;
    private static ActionListenerButton actionListener = null;

    /**
     * Die Klasse setButton() setzt einen Button mit dem übergebenen Button-Namen.
     *
     * @param buttonName
     */
    public static void setButton(String buttonName) {
        LOG.trace("Setze Button " + buttonName);
        button = new JButton(buttonName);
    }

    /**
     * Die Klasse getButton() liefert den Button zurück. Dieser ist als private
     * Komponente geschützt.
     *
     * @return button
     */

    public static JButton getButton() {
        LOG.trace("Holen Button " + button.getText());
        return button;
    }

    /**
     * Die Methode activate aktiviert den übergebenen Button, indem ein
     * ActionListener definiert wird, der von den übergebenen Parametern accessPaths
     * und selection abhängt. Der so definierte ActionListener wird mit einer
     * TextArea versehen und dem Button hinzugefügt. Die einzige strukturelle
     * Ausnahme bildet der ActionListenerArchive, der mehr als einen Zugriffspfad
     * verwertet.
     *
     * @param activatableButton
     * @param textArea
     * @param accessPaths
     * @param selection
     */

    public static void activate(JButton activatableButton, JTextArea textArea, List<String> accessPaths,
                                String selection) {
        LOG.trace("Activate Button " + activatableButton.getText());
        if (selection.equals(Property.getButtonWhite())) {
            actionListener = new ActionListenerWhite(textArea, accessPaths.get(0), selection);
        } else if (selection.equals(Property.getButtonBlack())) {
            actionListener = new ActionListenerBlack(textArea, accessPaths.get(0), selection);
        } else if (selection.equals(Property.getButtonTimWhite())) {
            actionListener = new ActionListenerTimWhite(textArea, accessPaths.get(0), selection);
        } else if (selection.equals(Property.getButtonTimBlack())) {
            actionListener = new ActionListenerTimBlack(textArea, accessPaths.get(0), selection);
        } else if (selection.equals(Property.getButtonTournament())) {
            actionListener = new ActionListenerTournament(textArea, accessPaths.get(0), selection);
        } else if (selection.equals(Property.getButtonArchive())) {
            actionListener = new ActionListenerArchive(textArea, accessPaths.get(0), accessPaths.get(1),
                    accessPaths.get(2), accessPaths.get(3), accessPaths.get(4), accessPaths.get(5),selection);
        } else if (selection.equals(Property.getButtonClear())) {
            actionListener = new ActionListenerClear(textArea, accessPaths.get(0), selection);
        } else if (selection.equals(Property.getButtonBook())) {
            actionListener = new ActionListenerBook(textArea, accessPaths.get(0), selection);
        }
        activatableButton.addActionListener(actionListener);
    }

}

