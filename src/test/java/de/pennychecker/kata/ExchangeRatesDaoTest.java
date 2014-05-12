package de.pennychecker.kata;

import java.io.IOException;
import java.text.ParseException;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Range;
import com.google.common.collect.Table;

public class ExchangeRatesDaoTest {

	@Test
	public void testConvert() throws IOException, ParseException {
		Table<Country, Double, Range<Long>> exchangeRates = new ExchangeRatesDao().find();
		Assert.assertEquals(9, exchangeRates.size());
	}

}
