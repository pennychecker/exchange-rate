package de.pennychecker.kata.repo;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

import de.pennychecker.kata.model.ExchangeRate;
import de.pennychecker.kata.model.ExchangeRates;

public interface IExchangeRatesRepo {
	public Map<String, ExchangeRates> find() throws IOException, ParseException;

	public ExchangeRate find(String currencyIsoCode, Date date) throws IOException, ParseException;

	public boolean exist(String isoCountry, Date date) throws IOException, ParseException;

	public void add(String currencyIsoCode, Date from, Date to, double exchangeRate);
}
