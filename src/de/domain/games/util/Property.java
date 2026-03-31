package de.domain.games.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Die Klasse Property ist die Property-Klasse der Anwendung. Sie lädt die
 * Properties, setzt die Keys und ermittelt für jeden Key das entsprechende
 * Property.
 * 
 * @author Tim Schmitz
 *
 */

public class Property {

	// Variablen für die Properties-Datei
	private static File file;
	private static Properties properties;

	// Statische Variablen für die PC-Namen
	private static String metaPC;
	private static String schmiPC;
	private static String privatPC;
	private static String testPC;
	private static String timPC ;

	// Statische Variablen für die Logging-Pfade
	private static String logPathMeta;
	private static String logPathPrivat;
	private static String logPathSchmi;
	private static String logPathTest;
	private static String logPathTim;

	// Statische Variablen für die Basispfade
	private static String basePathMeta;
	private static String basePathSchmi;
	private static String basePathPrivat;
	private static String basePathTest;
	private static String basePathTim;

	// Statische Variablen für die Testpfade
	// private static String testPathMeta;
	// private static String testPathSchmi;
	// private static String testPathPrivat;
	// private static String testPathTest;
	// private static String testPathTim;

	// Statische Variablen für die Datenpfade
	private static String dataPathMeta;
	private static String dataPathSchmi;
	private static String dataPathPrivat;
	private static String dataPathTest;
	private static String dataPathTim;

	// Statische Variablen für die Buttons
	private static String buttonWhite;
	private static String buttonBlack;
	private static String buttonTimWhite;
	private static String buttonTimBlack;
	private static String buttonTournament;
	private static String buttonArchive;
	private static String buttonClear;
	private static String buttonBook;

	// Sonstige Variablen
	private static String errorHost;
	private static String frameTitle;
	private static String displayTitle;
	private static String controlTitle;
	private static String openingPath;

	/**
	 * Die Methode load() erzeugt einen Eingabestrom mit Dateinamen und lädt die
	 * Properties des Projekts
	 */
	public static void load() {
		try {
			FileInputStream inputStream = new FileInputStream(file);
			properties.load(inputStream);
		} catch (IOException ioException) {
			ioException.getMessage();
		}
	}

	/**
	 * Die Methode setKey() setzt für jedes Property die Keys, um später einen Value
	 * auszuwerten
	 */
	private static void setKeys() {

		// Setzen der PC-Namen
		metaPC = "meta.pc";
		privatPC = "privat.pc";
		schmiPC = "schmi.pc";
		testPC = "test.pc";
		timPC = "tim.pc";

		// Setzen der Logging-Pfade
		logPathMeta = "logpath.meta";
		logPathSchmi = "logpath.schmi";
		logPathPrivat = "logpath.privat";
		logPathTest = "logpath.test";
		logPathTim = "logpath.tim";

		// Setzen der Basispfade
		basePathMeta = "basepath.meta";
		basePathSchmi = "basepath.schmi";
		basePathPrivat = "basepath.privat";
		basePathTest = "basepath.test";
		basePathTim = "basepath.tim";

		// Setzen der Datenpfade
		dataPathMeta = "datapath.meta";
		dataPathSchmi = "datapath.schmi";
		dataPathPrivat = "datapath.privat";
		dataPathTest = "datapath.test";
		dataPathTim = "datapath.tim";

		// Setzen der Testpfade
		// testPathMeta = "testpath.meta";
		// testPathSchmi = "testpath.schmi";
		// testPathPrivat = "testpath.privat";
		// testPathTest = "testpath.test";
		// testPathTim = "testpath.tim";

		// Setzen der Buttons
		buttonWhite = "button.white";
		buttonBlack = "button.black";
		buttonTimWhite = "button.timWhite";
		buttonTimBlack = "button.timBlack";
		buttonTournament = "button.tournament";
		buttonArchive = "button.archive";
		buttonClear = "button.clear";
		buttonBook = "button.book";

		// Setzen der sonstigen Keys
		frameTitle = "frame.title";
		displayTitle = "display.title";
		controlTitle = "control.title";
		errorHost = "error.host";
		openingPath = "opening.path";
	}

	/**
	 * Die Methode setProperties() definiert das Property-File samt Pfad
	 */
	public static void setProperties() {

		// Ermitteln der Properties
		String directory = System.getProperty("user.dir") + File.separator + "properties";
		file = new File(directory, "games.properties");
		properties = new Properties();
		setKeys();
	}

	//------------------------//
	// Ermitteln der PC-Namen //
	//------------------------//
	/**
	 * Die Methode getMetaPC() ermittelt den Computernamen zu Meta-PC
	 *
	 * @return metaPcValue
	 */
	public static String getMetaPC() {
		return properties.getProperty(metaPC);
	}

