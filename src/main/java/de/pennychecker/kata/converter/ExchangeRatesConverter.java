package de.pennychecker.kata.converter;

import java.util.Map;

import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import com.google.common.collect.Table;

import de.pennychecker.kata.Country;
import de.pennychecker.kata.ExchangeRates;

public class ExchangeRatesConverter implements
		Converter<Table<Country, Range<Long>, Double>, Map<Country, ExchangeRates>> {

	public Map<Country, ExchangeRates> convert(Table<Country, Range<Long>, Double> exchangeRates) {
		return merge(exchangeRates);
	}

	private Map<Country, ExchangeRates> merge(Table<Country, Range<Long>, Double> csvExchangeRates) {
		final Map<Country, ExchangeRates> countryExchangeRates = Maps.newHashMap();
		for (Country country : csvExchangeRates.rowMap().keySet()) {
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
