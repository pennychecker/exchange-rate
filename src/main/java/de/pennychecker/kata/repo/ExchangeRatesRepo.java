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
import de.pennychecker.kata.model.ExchangeRatesWrapper;

public class ExchangeRatesRepo implements IExchangeRatesRepo {

	@Inject
	private IExchangeRatesDao exchangeratesDao;

	public Map<String, ExchangeRatesWrapper> find() throws IOException, ParseException {
		final Map<String, ExchangeRatesWrapper> exchangeRates = this.exchangeratesDao.findAll();
		return Collections.unmodifiableMap(exchangeRates);
	}

	public ExchangeRate find(String iso, Date currencyIsoDate) throws IOException, ParseException {
		final String currencyIsoCode = iso.toUpperCase();

		return exist(currencyIsoCode, currencyIsoDate) ? findExchangeRate(currencyIsoCode, currencyIsoDate) : null;
	}

	public boolean exist(String currencyIsoCode, Date exchangeRateDate) throws IOException, ParseException {
		final Map<String, ExchangeRatesWrapper> exchangeRates = this.exchangeratesDao.findAll();
		final ExchangeRatesWrapper currencyIsoExchangeRates = exchangeRates.get(currencyIsoCode);
		return exchangeRates.containsKey(currencyIsoCode) && exchangeRatesContains(exchangeRateDate, currencyIsoExchangeRates);
	}

	private boolean exchangeRatesContains(Date currencyIsoDate, final ExchangeRatesWrapper currencyIsoExchangeRates) {
		return currencyIsoExchangeRates.entries().getEntry(new DateTime(currencyIsoDate.getTime())) != null;
	}

	private ExchangeRate findExchangeRate(String iso, Date exchangeRateDate) throws IOException, ParseException {
		final String currencyIsoCode = iso.toUpperCase();
		final Map<String, ExchangeRatesWrapper> exchangeRates = this.exchangeratesDao.findAll();
		final ExchangeRatesWrapper countryExchangeRates = exchangeRates.get(currencyIsoCode);
		final Entry<Range<DateTime>, Double> entry = countryExchangeRates.entries().getEntry(new DateTime(exchangeRateDate.getTime()));
		final ExchangeRate rate = createExchangeRate(currencyIsoCode, entry);
		return rate;
	}

	private ExchangeRate createExchangeRate(final String currencyIsoCode, final Entry<Range<DateTime>, Double> entry) {
		final ExchangeRate rate = new ExchangeRate();
		rate.setAmount(entry.getValue());
		rate.setBegin(new DateTime(entry.getKey().lowerEndpoint()));
		rate.setEnd(new DateTime(entry.getKey().upperEndpoint()));
		rate.setCurrencyIso(currencyIsoCode);
		return rate;
	}

	public void add(String currencyIsoCode, Date from, Date to, double exchangeRate) {
		this.exchangeratesDao.add(currencyIsoCode, new DateTime(from), new DateTime(to), exchangeRate);
	}

}