	/**
	 * Die Methode getPrivatPC() ermittelt den Computernamen zu PRIVAT-PC
	 *
	 * @return privatPcValue
	 */
	public static String getPrivatPC() {
		return properties.getProperty(privatPC);
	}

	/**
	 * Die Methode getSchmiPC() ermittelt den Computernamen zu Schmi-PC
	 *
	 * @return schmiPcValue
	 */
	public static String getSchmiPC() {
		return properties.getProperty(schmiPC);
	}

	/**
	 * Die Methode getTestPC() ermittelt den Computernamen zu DESKTOP-LERLCJ6
	 *
	 * @return testPcValue
	 */
	public static String getTestPC() {
		return properties.getProperty(testPC);
	}

	/**
	 * Die Methode getTimPC() ermittelt den Computernamen zu Tim-PC
	 *
	 * @return timPcValue
	 */
	public static String getTimPC() {
		return properties.getProperty(timPC);
	}

	//-----------------------------//
	// Ermitteln der Logging-Pfade //
	//-----------------------------//
	/**
	 * Die Methode getLogPathMeta() ermittelt den Logging Pfad auf Meta-PC
	 *
	 * @return logPathMetaValue
	 */
	public static String getLogPathMeta() {
		return properties.getProperty(logPathMeta);
	}

	/**
	 * Die Methode getLogPathPrivat() ermittelt den Logging Pfad auf PRIVAT-PC
	 *
	 * @return logPathPrivatValue
	 */
	public static String getLogPathPrivat() {
		return properties.getProperty(logPathPrivat);
	}

	/**
	 * Die Methode getLogPathSchmi() ermittelt den Logging Pfad auf dem Schmi-PC
	 *
	 * @return logPathSchmiValue
	 */
	public static String getLogPathSchmi() {
		return properties.getProperty(logPathSchmi);
	}

	/**
	 * Die Methode getLogPathTest() ermittelt den Logging Pfad auf DESKTOP-LERLCJ6
	 *
	 * @return logPathTestValue
	 */
	public static String getLogPathTest() {
		return properties.getProperty(logPathTest);
	}

	/**
	 * Die Methode getLogPathTest() ermittelt den Logging Pfad auf Tim-PC
	 *
	 * @return logPathTimValue
	 */
	public static String getLogPathTim() {
		return properties.getProperty(logPathTim);
	}

	//---------------------------//
	// Ermitteln der Basis-Pfade //
	//---------------------------//
	/**
	 * Die Methode getBasePathMeta() ermittelt den Basis-Pfad auf Meta-PC
	 *
	 * @return basePathMetaValue
	 */
	public static String getBasePathMeta() {
		return properties.getProperty(basePathMeta);
	}

	/**
	 * Die Methode getBasePathPrivat() ermittelt den Basis-Pfad auf PRIVAT-PC
	 *
	 * @return basePathPrivatValue
	 */
	public static String getBasePathPrivat() {
		return properties.getProperty(basePathPrivat);
	}

	/**
	 * Die Methode getBasePathSchmi() ermittelt den Basis-Pfad auf dem Schmi-PC
	 *
	 * @return basePathSchmiValue
	 */
	public static String getBasePathSchmi() {
		return properties.getProperty(basePathSchmi);
	}

	/**
	 * Die Methode getBasePathTest() ermittelt den Basis-Pfad auf DESKTOP-LERLCJ6
	 *
	 * @return basePathTestValue
	 */
	public static String getBasePathTest() {
		return properties.getProperty(basePathTest);
	}

	/**
	 * Die Methode getBasePathTim() ermittelt den Basis-Pfad auf Tim-PC
	 *
	 * @return basePathTimValue
	 */
	public static String getBasePathTim() {
		return properties.getProperty(basePathTim);
	}

	//--------------------------//
	// Ermitteln der Test-Pfade //
	// Ermitteln der Data-Pfade //
	//--------------------------//
	/**
	 * Die Methode getTestPathMeta() ermittelt den Test-Pfad auf Meta-PC
	 *
	 * @return testPathMetaValue
	public static String getTestPathMeta() {
		return properties.getProperty(testPathMeta);
	}	 */

	public static String getDataPathMeta() {
		return properties.getProperty(dataPathMeta);
	}

	/**
	 * Die Methode getTestPathSchmi() ermittelt den Test-Pfad auf dem Schmi-PC
	 *
	 * @return testPathSchmiValue

	public static String getTestPathSchmi() {
		return properties.getProperty(testPathSchmi);
	}
	 */
	public static String getDataPathSchmi() {
		return properties.getProperty(dataPathSchmi);
	}

