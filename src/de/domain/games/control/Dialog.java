package de.domain.games.control;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.domain.games.build.Builder;
import de.domain.games.component.Button;
import de.domain.games.component.Frame;
import de.domain.games.component.Panel;
import de.domain.games.util.Property;

/**
 * Die Klasse Dialog repräsentiert das Dialog-Objekt der Anwendung. Sie
 * kontrolliert alle UI-Komponenten und setzt sie zu einer lauffähigen
 * Oberfläche zusammen. Die Dialogsteuerung delegiert soweit es möglich ist.
 *
 * @author Tim Schmitz
 *
 */
public class Dialog {

    // Statischer Logger für die Anwendung
    private static final Logger LOG = LogManager.getLogger(Dialog.class.getName());

    // Statische Variablen für die Anwendungsumgebung
    private static String TITLE;
    private static String DISPLAY;
    private static String CONTROL;
    private static String REPO;
    private static String CLEAR;

    // Statische Button-Variablen für die Datenbanken
    private static String WHITE;
    private static String BLACK;
    private static String TIM_WHITE;
    private static String TIM_BLACK;
    private static String TOURNAMENT;
    private static String ARCHIVE;
    private static String BOOK;

    // Statische Button-Variablen für das Repository
    private static String PUSH_WHITE;
    private static String PUSH_BLACK;
    private static String PUSH_TIM_WHITE;
    private static String PUSH_TIM_BLACK;
    private static String PUSH_TOURNAMENT;
    private static String PUSH_ARCHIVE;
    private static String PUSH_BOOK;

    // Variablen für die Zugriffspfade
    private static List<String> whitePaths = new ArrayList<>();
    private static List<String> blackPaths = new ArrayList<>();
    private static List<String> timWhitePaths = new ArrayList<>();
    private static List<String> timBlackPaths = new ArrayList<>();
    private static List<String> tournamentPaths = new ArrayList<>();
    private static List<String> archivePaths = new ArrayList<>();
    private static List<String> basePaths = new ArrayList<>();
    private static List<String> bookPaths = new ArrayList<>();

    // Komponenten des Dialogs
    private static JFrame dialogFrame;
    private static JFrame dialogComposedFrame;
    private static JPanel dialogPanel;
    private static JPanel dialogLayoutPanel;
    private static JPanel dialogControlPanel;
    private static JPanel dialogRepoPanel;
    private static JPanel dialogFullPanel;
    private static JPanel displayPanel;
    private static JPanel controlPanel;
    private static JPanel repoPanel;
    private static JScrollPane scrollPane;
    private static JTextArea textArea;

    // Subkomponenten des Steuerung-Panels
    private static JButton buttonWhite;
    private static JButton buttonBlack;
    private static JButton buttonTimWhite;
    private static JButton buttonTimBlack;
    private static JButton buttonTournament;
    private static JButton buttonArchive;
    private static JButton buttonBook;
    private static JButton buttonClear;

    // Subkomponenten des Repository-Panels
    private static JButton buttonPushWhite;
    private static JButton buttonPushBlack;
    private static JButton buttonPushTimWhite;
    private static JButton buttonPushTimBlack;
    private static JButton buttonPushTournament;
    private static JButton buttonPushArchive;
    private static JButton buttonPushBook;

