package de.pennychecker.kata.converter;

import org.junit.Assert;
import org.junit.Test;

import de.pennychecker.kata.model.ExchangeRate;

public class ExchangeRateConverterTest {

	@Test
	public void testAmount() {
		final String actual = convert(1.3755, "USD");
		Assert.assertEquals("1 USD entspricht 0,727 Euro", actual);
	}

	@Test
	public void testAmount2() {
		final String actual = convert(140.5331, "JPY");
		Assert.assertEquals("1 JPY entspricht 0,007 Euro", actual);
	}

	private String convert(double d, String isoCode) {
		final ExchangeRate rate = new ExchangeRate();
		rate.setAmount(d);
		rate.setCurrencyIso(isoCode);

		return new ExchangeRateConverter().convert(rate);
	}
}
