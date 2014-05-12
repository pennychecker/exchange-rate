package de.pennychecker.kata;

import com.google.common.collect.ImmutableRangeMap;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;

public class ExchangeRates {
	private final RangeMap<Long, Double> exchangeRates = TreeRangeMap.create();

	public ExchangeRates(RangeMap<Long, Double> entries) {
		this.exchangeRates.putAll(entries);
	}
	
	public ExchangeRates() {
	}

	public void insert(Range<Long> entry, Double exchangeRate) {
		exchangeRates.put(entry, exchangeRate);
	}

	public RangeMap<Long, Double> entries() {
		return ImmutableRangeMap.copyOf(exchangeRates);
	}

	public void put(Range<Long> arg0, Double arg1) {
		exchangeRates.put(arg0, arg1);
	}
	
	

}
