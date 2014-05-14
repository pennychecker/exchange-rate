package de.pennychecker.kata.repo;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.Map;

import org.joda.time.DateTime;

import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import com.google.common.collect.Table;
import com.google.inject.Inject;

import de.pennychecker.kata.converter.Converter;
import de.pennychecker.kata.model.ExchangeRatesWrapper;

public class ExchangeRatesDao implements IExchangeRatesDao {

	private final Map<String, ExchangeRatesWrapper> exchangeRates = Maps.newHashMap();

	@Inject
	private Converter<Table<String, Range<DateTime>, Double>, Map<String, ExchangeRatesWrapper>> csvExchangeRatesConverter;

	@Inject
	ExchangeRateLoader csvLoader;

	public Map<String, ExchangeRatesWrapper> findAll() throws IOException, ParseException {
		if (exchangeRates.isEmpty()) {
			final Table<String, Range<DateTime>, Double> csvExchangerates = csvLoader.load();
			this.exchangeRates.putAll(csvExchangeRatesConverter.convert(csvExchangerates));
		}

		return Collections.unmodifiableMap(exchangeRates);
	}

	public void add(String currencyIsoCode, DateTime from, DateTime to, double exchangeRate) {
		final String iso = currencyIsoCode.toUpperCase();
		final ExchangeRatesWrapper currencyExchangeRate;
		if (exchangeRates.containsKey(iso)) {
			currencyExchangeRate = exchangeRates.get(iso);
		} else {
			currencyExchangeRate = new ExchangeRatesWrapper();
			exchangeRates.put(iso, currencyExchangeRate);
		}
		// ClosedOpen contains all values greater than or equal to lower and
		// strictly less than upper.
		currencyExchangeRate.insert(Range.closedOpen(from, to.plusDays(1)), exchangeRate);
	}

}
