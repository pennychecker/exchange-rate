package de.pennychecker.kata.repo;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Map;

import org.joda.time.DateTime;

import au.com.bytecode.opencsv.CSVReader;

import com.google.common.base.Charsets;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import com.google.common.collect.Table;
import com.google.inject.Inject;

import de.pennychecker.kata.converter.Converter;
import de.pennychecker.kata.model.ExchangeRates;

public class ExchangeRatesDao implements IExchangeRatesDao {

	private static final SimpleDateFormat SDF = new SimpleDateFormat("dd.MM.yyyy");
	public final static String FILE_NAME = "KursExport.csv";

	private final Map<String, ExchangeRates> exchangeRates = Maps.newHashMap();

	@Inject
	private Converter<Table<String, Range<Long>, Double>, Map<String, ExchangeRates>> converter;

	public ExchangeRatesDao() {

	}

	public Map<String, ExchangeRates> find() throws IOException, ParseException {
		final Table<String, Range<Long>, Double> csvExchangeRates = fetchExchangeRatesFromCsv();
		if (exchangeRates.isEmpty()) {
			this.exchangeRates.putAll(converter.convert(csvExchangeRates));
		}

		return Collections.unmodifiableMap(exchangeRates);
	}

	public void add(String currencyIsoCode, DateTime from, DateTime to, double exchangeRate) {
		final String iso = currencyIsoCode.toUpperCase();
		if (exchangeRates.containsKey(iso)) {
			final ExchangeRates currencyExchangeRate = exchangeRates.get(iso);
			// ClosedOpen contains all values greater than or equal to lower and
			// strictly less than upper.
			currencyExchangeRate.insert(Range.closedOpen(from.getMillis(), to.plusDays(1).getMillis()), exchangeRate);
		} else {
			final ExchangeRates currencyExchangeRate = new ExchangeRates();
			currencyExchangeRate.insert(Range.closedOpen(from.getMillis(), to.plusDays(1).getMillis()), exchangeRate);
			exchangeRates.put(iso, currencyExchangeRate);
		}
	}

	private Table<String, Range<Long>, Double> fetchExchangeRatesFromCsv() throws IOException, ParseException {
		final Table<String, Range<Long>, Double> rates = HashBasedTable.create();
		for (String[] row : getExchangeRatesCsv().readAll()) {
			final String currencyIso = row[2];
			final Double exchangeRate = new Double(new DecimalFormat("#.##").parse(row[1]).doubleValue());
			final long lower = SDF.parse(row[3]).getTime();
			final DateTime upperDate = new DateTime(SDF.parse(row[4]).getTime());
			// ClosedOpen contains all values greater than or equal to lower and
			// strictly less than upper.
			final long upper = upperDate.plusDays(1).getMillis();
			final Range<Long> range = Range.closedOpen(lower, upper);
			rates.put(currencyIso, range, exchangeRate);
		}
		return rates;
	}

	/**
	 * @throws NullpointerException
	 *             if file does not exist
	 * @return
	 */
	private CSVReader getExchangeRatesCsv() {
		final InputStream resourceAsStream = ExchangeRatesDao.class.getResourceAsStream(FILE_NAME);
		final InputStreamReader inputStreamReader = new InputStreamReader(resourceAsStream, Charsets.ISO_8859_1);
		return new CSVReader(inputStreamReader, ';');
	}

}
