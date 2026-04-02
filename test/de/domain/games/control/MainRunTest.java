package de.domain.games.control;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.domain.games.control.MainRun;

class MainRunTest {

	// Statische Variablen für den Test
	public final static String EXPECTED_TEST_PC = "DESKTOP-LERLCJ6";
	public final static String EXPECTED_SCHMI_PC = "DESKTOP-Q6O0UGB";
	public final static String EXPECTED_LOGPATH_TEST = "C:\\Users\\test\\Daten\\Java\\Applications\\games\\logs";
	public final static String EXPECTED_BASEPATH_TEST = "C:\\Users\\test\\Documents\\ChessBase\\TimBase";

	/**
	 * Bevor irgendein Testfall ausgeführt wird, müssen erst die Properties gesetzt
	 * und geladen werden
	 */
	@BeforeAll
	public static void prepare() {
		MainRun.initialize();
	}

	/**
	 * Der Testfall testGetHostNameXXX ist environment-abhängig und muss je nach
	 * Umgebung angepasst werden.
	 */
	@Test
	void testGetHostNameSchmi() {
	 assertEquals(EXPECTED_SCHMI_PC, MainRun.getHostName());
	 }
	
	@Test
	void testGetLogPathTest() { assertEquals(EXPECTED_LOGPATH_TEST, MainRun.getLogPath(EXPECTED_TEST_PC)); }

	@Test
	void testGetBasePathTest() {assertEquals(EXPECTED_BASEPATH_TEST, MainRun.getBasePath(EXPECTED_TEST_PC));}

}
