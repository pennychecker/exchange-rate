package de.pennychecker.kata.converter;

import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.Table;
import com.google.common.collect.TreeRangeMap;

import de.pennychecker.kata.model.ExchangeRatesWrapper;

public class ExchangeRatesConverterTest {

	private Map<String, ExchangeRatesWrapper> exchangeRates;
	private final static String GERMANY = "DE";
	private final static Range<DateTime> GERMANY_1 = Range.closedOpen(new DateTime(2010,1,1,0,0), new DateTime(2010,1,10,0,0));
	private final static Range<DateTime> GERMANY_2 = Range.closedOpen(new DateTime(2010,1,10,0,0), new DateTime(2010,1,12,0,0));

	@Before
	public void setup() {
		final Table<String, Range<DateTime>, Double> rates = HashBasedTable.create();
		rates.put(GERMANY, GERMANY_1, 2d);
		rates.put(GERMANY, GERMANY_2, 2d);
		rates.put("AT",Range.closedOpen(new DateTime(2010,1,5,0,0), new DateTime(2010,1,11,0,0)), 3d);

		final Converter<Table<String, Range<DateTime>, Double>, Map<String, ExchangeRatesWrapper>> merger = new ExchangeRatesConverter();
		this.exchangeRates = merger.convert(rates);
	}

	@Test
	public void testSize() {
		Assert.assertEquals(2, exchangeRates.size());
	}

	@Test
	public void testGermanyRanges() {
		final ExchangeRatesWrapper germanExchangeRates = exchangeRates.get(GERMANY);
		final Map<Range<DateTime>, Double> actual = germanExchangeRates.entries().asMapOfRanges();

		final RangeMap<DateTime, Double> expected = TreeRangeMap.create();
		expected.put(GERMANY_1, 2d);
		expected.put(GERMANY_2, 2d);


		Assert.assertEquals(expected.asMapOfRanges(), actual);
	}
}
