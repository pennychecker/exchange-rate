package de.pennychecker.kata.repo;

import java.io.IOException;
import java.text.ParseException;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Range;
import com.google.common.collect.Table;

public class CsvExchangeRateLoaderTest {

	private ExchangeRateLoader csvExchangeRatesLoader = new CsvExchangeRateLoader();

	@Test
	public void testFind() throws IOException, ParseException {
		final Table<String, Range<DateTime>, Double> exchangeRates = csvExchangeRatesLoader.load();
		Assert.assertEquals(9, exchangeRates.size());
	}

}
