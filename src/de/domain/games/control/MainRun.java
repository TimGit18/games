package de.domain.games.control;

import java.io.IOException;
import java.net.InetAddress;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.domain.games.util.Property;

/**
 * Die Klasse MainRun ist die Hauptklasse der Anwendung. Zuerst wird die
 * System-Umgebung initialisiert. Anschließend inspiziert sie die
 * System-Umgebung der Anwendung und legt in Abhängigkeit von der Umgebung fest,
 * wohin die Anwendung gelogget wird. Zum Schluß wird die Kontrolle der
 * Anwendung übergeben und die Dialogsteuerung aufgerufen.
 *
 * @author Tim Schmitz
 *
 */

public class MainRun {

    // PC-Namen
    private static String META_PC = null;
    private static String PRIVAT_PC = null;
    private static String SCHMI_PC = null;
    private static String TEST_PC = null;

    // Logging-Pfade
    private static String LOG_PATH_META_PC = null;
    private static String LOG_PATH_PRIVAT_PC = null;
    private static String LOG_PATH_SCHMI_PC = null;
    private static String LOG_PATH_TEST_PC = null;

    // Basispfade
    private static String BASE_PATH_META_PC = null;
    private static String BASE_PATH_PRIVAT_PC = null;
    private static String BASE_PATH_SCHMI_PC = null;
    private static String BASE_PATH_TEST_PC = null;

    // Datapfade
    private static String DATA_PATH_META_PC = null;
    private static String DATA_PATH_PRIVAT_PC = null;
    private static String DATA_PATH_SCHMI_PC = null;
    private static String DATA_PATH_TEST_PC = null;


    // Sonstige Parameter
    private static String ERROR_HOST = null;
    private static String ENVIRONMENT = null;
    private static final String ENVIRONMENT_TEST = "Test";
    private static final String ENVIRONMENT_PROD = "Prod";

    /**
     * Die Hauptmethode der Anwendung initialisiert den Umgebungskontext, setzt die
     * System-Properties und schreibt ein Logging. Erst danach wird der Dialog
     * gestartet.
     *
     * @param args
     */
    public static void main(String[] args) {

        // Initialisierung
        initialize();

        // Setzen der System-Properties
        if (args.length > 0) ENVIRONMENT = args[0];
        else                 ENVIRONMENT = "";

        // Ermitteln der Basiswerte (Host, Logging-Pfad, Test-Pfad und Basis-Pfad)
        String computerName = getHostName();
        String logPath = getLogPath(computerName);
        // String testPath = getTestPath(computerName);
        String dataPath = getDataPath(computerName);
        String basePath = getBasePath(computerName);

        // Environment auswerten (BasePath = Daten oder Documents)
        if (ENVIRONMENT.equals(ENVIRONMENT_TEST)) basePath = basePath;
        if (ENVIRONMENT.equals(ENVIRONMENT_PROD)) basePath = dataPath;

        // Setzen des System Logging-Pfades
        setSystemLogPath(logPath);

        // Der Logger ist ein Parameter, um den Workflow der Anwendung zu
        // protokollieren. Erst wenn der Umgebungskontext initialisiert wurde, kann der
        // Logger gesetzt werden.
        Logger LOG = LogManager.getLogger(MainRun.class.getName());
        LOG.trace("Computername:  " + computerName);
        LOG.trace("LogPath:       " + logPath);
        LOG.trace("DataPath:      " + dataPath);
        LOG.trace("BasePath:      " + basePath);
        LOG.trace("Environment:   " + ENVIRONMENT );

        // Starten der Anwendung
        Dialog.start(basePath);
    }

    /**
     * Die Methode getHostName() ermittelt den Rechnernamen. Dieser wird später für
     * den Logging-Kontext und den Basispfad benötigt
     *
     * @return hostName
     */
    public static String getHostName() {
        String hostName = "";

        // Den Rechnernamen aus der Umgebung holen
        try {
            hostName = InetAddress.getLocalHost().getHostName();
        } catch (IOException ioException) {
            System.err.println(ioException.getMessage());
        }
        System.out.println(hostName);
        return hostName;
    }

