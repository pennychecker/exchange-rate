package de.pennychecker.kata.repo;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import com.google.inject.Guice;
import com.google.inject.Injector;

import de.pennychecker.kata.assembler.TestAssembler;
import de.pennychecker.kata.model.ExchangeRate;
import de.pennychecker.kata.model.ExchangeRatesWrapper;

public class ExchangeRatesRepoTest {

	private IExchangeRatesRepo repo;

	private DateTime rangeLower1 = new DateTime(2014, 2, 1, 0, 0);
	private DateTime rangeUpper1 = new DateTime(2014, 2, 4, 0, 0);

	private final static String currencyIso = "DE";

	@Before
	public void setup() throws IOException, ParseException {
		final Injector injector = Guice.createInjector(new TestAssembler());
		this.repo = injector.getInstance(IExchangeRatesRepo.class);

		final DateTime rangeLower = new DateTime(2013, 2, 1, 0, 0);
		final DateTime rangeUpper = new DateTime(2013, 2, 4, 0, 0);
		final DateTime rangeLower2 = new DateTime(2014, 2, 10, 0, 0);
		final DateTime rangeUpper2 = new DateTime(2014, 2, 20, 0, 0);

		final RangeMap<DateTime, Double> entries = TreeRangeMap.create();
		entries.put(Range.closedOpen(rangeLower, rangeUpper), 5d);
		entries.put(Range.closedOpen(rangeLower1, rangeUpper1), 1d);
		entries.put(Range.closedOpen(rangeLower2, rangeUpper2), 2d);

		final Map<String, ExchangeRatesWrapper> mockedExchangeRates = Maps.newHashMap();
		mockedExchangeRates.put(currencyIso, new ExchangeRatesWrapper(entries));

		Mockito.when(this.repo.find()).thenReturn(mockedExchangeRates);

	}

	@Test
	public void testAmount() throws IOException, ParseException {
		final ExchangeRate rate = getRate();
		Assert.assertEquals(Double.valueOf(1d), rate.getAmount());
	}

	@Test
	public void testBegin() throws IOException, ParseException {
		final ExchangeRate rate = getRate();
		Assert.assertEquals(rangeLower1, rate.getBegin());
	}

	@Test
	public void testEnd() throws IOException, ParseException {
		final ExchangeRate rate = getRate();
		Assert.assertEquals(rangeUpper1, rate.getEnd());
	}

	@Test
	public void testCountry() throws IOException, ParseException {
		final ExchangeRate rate = getRate();
		Assert.assertEquals(currencyIso, rate.getCurrencyIso());
	}

	private ExchangeRate getRate() throws IOException, ParseException {
		return repo.find("DE", new DateTime(2014, 2, 3, 0, 0).toDate());
	}
}