    /**
     * Die Methode build() konstruiert den Dialog für die Anwendung.
     * <ol>
     *     <li>Zuerst wird der Rahmen für den Dialog inklusive Titel gesetzt</li>
     *     <li>Dann wird das Dialog-Panel (das Basis-Panel) erzeugt</li>
     *     <li>Im nächsten Schritt wird das Panel gelayoutet</li>
     *     <li>Anschließend wird die Scroll Pane erzeugt, die für das Display-Panel notwendig ist</li>
     *     <li>Danach kann das Display-Panel erzeugt und mit der Scroll Pane versehen werden </li>
     * </ol> Im nächsten Schritt werden die Control-Buttons erzeugt
     * und aktiviert. Diese Buttons werden dann einem weiteren Panel, dem
     * Control-Panel, hinzugefügt. Anschließend werden das Dialog-Panel, das
     * Control-Panel dem Dialog-Panel hinzugefügt, wozu an eine weitere Komponente
     * Builder delegiert wird, der auch das Layout managet. Dieser Builder setzt
     * auch das Dialog-Panel in den Rahmen ein, der zum Schluß angezeigt wird.
     */
    public static void build() {
        LOG.trace("*************************");
        LOG.trace("Step 1: Build des Rahmens");
        LOG.trace("*************************");
        Frame.setFrame(TITLE);

        LOG.trace("**********************************");
        LOG.trace("Step 2: Erzeugen des Dialog-Panels");
        LOG.trace("**********************************");
        Panel.setDialogPanel();

        LOG.trace("***********************************************");
        LOG.trace("Step 3: Setzen des Layouts f\u00fcr das Dialog-Panel");
        LOG.trace("***********************************************");
        dialogPanel = Panel.getDialogPanel();
        dialogLayoutPanel = Builder.setLayout(dialogPanel);

        LOG.trace("********************************");
        LOG.trace("Step 4: Erzeugen der Scroll Pane");
        LOG.trace("********************************");
        Panel.setScrollPane();
        scrollPane = Panel.getScrollPane();

        LOG.trace("***********************************");
        LOG.trace("Step 5: Erzeugen des Display-Panels");
        LOG.trace("***********************************");
        Panel.setDisplayPanel(DISPLAY, scrollPane);
        displayPanel = Panel.getDisplayPanel();
        textArea = Panel.getScrollTextArea();

        LOG.trace("************************************");
        LOG.trace("Step 6: Erzeugen der Control-Buttons");
        LOG.trace("************************************");
        Button.setButton(WHITE);
        buttonWhite = Button.getButton();
        Button.setButton(BLACK);
        buttonBlack = Button.getButton();
        Button.setButton(TIM_WHITE);
        buttonTimWhite = Button.getButton();
        Button.setButton(TIM_BLACK);
        buttonTimBlack = Button.getButton();
        Button.setButton(TOURNAMENT);
        buttonTournament = Button.getButton();
        Button.setButton(ARCHIVE);
        buttonArchive = Button.getButton();
        Button.setButton(BOOK);
        buttonBook = Button.getButton();
        Button.setButton(CLEAR);
        buttonClear = Button.getButton();

        LOG.trace("*********************************");
        LOG.trace("Step 7: Erzeugen der Repo-Buttons");
        LOG.trace("*********************************");
        Button.setButton(PUSH_WHITE);
        buttonPushWhite = Button.getButton();
        Button.setButton(PUSH_BLACK);
        buttonPushBlack = Button.getButton();
        Button.setButton(PUSH_TIM_WHITE);
        buttonPushTimWhite = Button.getButton();
        Button.setButton(PUSH_TIM_BLACK);
        buttonPushTimBlack = Button.getButton();
        Button.setButton(PUSH_TOURNAMENT);
        buttonPushTournament = Button.getButton();
        Button.setButton(PUSH_ARCHIVE);
        buttonPushArchive = Button.getButton();
        Button.setButton(PUSH_BOOK);
        buttonPushBook = Button.getButton();

        LOG.trace("**************************************");
        LOG.trace("Step 8: Aktivieren der Control-Buttons");
        LOG.trace("**************************************");
        Button.activate(buttonWhite, textArea, whitePaths, WHITE);
        Button.activate(buttonBlack, textArea, blackPaths, BLACK);
        Button.activate(buttonTimWhite, textArea, timWhitePaths, TIM_WHITE);
        Button.activate(buttonTimBlack, textArea, timBlackPaths, TIM_BLACK);
        Button.activate(buttonTournament, textArea, tournamentPaths, TOURNAMENT);
        Button.activate(buttonArchive, textArea, archivePaths, ARCHIVE);
        Button.activate(buttonClear, textArea, basePaths, CLEAR);
        Button.activate(buttonBook, textArea, bookPaths, BOOK);

        LOG.trace("***********************************");
        LOG.trace("Step 9: Erzeugen des Control-Panels");
        LOG.trace("***********************************");
        Panel.setControlPanel(CONTROL);
        controlPanel = Panel.getControlPanel();

        LOG.trace("**************************************");
        LOG.trace("Step 10: Hinzuf\u00fcgen der Control-Buttons");
        LOG.trace("**************************************");
        controlPanel = Panel.addButtonToControlPanel(buttonWhite);
        controlPanel = Panel.addButtonToControlPanel(buttonBlack);
        controlPanel = Panel.addButtonToControlPanel(buttonTimWhite);
        controlPanel = Panel.addButtonToControlPanel(buttonTimBlack);
        controlPanel = Panel.addButtonToControlPanel(buttonTournament);
        controlPanel = Panel.addButtonToControlPanel(buttonArchive);
        controlPanel = Panel.addButtonToControlPanel(buttonBook);
        controlPanel = Panel.addButtonToControlPanel(buttonClear);

        LOG.trace("*******************************************************");
        LOG.trace("Step 11: Hinzuf\u00fcgen des Control-Panels zum Dialog-Panel");
        LOG.trace("*******************************************************");
        dialogControlPanel = Builder.setComponentToPanel(controlPanel, 0, 0, 1,
                1, 0.5, 0.5, 1, 1, dialogLayoutPanel);

        LOG.trace("***************************************");
        LOG.trace("Step 12: Erzeugen des Repository-Panels");
        LOG.trace("***************************************");
        Panel.setRepositoryPanel(REPO);
        repoPanel = Panel.getRepoPanel();

        LOG.trace("**************************************");
        LOG.trace("Step 13: Hinzuf\u00fcgen der Repo-Buttons");
        LOG.trace("**************************************");
        repoPanel = Panel.addButtonToRepoPanel(buttonPushWhite);
        repoPanel = Panel.addButtonToRepoPanel(buttonPushBlack);
        repoPanel = Panel.addButtonToRepoPanel(buttonPushTimWhite);
        repoPanel = Panel.addButtonToRepoPanel(buttonPushTimBlack);
        repoPanel = Panel.addButtonToRepoPanel(buttonPushTournament);
        repoPanel = Panel.addButtonToRepoPanel(buttonPushArchive);
        repoPanel = Panel.addButtonToRepoPanel(buttonPushBook);

        LOG.trace("*******************************************************");
        LOG.trace("Step 14: Hinzuf\u00fcgen des Repo-Panels zum Dialog-Panel");
        LOG.trace("*******************************************************");
        dialogRepoPanel = Builder.setComponentToPanel(repoPanel, 0, 1, 1,
                1, 0.5, 0.5, 1, 1, dialogControlPanel);

        LOG.trace("*******************************************************");
        LOG.trace("Step 15: Hinzuf\u00fcgen des Display-Panels zum Dialog-Panel");
        LOG.trace("*******************************************************");
        dialogFullPanel = Builder.setComponentToPanel(displayPanel, 0, 2, 1,
                2, 0.5, 0.5, 1, 1, dialogRepoPanel);

        LOG.trace("***********************************************");
        LOG.trace("Step 16: Hinzuf\u00fcgen des Dialog-Panels zum Frame");
        LOG.trace("***********************************************");
        dialogFrame = Frame.getFrame();
        dialogComposedFrame = Builder.addPanelToFrame(dialogFullPanel, dialogFrame);

        LOG.trace("****************************");
        LOG.trace("Step 17: Anzeige des Rahmens");
        LOG.trace("****************************");
        Frame.displayFrame(dialogComposedFrame);
    }

