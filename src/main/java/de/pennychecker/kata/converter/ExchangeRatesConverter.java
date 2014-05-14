package de.pennychecker.kata.converter;

import java.util.Map;

import org.joda.time.DateTime;

import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import com.google.common.collect.Table;

import de.pennychecker.kata.model.ExchangeRatesWrapper;

public class ExchangeRatesConverter implements
		Converter<Table<String, Range<DateTime>, Double>, Map<String, ExchangeRatesWrapper>> {

	public Map<String, ExchangeRatesWrapper> convert(Table<String, Range<DateTime>, Double> exchangeRates) {
		return merge(exchangeRates);
	}

	private Map<String, ExchangeRatesWrapper> merge(Table<String, Range<DateTime>, Double> csvExchangeRates) {
		final Map<String, ExchangeRatesWrapper> countryExchangeRates = Maps.newHashMap();
		for (String country : csvExchangeRates.rowMap().keySet()) {
			final ExchangeRatesWrapper exchangeRates = new ExchangeRatesWrapper();
			countryExchangeRates.put(country, exchangeRates);
			for (Range<DateTime> longR : csvExchangeRates.row(country).keySet()) {
				final Double amount = csvExchangeRates.get(country, longR);
				exchangeRates.insert(longR, amount);
			}
		}
		
		return countryExchangeRates;
	}

}
