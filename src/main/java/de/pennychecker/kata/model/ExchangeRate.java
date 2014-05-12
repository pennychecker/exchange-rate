package de.pennychecker.kata.model;

import java.util.Date;

public class ExchangeRate {
	private String currencyIso;
	private Date begin;
	private Date end;
	private Double amount;

	public String getCurrencyIso() {
		return currencyIso;
	}

	public void setCurrencyIso(String currencyIso) {
		this.currencyIso = currencyIso;
	}

	public Date getBegin() {
		if (null != begin) {
			return new Date(begin.getTime());
		}
		return null;
	}

	public void setBegin(Date begin) {
		if (null != begin) {
			this.begin = new Date(begin.getTime());
		} else {
			this.begin = null;
		}
	}

	public Date getEnd() {
		if (null != end) {
			return new Date(end.getTime());
		}
		return null;
	}

	public void setEnd(Date end) {
		if (null != end) {
			this.end = new Date(end.getTime());
		} else {
			this.end = null;
		}

	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
