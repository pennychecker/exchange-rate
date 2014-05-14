package de.pennychecker.kata.repo;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import org.joda.time.DateTime;

import de.pennychecker.kata.model.ExchangeRates;

public interface IExchangeRatesDao {


	public abstract void add(String currencyIsoCode, DateTime from, DateTime to, double exchangeRate);
	
	public Map<String, ExchangeRates> find() throws IOException, ParseException;

}