package de.domain.games.util;

import static org.junit.jupiter.api.Assertions.*;

// Notwendig wegen JUnit 5
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PropertyTest {

	// Erwartungswerte für die PC-Namen
	public final static String EXPECTED_META_PC = "22H0566";
	public final static String EXPECTED_PRIVAT_PC = "DESKTOP-3NCAR95";
	public final static String EXPECTED_SCHMI_PC = "DESKTOP-Q6O0UGB";
	public final static String EXPECTED_TEST_PC = "DESKTOP-LERLCJ6";

	// Erwartungswerte für die Logging-Pfade
	public final static String EXPECTED_LOGPATH_META = "C:\\Users\\TSZ\\Daten\\Java\\Applications\\games\\logs";
	public final static String EXPECTED_LOGPATH_SCHMI = "C:\\Users\\schmi\\Daten\\Java\\Applications\\games\\logs";
	public final static String EXPECTED_LOGPATH_PRIVAT = "C:\\Users\\Privat\\Daten\\Java\\Applications\\games\\logs";
	public final static String EXPECTED_LOGPATH_TEST = "C:\\Users\\test\\Daten\\Java\\Applications\\games\\logs";

	// Erwartungswerte für die Basispfade
	public final static String EXPECTED_BASEPATH_META = "C:\\Users\\TSZ\\Documents\\ChessBase\\TimBase";
	public final static String EXPECTED_BASEPATH_PRIVAT = "C:\\Users\\Privat\\Documents\\ChessBase\\TimBase";
	public final static String EXPECTED_BASEPATH_SCHMI = "C:\\Users\\schmi\\Documents\\ChessBase\\TimBase";
	public final static String EXPECTED_BASEPATH_TEST = "C:\\Users\\test\\Documents\\ChessBase\\TimBase";

	// Erwartungswertue für die Buttons
	public final static String EXPECTED_BUTTON_WHITE = "NeuWeiss";
	public final static String EXPECTED_BUTTON_BLACK = "NeuSchwarz";
	public final static String EXPECTED_BUTTON_TIM_WHITE = "TimWeiss";
	public final static String EXPECTED_BUTTON_TIM_BLACK = "TimSchwarz";
	public final static String EXPECTED_BUTTON_TOURNAMENT = "Turnier";
	public final static String EXPECTED_BUTTON_ARCHIVE = "Archiv";
	public final static String EXPECTED_BUTTON_CLEAR = "Leeren";

	// Sonstige Erwartungswerte
	public final static String EXPECTED_CONTROL_TITLE = "Steuerung";
	public final static String EXPECTED_REPOSITORY_TITLE = "Repository";
	public final static String EXPECTED_DISPLAY_TITLE = "Anzeige";
	public final static String EXPECTED_ERROR_HOST = "Kein Computername definiert";
	public final static String EXPECTED_FRAME_TITLE = "Partien";
	public final static String EXPECTED_OPENING_PATH = "Eroeffnung";

	/**
	 * Bevor irgendein Testfall ausgeführt wird, müssen erst die Properties gesetzt
	 * und geladen werden
	 */
	@BeforeAll
	public static void prepare() {
		Property.setProperties();
		Property.load();
	}

	//-------------------//
	// Test der PC-Namen //
	//-------------------//

	@Test
	void testGetMetaPC() {
		assertEquals(EXPECTED_META_PC, Property.getMetaPC());
	}

	@Test
	void testGetPrivatPC() { assertEquals(EXPECTED_PRIVAT_PC, Property.getPrivatPC()); }

	@Test
	void testGetSchmiPC() {
		assertEquals(EXPECTED_SCHMI_PC, Property.getSchmiPC());
	}

	@Test
	void testGetTestPC() {
		assertEquals(EXPECTED_TEST_PC, Property.getTestPC());
	}

	//------------------------//
	// Test der Logging-Pfade //
	//------------------------//
	@Test
	void testGetLogPathMeta() {
		assertEquals(EXPECTED_LOGPATH_META, Property.getLogPathMeta());
	}

	@Test
	void testGetLogPathPrivat() {
		assertEquals(EXPECTED_LOGPATH_PRIVAT, Property.getLogPathPrivat());
	}

	@Test
	void testGetLogPathSchmi() {
		assertEquals(EXPECTED_LOGPATH_SCHMI, Property.getLogPathSchmi());
	}

	@Test
	void testGetLogPathTest() {
		assertEquals(EXPECTED_LOGPATH_TEST, Property.getLogPathTest());
	}

	//---------------------//
	// Test der Basispfade //
	//---------------------//
	@Test
	void testGetBasePathMeta() {
		assertEquals(EXPECTED_BASEPATH_META, Property.getBasePathMeta());
	}

	@Test
	void testGetBasePathPrivat() { assertEquals(EXPECTED_BASEPATH_PRIVAT, Property.getBasePathPrivat());	}

	@Test
	void testGetBasePathSchmi() {
		assertEquals(EXPECTED_BASEPATH_SCHMI, Property.getBasePathSchmi());
	}

	@Test
	void testGetBasePathTest() {
		assertEquals(EXPECTED_BASEPATH_TEST, Property.getBasePathTest());
	}

	//------------------//
	// Test der Buttons //
	//------------------//
	@Test
	void testGetButtonWhite() {
		assertEquals(EXPECTED_BUTTON_WHITE, Property.getButtonWhite());
	}

	@Test
	void testGetButtonBlack() {
		assertEquals(EXPECTED_BUTTON_BLACK, Property.getButtonBlack());
	}

	@Test
	void testGetButtonTimWhite() {
		assertEquals(EXPECTED_BUTTON_TIM_WHITE, Property.getButtonTimWhite());
	}

	@Test
	void testGetButtonTimBlack() {
		assertEquals(EXPECTED_BUTTON_TIM_BLACK, Property.getButtonTimBlack());
	}

	@Test
	void testGetButtonTournament() {
		assertEquals(EXPECTED_BUTTON_TOURNAMENT, Property.getButtonTournament());
	}

	@Test
	void testGetButtonArchive() {
		assertEquals(EXPECTED_BUTTON_ARCHIVE, Property.getButtonArchive());
	}

	@Test
	void testGetButtonClear() {
		assertEquals(EXPECTED_BUTTON_CLEAR, Property.getButtonClear());
	}

	//----------------------------//
	// Test der übrigen Parameter //
	//----------------------------//
	@Test
	void testGetErrorHost() {
		assertEquals(EXPECTED_ERROR_HOST, Property.getErrorHost());
	}

	@Test
	void testGetFrameTitle() {
		assertEquals(EXPECTED_FRAME_TITLE, Property.getFrameTitle());
	}

	@Test
	void testGetDisplayTitle() {
		assertEquals(EXPECTED_DISPLAY_TITLE, Property.getDisplayTitle());
	}

	@Test
	void testGetControlTitle() {
		assertEquals(EXPECTED_CONTROL_TITLE, Property.getControlTitle());
	}

	@Test
	void testGetRepoTitle() {
		assertEquals(EXPECTED_REPOSITORY_TITLE, Property.getRepoTitle());
	}

	@Test
	void testGetOpeningPath() {
		assertEquals(EXPECTED_OPENING_PATH, Property.getOpeningPath());
	}
}