    /**
     * Die Methode initialize() initialisiert den Anwendungsrahmen des Dialogs. Sie
     * lädt die Properties der Anwendung, die für die Anzeige und Steuerung der
     * Anwendung wichtig sind und liest sie lokal aus.
     */
    public static void initialize() {
        LOG.trace("Initialisierung des Dialogs");

        TITLE = Property.getFrameTitle();
        LOG.trace("Property Title: " + TITLE);

        DISPLAY = Property.getDisplayTitle();
        LOG.trace("Property Display: " + DISPLAY);

        CONTROL = Property.getControlTitle();
        LOG.trace("Property Control: " + CONTROL);

        REPO = Property.getRepoTitle();
        LOG.trace("Property Repository: " + REPO);

        CLEAR = Property.getButtonClear();
        LOG.trace("Button Leeren: " + CLEAR);

        WHITE = Property.getButtonWhite();
        LOG.trace("Button Wei\u00df: " + WHITE);

        BLACK = Property.getButtonBlack();
        LOG.trace("Button Schwarz: " + BLACK);

        TIM_WHITE = Property.getButtonTimWhite();
        LOG.trace("Button TimWei\u00df: " + TIM_WHITE);

        TIM_BLACK = Property.getButtonTimBlack();
        LOG.trace("Button TimSchwarz: " + TIM_BLACK);

        TOURNAMENT = Property.getButtonTournament();
        LOG.trace("Button Turnier: " + TOURNAMENT);

        ARCHIVE = Property.getButtonArchive();
        LOG.trace("Button Archiv: " + ARCHIVE);

        BOOK = Property.getButtonBook();
        LOG.trace("Button Buch: " + BOOK);

        PUSH_WHITE = Property.getButtonWhite();
        LOG.trace("Button Wei\u00df: " + PUSH_WHITE);

        PUSH_BLACK = Property.getButtonBlack();
        LOG.trace("Button Schwarz: " + PUSH_BLACK);

        PUSH_TIM_WHITE = Property.getButtonTimWhite();
        LOG.trace("Button TimWei\u00df: " + PUSH_TIM_WHITE);

        PUSH_TIM_BLACK = Property.getButtonTimBlack();
        LOG.trace("Button TimSchwarz: " + PUSH_TIM_BLACK);

        PUSH_TOURNAMENT = Property.getButtonTournament();
        LOG.trace("Button Turnier: " + PUSH_TOURNAMENT);

        PUSH_ARCHIVE = Property.getButtonArchive();
        LOG.trace("Button Archiv: " + PUSH_ARCHIVE);

        PUSH_BOOK = Property.getButtonBook();
        LOG.trace("Button Buch: " + PUSH_BOOK);


    }

