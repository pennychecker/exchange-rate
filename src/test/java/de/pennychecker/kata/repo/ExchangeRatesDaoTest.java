package de.pennychecker.kata.repo;

import java.io.IOException;
import java.text.ParseException;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Range;
import com.google.common.collect.Table;

public class ExchangeRatesDaoTest {

	@Test
	public void testConvert() throws IOException, ParseException {
		Table<String, Range<Long>, Double> exchangeRates = new ExchangeRatesDao().find();
		Assert.assertEquals(9, exchangeRates.size());
	}

}
