package de.pennychecker.kata.converter;

import java.util.Map;

import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import com.google.common.collect.Table;

import de.pennychecker.kata.model.ExchangeRates;

public class ExchangeRatesConverter implements
		Converter<Table<String, Range<Long>, Double>, Map<String, ExchangeRates>> {

	public Map<String, ExchangeRates> convert(Table<String, Range<Long>, Double> exchangeRates) {
		return merge(exchangeRates);
	}

	private Map<String, ExchangeRates> merge(Table<String, Range<Long>, Double> csvExchangeRates) {
		final Map<String, ExchangeRates> countryExchangeRates = Maps.newHashMap();
		for (String country : csvExchangeRates.rowMap().keySet()) {
			final ExchangeRates exchangeRates = new ExchangeRates();
			countryExchangeRates.put(country, exchangeRates);
			for (Range<Long> longR : csvExchangeRates.row(country).keySet()) {
				final Double amount = csvExchangeRates.get(country, longR);
				exchangeRates.put(longR, amount);
			}
		}
		
		return countryExchangeRates;
	}

}