    /**
     * Die Methode setLookAndFeel() setzt das Look & Feel des zugehörigen
     * Betriebssystems.
     */
    public static void setLookAndFeel() {
        LOG.trace("Setzen des Look and Feel des Dialogs");
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (UnsupportedLookAndFeelException systemException) {
            systemException.getMessage();
        } catch (ClassNotFoundException classNotFoundException) {
            classNotFoundException.getMessage();
        } catch (InstantiationException instantiationException) {
            instantiationException.getMessage();
        } catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.getMessage();
        }
    }

    /**
     * Die Methode setAccessPaths definiert die verschiedenen Zugriffspfade der
     * Anwendung. Dabei benutzt sie einerseits den bereits in der Hauptmethode
     * ermittelten Basispfad der Anwendung, andererseits auch einige der Properties,
     * die während der Initialisierung ermittelt wurden.
     *
     * @param basePath
     */
    public static void setAccessPaths(String basePath) {

        // Ermitteln des Eröffnungsnamens
        String OPENING = Property.getOpeningPath();
        LOG.trace("Opening Path: " + OPENING);

        // Setzen der Zugriffspfade für den Weiß-Button
        whitePaths.add(basePath + File.separator + OPENING + File.separator + WHITE);
        LOG.trace("Zugriffspfad Wei\u00df: " + whitePaths.get(0));

        // Setzen der Zugriffspfade für den Schwarz-Button
        blackPaths.add(basePath + File.separator + OPENING + File.separator + BLACK);
        LOG.trace("Zugriffspfad Schwarz: " + blackPaths.get(0));

        // Setzen der Zugriffspfade für den TimWeiß-Button
        timWhitePaths.add(basePath + File.separator + OPENING + File.separator + TIM_WHITE);
        LOG.trace("Zugriffspfad TimWei\u00df: " + timWhitePaths.get(0));

        // Setzen der Zugriffspfade für den TimSchwarz-Button
        timBlackPaths.add(basePath + File.separator + OPENING + File.separator + TIM_BLACK);
        LOG.trace("Zugriffspfad TimSchwarz: " + timBlackPaths.get(0));

        // Setzen der Zugriffspfade für den Turnier-Button
        tournamentPaths.add(basePath + File.separator + TOURNAMENT);
        LOG.trace("Zugriffspfad Turnier: " + tournamentPaths.get(0));

        // Setzen der Zugriffspfade für den Buch-Button
        bookPaths.add(basePath + File.separator + BOOK);
        LOG.trace("Zugriffspfad Buch: " + bookPaths.get(0));

        // Die Archivierungs-Pfade für den Archiv-Button setzen
        archivePaths.add(basePath + File.separator + ARCHIVE);
        archivePaths.add(basePath + File.separator + OPENING + File.separator + WHITE);
        archivePaths.add(basePath + File.separator + OPENING + File.separator + BLACK);
        archivePaths.add(basePath + File.separator + OPENING + File.separator + TIM_WHITE);
        archivePaths.add(basePath + File.separator + OPENING + File.separator + TIM_BLACK);
        archivePaths.add(basePath + File.separator + TOURNAMENT);

        LOG.trace("Zugriffspfade Archiv 0: " + archivePaths.get(0));
        LOG.trace("Zugriffspfade Archiv 1: " + archivePaths.get(1));
        LOG.trace("Zugriffspfade Archiv 2: " + archivePaths.get(2));
        LOG.trace("Zugriffspfade Archiv 3: " + archivePaths.get(3));
        LOG.trace("Zugriffspfade Archiv 4: " + archivePaths.get(4));
        LOG.trace("Zugriffspfade Archiv 5: " + archivePaths.get(5));

        // Den Basispfad für den Leeren-Button setzen
        basePaths.add(basePath);
        LOG.trace("Zugriffspfad Leeren: " + basePaths.get(0));
    }

    /**
     * Die Methode start startet den Dialog und liefert eine lauffähige Oberfläche
     * aus. Im ersten Schritt initialisiert sie die notwendigen Parameter und setzt
     * basierend darauf die benötigten Zugriffspfade. Dann wird das systemimmanente
     * Look and Feel gesetzt, bevor im letzten Schritt die Anwendung in mehreren
     * Subschritten zusammengesetzt wird.
     */
    public static void start(String basePath) {
        LOG.trace("Starten des Dialogs");
        initialize();
        setAccessPaths(basePath);
        setLookAndFeel();
        build();
    }
}
