package de.pennychecker.kata;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class IataExchangeRateApplication {

	public void run() throws Exception {
		readIataExchangeRates();

		displayMenu();

		boolean exitRequested = false;

		while (!exitRequested) {
			String userInput = getUserInput();

			exitRequested = processUserInputAndCheckForExitRequest(userInput);
		}

		System.out.println("Auf Wiedersehen!");
	}

	private void readIataExchangeRates() {
		// TODO: Hier muss das Einlesen der IATA-Währungskurse aus der Datei
		// geschehen.
	}

	private void displayMenu() {
		System.out.println("IATA Währungskurs-Beispiel");
		System.out.println();

		System.out.println("Wählen Sie eine Funktion durch Auswahl der Zifferntaste und Drücken von 'Return'");
		System.out.println("[1] Währungskurs anzeigen");
		System.out.println("[2] Neuen Währungskurs eingeben");
		System.out.println();

		System.out.println("[0] Beenden");
	}

	private String getUserInput() throws Exception {
		BufferedReader consoleInput = new BufferedReader(new InputStreamReader(System.in));

		return consoleInput.readLine();
	}

	// Returns true when the user wants to exit the application
	private boolean processUserInputAndCheckForExitRequest(String userInput) throws Exception {
		if (userInput.equals("0")) {
			return true;
		}

		if (userInput.equals("1")) {
			displayIataExchangeRate();
		} else if (userInput.equals("2")) {
			enterIataExchangeRate();
		} else {
			System.out.println("Falsche Eingabe. Versuchen Sie es bitte erneut.");
		}

		return false;
	}

	/**
	 * SK aus csv  die 2. Spalte ausgeben ( dao currencyIsoCode und datum )
	 * @throws Exception
	 */
	private void displayIataExchangeRate() throws Exception {
		String currencyIsoCode = getUserInputForStringField("Währung");
		Date date = getUserInputForDateField("Datum");

		// TODO: Mit currencyIsoCode und date sollte hier der Kurs ermittelt und
		// ausgegeben werden.
	}

	private void enterIataExchangeRate() throws Exception {
		String currencyIsoCode = getUserInputForStringField("Währung");
		Date from = getUserInputForDateField("Von");
		Date to = getUserInputForDateField("Bis");
		Double exchangeRate = getUserInputForDoubleField("Euro-Kurs für 1 " + currencyIsoCode);

		// TODO: Aus den Variablen muss jetzt ein Kurs zusammengesetzt und in
		// die eingelesenen Kurse eingefügt werden.
	}

	private String getUserInputForStringField(String fieldName) throws Exception {
		System.out.print(fieldName + ": ");
		return getUserInput();
	}

	private Date getUserInputForDateField(String fieldName) throws Exception {
		System.out.print(fieldName + " (tt.mm.jjjj): ");
		String dateString = getUserInput();

		DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
		return dateFormat.parse(dateString);
	}

	private Double getUserInputForDoubleField(String fieldName) throws Exception {
		String doubleString = getUserInputForStringField(fieldName);
		return Double.valueOf(doubleString);
	}
}
