package de.domain.games.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * Die Klasse Directory liefert einige querschnittliche Methoden für die
 * Anwendung, die sich auf die Dateistruktur und die Verzeichnisse beziehen, in
 * denen die Partien abgespeichert sind.
 * 
 * @author Tim Schmitz
 *
 */

public class Directory {

	// Definition des Loggers
	public static Logger LOG = LogManager.getLogger(Directory.class.getName());

	// Statische Konstante zur Erkennung von Datenbanken
	public static final String PORTABLE_GAME_NOTATION = "^(.*)\\.pgn$";

	/**
	 * Die Methode getDirectories() ermittelt alle Unterverzeichnisse, die zu dem
	 * Verzeichnis gehören, das durch den Zugriffspfad identifiziert wird.
	 * 
	 * @param accessPath
	 * @return directories
	 */
	public static List<String> getDirectories(String accessPath) {

		// Liste der zu ermitteltenden Unterverzeichnisse des Zugriffspfad-Verzeichnis
		List<String> directories = new ArrayList<>();

		/*
		  Über den Zugriffspfad wird auf das Verzeichnis zugegriffen und sein Inhalt
		  aufgelistet
		 */
		File directory = new File(accessPath);
		String[] items = directory.list();

		if (items != null) {
			for (String item : items) {
				String filePath = accessPath + File.separator + item;
				File file = new File(filePath);

				// Prüfe ob das Kriterium erfüllt ist. Wenn ja, wird es hinzufügt
				if (file.isDirectory())
					directories.add(item);
			}
		}

		// Rückabe der Unterverzeichnisse
		return directories;
	}

	/**
	 * Die Methode containsdatabase() ermittelt, ob ein Verzeichnis, das durch einen übergebenen Pfad gekennzeichnet
	 * ist, eine PGN-Datenbank enthält. Dafür wird über alle Elemente des Verzeichnisses iteriert und geprüft, ob ihr
	 * Dateiname mit der Formatendung matchet.
	 *
	 * @param accessPath
	 * @return directoryContainsDatabase
	 */
	public static Boolean containsDatabase(String accessPath) {

		// Rückgabewert der Anwendung ist initial falsch
		boolean directoryContainsDatabase = false;

		// Definition des Zielverzeichnisses und seiner Elemente
		File directory = new File(accessPath);
		String[] items = directory.list();

		// Iteration über alle Elemente des Verzeichnisses
		if (items != null) {
			for (String item : items) {
				if (item.matches(PORTABLE_GAME_NOTATION)) {
					directoryContainsDatabase = true;
				}
			}
		}
		return directoryContainsDatabase;
	}

	/**
	 * Die Methode deleteFile() löscht eine Datei oder ein Verzeichnis rekursiv.
	 * @return fileIsDeleted
	 */
	public static void deleteFile(String path) {

		// Initialisierung der Variablen für diese Methode
		String documentPath;
		File file = new File(path);

		String unicodePath = path.replace("ß", "\u00df");
		LOG.trace("Pfad: " + unicodePath);

		// Überprüfung, ob es sich um eine richtige Datei handelt oder ein Verzeichnis
		if (file.isDirectory()) {

			LOG.trace(unicodePath + " ist Verzeichnis");
			String[] documentList = file.list();

			if (documentList!=null) {

				// Rekursiver Aufruf der Löschmethode
				for (String document : documentList) {
					documentPath = path + File.separator + document;
					deleteFile(documentPath);
				}
			}
		}
		LOG.trace(unicodePath + " ist Datei");
		file.delete();
		LOG.trace(unicodePath + " ist gel\u00f6scht");
	}

	/**
	 * Die Methode createDirectory() erzeugt ein Verzeichnis über einen Verzeichnispfad.
	 * @param directoryPath
	 * @return directoryIsCreated
	 */
	public static void createDirectory(String directoryPath) {
		File directory = new File(directoryPath);
		directory.mkdir();
	}

	/**
	 * Die Methode copyDirectory() kopiert ein Verzeichnis recursiv von einem gegebenen Quell-Pfad in einen
	 * definierten ZUielpfad. Dafür benutzt sie die Methode copyFile().
	 */
	public static void copyDirectory(String sourcePath, String targetPath) {

		// Temporäre Variablen für die Pfade
		String originalPath = new String();
		String createdPath = new String();

		// Definition der Quelldatei über den Pfad
		File sourceFile = new File(sourcePath);

		// Falls die Quelldatei ein Verzeichnis ist, dann wird die Methode rekursiv aufgerufen.
		if (sourceFile.isDirectory()) {

			// Liste aller Dateien dieses Verzeichnisses
			String[] files = sourceFile.list();

			// Jede Datei der Liste wird untersucht
			for (String file : files) {

				// Den Quell- und den Zielpfad hinunterdrillen
				originalPath = sourcePath + File.separator + file;
				createdPath = targetPath + File.separator + file;

				// Rekursiver Methodenaufruf
				copyDirectory(originalPath, createdPath);
			}
		}

		// Falls die Quelldatei eine richtige Datei ist, so wird die entsprechende Kopiermethode aufgerufen
		if (sourceFile.isFile()) copyFile(sourcePath, targetPath);


	}

	/**
	 * Die Methode copyFile() kopiert eine binäre Datei oder eine Zeichendatei. Dabei unterscheiden sich die
	 * Kopiermethoden. Im Falle einer binären Datei werden ein Input Stream und ein Output Stream definiert.
	 * Falls eine  ASCII-Datei kopiert wird, reichen bereits ein Reader und ein Writer aus.
	 * @param sourcePath
	 * @param targetPath
	 */
	public static void copyFile(String sourcePath, String targetPath) {

		// Definition von Quell- und Zieldatei über den vollqualifizierten Pfad
		File sourceFile = new File(sourcePath);
		File targetFile = new File(targetPath);

		try {

			// Definition des Readers und des Writers
			FileReader inputReader = new FileReader(sourceFile);
				FileWriter outputWriter = new FileWriter(targetFile);

				// Kopier-Prozedur
				int c;
				while ((c = inputReader.read()) != -1)
					outputWriter.write(c);
				inputReader.close();
				outputWriter.close();

		}
		catch (IOException ioException) {
			LOG.error(ioException.getMessage());
		}
	}

	/**
	 * Die Methode createDocumentStructure überführt eine Quellstruktur in eine Zielstruktur. Dafür werden rekursive
	 * Methodenaufrufe unterstützt.
	 * @param sourcePath
	 * @param targetPath
	 */
	public static void createDocumentStructure(String sourcePath, String targetPath) {

		// Lokale Variablen für diese3 Methode
		String documentPath = new String();
		String createdPath = new String();

		// Dateidefinition von Quell- und Zielpfad
		File sourceFile = new File(sourcePath);
		File targetFile = new File(targetPath);

		// Erzeugen einer Verzeichnisstruktur nur dann, falls das Quellverzeichnis ein richtiges Verzeichnbis ist
		if (sourceFile.isDirectory()) {
			String[] documents = sourceFile.list();

			// Die Überfrprüfung der folgenden Bedingung ist eigentlich nicht notwendig.
			if (!targetFile.exists())	targetFile.mkdir();

			// Hinunterdriillen der Struktur
			for (String document : documents) {
				documentPath = sourcePath + File.separator + document;
				createdPath = targetPath + File.separator + document;
				createDocumentStructure(documentPath, createdPath);
			}
		}
	}
}
