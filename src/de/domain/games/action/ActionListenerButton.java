package de.domain.games.action;

import java.awt.event.ActionListener;

/**
 * Abstrakte Klasse, die das Interface ActionListener implementiert. Diese
 * Klasse soll als Elternklasse für alle ActionListener fungieren, die den
 * Buttons der Anwendung hinzugefügt werden und ein ActionEvent behandeln. Die
 * Verarbeitung des ActionEvents ist button-spezifisch. Das Hinzufügen des
 * Listeners geschieht in der Klasse Button und ist generisch. Die Auswahl des
 * Listeners hängt von dem Namen des Buttons ab.
 * 
 * @author Tim Schmitz
 *
 */

public abstract class ActionListenerButton implements ActionListener {

}
