package de.pennychecker.kata.model;

import org.joda.time.DateTime;

public class ExchangeRate {
	private String currencyIso;
	private DateTime begin;
	private DateTime end;
	private Double amount;

	public String getCurrencyIso() {
		return currencyIso;
	}

	public void setCurrencyIso(String currencyIso) {
		this.currencyIso = currencyIso;
	}

	public DateTime getBegin() {
		return begin;
	}

	public void setBegin(DateTime begin) {
		this.begin = begin;
	}

	public DateTime getEnd() {
		return end;
	}

	public void setEnd(DateTime end) {
		this.end = end;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
