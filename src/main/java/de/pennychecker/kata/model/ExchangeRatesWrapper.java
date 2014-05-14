package de.pennychecker.kata.model;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableRangeMap;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;

public class ExchangeRatesWrapper {
	private final RangeMap<DateTime, Double> exchangeRates = TreeRangeMap.create();

	public ExchangeRatesWrapper(RangeMap<DateTime, Double> entries) {
		this.exchangeRates.putAll(entries);
	}

	public ExchangeRatesWrapper() {
	}

	public ExchangeRatesWrapper(DateTime from, DateTime to, double exchangeRateAmount) {
		exchangeRates.put(Range.closedOpen(from, to.plusDays(1)), exchangeRateAmount);
	}

	public void insert(Range<DateTime> entry, Double exchangeRate) {
		exchangeRates.put(entry, exchangeRate);
	}

	public RangeMap<DateTime, Double> entries() {
		return ImmutableRangeMap.copyOf(exchangeRates);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((exchangeRates == null) ? 0 : exchangeRates.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ExchangeRatesWrapper other = (ExchangeRatesWrapper) obj;
		if (exchangeRates == null) {
			if (other.exchangeRates != null)
				return false;
		} else if (!exchangeRates.equals(other.exchangeRates))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ExchangeRatesWrapper [exchangeRates=" + exchangeRates + "]";
	}

}
