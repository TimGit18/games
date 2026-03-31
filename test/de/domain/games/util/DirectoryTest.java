package de.domain.games.util;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

//Notwendig wegen JUnit 5
import de.domain.games.control.MainRun;
import de.domain.games.util.Property;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import de.domain.games.util.Directory;

class DirectoryTest {

	public final String OPENING_PATH_WHITE_SCHMI = "C:\\Users\\schmi\\Documents\\ChessBase\\TimBase\\Eroeffnung\\Weiss";
	public final String ARCHIV_PATH_SCHMI = "C:\\Users\\schmi\\Documents\\ChessBase\\TimBase\\Archiv";

	@BeforeAll
	public static void prepare() {
		// MainRun mainRun = new MainRun();
		MainRun.initialize();
		String computerName = MainRun.getHostName();
		String logPath = MainRun.getLogPath(computerName);
		System.out.println("DirectoryTest: " + computerName + ":" + logPath );
		System.setProperty("log.path", logPath);
	}

	@Test
	void testContainsDatabase() {
		Boolean directoryDoesNotContain = Directory.containsDatabase(OPENING_PATH_WHITE_SCHMI);
		assertEquals(directoryDoesNotContain,false);
	}

	@Test
	void testNotContainsDatabase() {
	 	Boolean directoryDoesNotContain = Directory.containsDatabase(ARCHIV_PATH_SCHMI);
	 	assertEquals(directoryDoesNotContain,false);
	}


}