		/**
         * Die Methode getTestPathPrivat() ermittelt den Test-Pfad auf PRIVAT-PC
         *
         * @return testPathPrivatValue
	public static String getDataPathPrivat() {
		return properties.getProperty(dataPathPrivat);
	}         */

	public static String getDataPathPrivat() {
			return properties.getProperty(dataPathPrivat);
	}

	/**
	 * Die Methode getTestPathTest() ermittelt den Test-Pfad auf DESKTOP-LERLCJ6
	 *
	 * @return testPathTestValue
	public static String getTestPathTest() {
		return properties.getProperty(testPathTest);
	}
	 */
	public static String getDataPathTest() {
		return properties.getProperty(dataPathTest);
	}

	/**
	 * Die Methode getTestPathTim() ermittelt den Test-Pfad auf Tim-PC
	 *
	 * @return testPathTimValue

	public static String getTestPathTim() {
		return properties.getProperty(testPathTim);
	}
	 */
	public static String getDataPathTim() {
		return properties.getProperty(dataPathTim);
	}



	//-----------------------//
	// Ermitteln der Buttons //
	//-----------------------//
	/**
	 * Die Methode getButtonWhite() liefert den Namen für den Weiß-Button.
	 * 
	 * @return buttonWhiteValue
	 */
	public static String getButtonWhite() {
		return properties.getProperty(buttonWhite);
	}

	/**
	 * Die Methode getButtonBlack() liefert den Namen für den Schwarz-Button.
	 * 
	 * @return buttonBlackValue
	 */
	public static String getButtonBlack() {
		return properties.getProperty(buttonBlack);
	}

	/**
	 * Die Methode getButtonTimWhite() liefert den Namen für den TimWeiß-Button.
	 *
	 * @return buttonTimWhiteValue
	 */
	public static String getButtonTimWhite() {
		return properties.getProperty(buttonTimWhite);
	}

	/**
	 * Die Methode getButtonTimBlack() liefert den Namen für den TimSchwarz-Button.
	 *
	 * @return buttonTimBlackValue
	 */
	public static String getButtonTimBlack() {
		return properties.getProperty(buttonTimBlack);
	}

	/**
	 * Die Methode getButtonTournament() liefert den Namen für den Turnier-Button.
	 * 
	 * @return buttonTournamentValue
	 */
	public static String getButtonTournament() {
		return properties.getProperty(buttonTournament);
	}

	/**
	 * Die Methode getButtonArchive() liefert den Namen für den Archiv-Button.
	 * 
	 * @return buttonArchiveValue
	 */
	public static String getButtonArchive() {
		return properties.getProperty(buttonArchive);
	}

	/**
	 * Die Methode getButtonClear() liefert den Namen für den Leeren-Button.
	 * 
	 * @return buttonClearValue
	 */
	public static String getButtonClear() {
		return properties.getProperty(buttonClear);
	}

	/**
	 * Die Methode getButtonBook() liefert den Namen für den Buch-Button.
	 *
	 * @return buttonBookValue
	 */
	public static String getButtonBook() { return properties.getProperty(buttonBook) ;}

	//-----------------------------------//
	// Ermitteln der sonstigen Parameter //
	//-----------------------------------//
	/**
	 * Die Methode getErrorHost() ermittelt die Fehlermeldung, wenn kein Rechner
	 * definiert wurde
	 * 
	 * @return errorHostValue
	 */
	public static String getErrorHost() {
		return properties.getProperty(errorHost);
	}

	/**
	 * Die Methode getFrameTitle() ermittelt den Namen, der auf dem Rahmen des
	 * Dialogs der Anwendung angezeigt wird
	 * 
	 * @return frameTitleValue
	 */

	public static String getFrameTitle() {
		return properties.getProperty(frameTitle);
	}

	/**
	 * Die Methode getDisplayTitle() ermittelt den Namen, der auf dem Rand des
	 * Anzeige-Panels des Dialogs gezeigt wird.
	 * 
	 * @return displayTitleValue
	 */

	public static String getDisplayTitle() {
		return properties.getProperty(displayTitle);
	}

	/**
	 * Die Methode getControlTitle() ermittelt den Namen der auf dem Rand des
	 * Steuerung-Panels des Dialogs gezeigt wird.
	 * 
	 * @return controlTitleValue
	 */

	public static String getControlTitle() {
		return properties.getProperty(controlTitle);
	}

	/**
	 * Die Methode getOpeningPath() ermittelt die Signatur des Eröffnungspfads
	 * 
	 * @return openingPathValue
	 */
	public static String getOpeningPath() {
		return properties.getProperty(openingPath);
	}

}
