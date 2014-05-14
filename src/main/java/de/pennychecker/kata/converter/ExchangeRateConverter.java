package de.pennychecker.kata.converter;

import java.text.DecimalFormat;

import de.pennychecker.kata.model.ExchangeRate;

public class ExchangeRateConverter implements Converter<ExchangeRate, String> {

	public String convert(ExchangeRate rate) {
		final String amount = new DecimalFormat("#.###").format(1 / rate.getAmount());
		return "1 " + rate.getCurrencyIso() + " entspricht " + amount + " Euro";
	}
}
