package de.pennychecker.kata.repo;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import org.joda.time.DateTime;

import com.google.common.collect.Range;
import com.google.inject.Inject;

import de.pennychecker.kata.model.ExchangeRate;
import de.pennychecker.kata.model.ExchangeRates;

public class ExchangeRatesRepo implements IExchangeRatesRepo {

	@Inject
	private IExchangeRatesDao dao;

	public Map<String, ExchangeRates> find() throws IOException, ParseException {
		final Map<String, ExchangeRates> exchangeRates = this.dao.find();
		return Collections.unmodifiableMap(exchangeRates);
	}

	public ExchangeRate find(String isoCountry, Date date) throws IOException, ParseException {
		return exist(isoCountry, date) ? findRate(isoCountry, date) : null;
	}

	public boolean exist(String isoCountry, Date date) throws IOException, ParseException {
		final Map<String, ExchangeRates> exchangeRates = this.dao.find();
		final String country = new String(isoCountry);
		final ExchangeRates countryExchangeRates = exchangeRates.get(isoCountry);
		return exchangeRates.containsKey(country) && countryExchangeRates.entries().getEntry(date.getTime()) != null;
	}

	private ExchangeRate findRate(String isoCountry, Date date) throws IOException, ParseException {
		final Map<String, ExchangeRates> exchangeRates = this.dao.find();
		final String currencyIso = new String(isoCountry);
		final ExchangeRates countryExchangeRates = exchangeRates.get(currencyIso);
		final Entry<Range<Long>, Double> entry = countryExchangeRates.entries().getEntry(date.getTime());
		final ExchangeRate rate = new ExchangeRate();
		rate.setAmount(entry.getValue());
		rate.setBegin(new Date(entry.getKey().lowerEndpoint()));
		rate.setEnd(new Date(entry.getKey().upperEndpoint()));
		rate.setCurrencyIso(currencyIso);
		return rate;
	}

	public void add(String currencyIsoCode, Date from, Date to, double exchangeRate) {
		this.dao.add(currencyIsoCode,new DateTime(from), new DateTime(to), exchangeRate);
	}

}
