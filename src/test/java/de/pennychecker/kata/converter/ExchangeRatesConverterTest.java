package de.pennychecker.kata.converter;

import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.Table;
import com.google.common.collect.TreeRangeMap;

import de.pennychecker.kata.model.ExchangeRates;

public class ExchangeRatesConverterTest {

	private Map<String, ExchangeRates> exchangeRates;
	private final static String GERMANY = "DE";
	private final static Range<Long> GERMANY_1 = Range.closedOpen(1l, 4l);
	private final static Range<Long> GERMANY_2 = Range.closedOpen(5l, 8l);

	@Before
	public void setup() {
		final Table<String, Range<Long>, Double> rates = HashBasedTable.create();
		rates.put(GERMANY, GERMANY_1, 2d);
		rates.put(GERMANY, GERMANY_2, 2d);
		rates.put("AT", Range.closedOpen(5l, 8l), 3d);

		final Converter<Table<String, Range<Long>, Double>, Map<String, ExchangeRates>> merger = new ExchangeRatesConverter();
		this.exchangeRates = merger.convert(rates);
	}

	@Test
	public void testSize() {
		Assert.assertEquals(2, exchangeRates.size());
	}

	@Test
	public void testGermanyRanges() {
		final ExchangeRates germanExchangeRates = exchangeRates.get(GERMANY);
		final Map<Range<Long>, Double> actual = germanExchangeRates.entries().asMapOfRanges();

		final RangeMap<Long, Double> expected = TreeRangeMap.create();
		expected.put(GERMANY_1, 2d);
		expected.put(GERMANY_2, 2d);
<<<<<<< HEAD
	
		Assert.assertEquals(exchangeRates.get(GERMANY),actual);
	}
=======
>>>>>>> 2826153c65276d051813b22e0c46a67cfd356be7

		Assert.assertEquals(expected.asMapOfRanges(), actual);
	}
}
