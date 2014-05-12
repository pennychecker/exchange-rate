package de.pennychecker.kata;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import au.com.bytecode.opencsv.CSVReader;

import com.google.common.base.Charsets;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Range;
import com.google.common.collect.Table;

public class ExchangeRatesDao {

	public final static String FILE_NAME = "KursExport.csv";

	public ExchangeRatesDao() {

	}

	public Table<Country, Double, Range<Long>> find() throws IOException, ParseException {
		final Table<Country, Double, Range<Long>> rates = HashBasedTable.create();

		for (String[] row : getExchangeRatesCsv().readAll()) {
			final Country country = new Country(row[0], row[2]);
			final Double exchangeRate = new Double(new DecimalFormat("#.##").parse(row[1]).doubleValue());
			final long lower = new SimpleDateFormat("dd.MM.yyyy").parse(row[3]).getTime();
			final long upper = new SimpleDateFormat("dd.MM.yyyy").parse(row[4]).getTime();
			final Range<Long> range = Range.closedOpen(lower, upper);
			rates.put(country, exchangeRate, range);
		}
		return rates;
	}

	/**
	 * @throws NullpointerException
	 *           if file does not exist
	 * @return
	 */
	private CSVReader getExchangeRatesCsv() {
		final InputStream resourceAsStream = ExchangeRatesDao.class.getResourceAsStream(FILE_NAME);
		final InputStreamReader inputStreamReader = new InputStreamReader(resourceAsStream, Charsets.ISO_8859_1);
		return new CSVReader(inputStreamReader, ';');
	}

}