    /**
     * Die Methode getLogPath() ermittelt den (zu setzenden) Logging Path abhänging
     * vom Rechnernamen.
     *
     * @param computerName
     * @return logPath
     */
    public static String getLogPath(String computerName) {
        String logPath = "";

        // Abfrage des Rechnernamens und setzen des Logging Pfads
        if (computerName.equals(META_PC)) {
            logPath = LOG_PATH_META_PC;
        } else if (computerName.equals(PRIVAT_PC)) {
            logPath = LOG_PATH_PRIVAT_PC;
        } else if (computerName.equals(SCHMI_PC)) {
            logPath = LOG_PATH_SCHMI_PC;
        } else if (computerName.equals(TEST_PC)) {
            logPath = LOG_PATH_TEST_PC;
        } else
            System.err.println(ERROR_HOST);

        System.out.println("MainRun: " + computerName + ":" + logPath );

        return logPath;
    }

     /**
     * Die Methode getBasePath() ermittelt den Basispfad zu dem Rechnernamen. Ebenso
     * wie der Logging-Pfad hängt der Basispfad vom Rechnernamen ab.
     *
     * @param computerName
     * @return basePath
     */
    public static String getBasePath(String computerName) {
        String basePath = "";

        // Abfrage des Rechnernamens und setzen des Basis-Pfads
        if (computerName.equals(META_PC)) {
            basePath = BASE_PATH_META_PC;
        } else if (computerName.equals(PRIVAT_PC)) {
            basePath = BASE_PATH_PRIVAT_PC;
        } else if (computerName.equals(SCHMI_PC)) {
            basePath = BASE_PATH_SCHMI_PC;
        } else if (computerName.equals(TEST_PC)) {
            basePath = BASE_PATH_TEST_PC;
        } else
            System.err.println(ERROR_HOST);

        return basePath;
    }


    public static String getDataPath(String computerName) {
        String dataPath = "";

        // Abfrage des Rechnernamens und setzen des Logging Pfads
        if (computerName.equals(META_PC)) {
            dataPath = DATA_PATH_META_PC;
        } else if (computerName.equals(PRIVAT_PC)) {
            dataPath = DATA_PATH_PRIVAT_PC;
        } else if (computerName.equals(SCHMI_PC)) {
            dataPath = DATA_PATH_SCHMI_PC;
        } else if (computerName.equals(TEST_PC)) {
            dataPath = DATA_PATH_TEST_PC;
        } else
            System.err.println(ERROR_HOST);

        return dataPath;
    }

    /**
     * Die Methode initialize() setzt die Propiertes und lädt sie. Dann werden die
     * möglichen Computernamen und ihre zugehörigen Logging-Pfade ermittelt sowie
     * eine Fehlermeldung.
     */
    public static void initialize() {

        // Properties setzen und laden
        Property.setProperties();
        Property.load();

        // Computernamen ermitteln
        META_PC = Property.getMetaPC();
        PRIVAT_PC = Property.getPrivatPC();
        SCHMI_PC = Property.getSchmiPC();
        TEST_PC = Property.getTestPC();

        // Logging-Pfade ermitteln
        LOG_PATH_META_PC = Property.getLogPathMeta();
        LOG_PATH_PRIVAT_PC = Property.getLogPathPrivat();
        LOG_PATH_SCHMI_PC = Property.getLogPathSchmi();
        LOG_PATH_TEST_PC = Property.getLogPathTest();

        // Basis-Pfade ermitteln
        BASE_PATH_META_PC = Property.getBasePathMeta();
        BASE_PATH_PRIVAT_PC = Property.getBasePathPrivat();
        BASE_PATH_SCHMI_PC = Property.getBasePathSchmi();
        BASE_PATH_TEST_PC = Property.getBasePathTest();

        DATA_PATH_META_PC = Property.getDataPathMeta();
        DATA_PATH_PRIVAT_PC = Property.getDataPathPrivat();
        DATA_PATH_SCHMI_PC = Property.getDataPathSchmi();
        DATA_PATH_TEST_PC = Property.getDataPathTest();

        // Error Message ermitteln
        ERROR_HOST = Property.getErrorHost();
    }

    /**
     * Die Methode setSystemLogPath() setzt den Logging Pfad als System-Property.
     * Dieses Property bleibt während der Laufzeit der Anwendung erhalten.
     */

    private static void setSystemLogPath(String logPath) {
        System.setProperty("log.path", logPath);
    }

}
