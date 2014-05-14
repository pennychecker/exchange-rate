package de.pennychecker.kata.repo;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.joda.time.DateTime;

import au.com.bytecode.opencsv.CSVReader;

import com.google.common.base.Charsets;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Range;
import com.google.common.collect.Table;

public class CsvExchangeRateLoader implements ExchangeRateLoader {

	private static final SimpleDateFormat SDF = new SimpleDateFormat("dd.MM.yyyy");
	public final static String FILE_NAME = "KursExport.csv";

	public Table<String, Range<DateTime>, Double> load() throws IOException, ParseException {
		final Table<String, Range<DateTime>, Double> csvExchangeRates = HashBasedTable.create();
		for (String[] row : getExchangeRatesCsv().readAll()) {
			final String currencyIsoCode = row[2];
			final Double exchangeRateAmount = new Double(new DecimalFormat("#.##").parse(row[1]).doubleValue());
			final Range<DateTime> exchangeRatePeriod = parsePeriodfrom(row);
			csvExchangeRates.put(currencyIsoCode, exchangeRatePeriod, exchangeRateAmount);
		}
		return HashBasedTable.create(csvExchangeRates);
	}

	private Range<DateTime> parsePeriodfrom(String[] row) throws ParseException {
		final DateTime from = new DateTime(SDF.parse(row[3]));
		final DateTime upper = new DateTime(SDF.parse(row[4]).getTime());
		// ClosedOpen contains all values greater than or equal to lower and
		// strictly less than upper.
		final DateTime to = upper.plusDays(1);
		final Range<DateTime> range = Range.closedOpen(from, to);
		return range;
	}

	private CSVReader getExchangeRatesCsv() {
		final InputStream resourceAsStream = CsvExchangeRateLoader.class.getResourceAsStream(FILE_NAME);
		final InputStreamReader inputStreamReader = new InputStreamReader(resourceAsStream, Charsets.ISO_8859_1);
		return new CSVReader(inputStreamReader, ';');
	}

}
