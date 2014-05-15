package de.pennychecker.kata.repo;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Maps;
import com.google.common.collect.Range;
import com.google.inject.Guice;
import com.google.inject.Inject;

import de.pennychecker.kata.assembler.Assembler;
import de.pennychecker.kata.model.ExchangeRate;
import de.pennychecker.kata.model.ExchangeRatesWrapper;

public class ExchangeRatesDaoTest {

	@Inject
	private IExchangeRatesDao dao = new ExchangeRatesDao();

	@Before
	public void setup() {
		this.dao = Guice.createInjector(new Assembler()).getInstance(IExchangeRatesDao.class);
	}

	@Test
	public void testFindAll() throws IOException, ParseException {
		final Map<String, ExchangeRatesWrapper> actual = dao.findAll();
		Assert.assertEquals(4, actual.size());
	}

	@Test
	public void testAddNew() throws IOException, ParseException {
		final DateTime from = new DateTime(2010, 1, 1, 0, 0);
		final DateTime to = new DateTime(2011, 1, 1, 0, 0);
		dao.add("aaa", from, to, 2d);

		final Map<String, ExchangeRatesWrapper> exchangeRates = dao.findAll();
		final ExchangeRatesWrapper actual = exchangeRates.get("AAA");
		final ExchangeRatesWrapper expected = new ExchangeRatesWrapper(from, to, 2d);
		Assert.assertEquals(expected, actual);
	}

	// USD // 01.01.2010 - 10.01.2010 | 11.01.2010 - 20.01.2010 | 01.03.2010 - 02.05.2010

	//1. 10.01.2010 - 11.01.2010
	//2. 01.01.2010 - 10.01.2010
	//3. 22.02.2010 - 25.02.2010
	//4. 05.01.2010 - 01.05.2010

	@Test
	public void test1() throws ParseException, IOException {
		final String country = add("BBB", "01.01.2010-10.01.2010", "11.01.2010-20.01.2010", "01.03.2010-02.05.2010");
		final Map<String, ExchangeRatesWrapper> actual = insert(country, "10.01.2010-11.01.2010");
		final Map<String, ExchangeRatesWrapper> expected = create(country, "01.01.2010-09.01.2010","10.01.2010-11.01.2010", "12.01.2010-20.01.2010",
				"01.03.2010-02.05.2010");
		Assert.assertEquals(expected, actual);
	}

	private Map<String, ExchangeRatesWrapper> create(String currencyIsoCode,String... exchangeRatePeriods) throws ParseException {
		final Map<String, ExchangeRatesWrapper> exchangeRates = Maps.newHashMap();
		final ExchangeRatesWrapper wrapper = new ExchangeRatesWrapper();
		for (String exchangeRatePeriod : exchangeRatePeriods) {
			final ExchangeRate exchangeRate = extract(exchangeRatePeriod);
			wrapper.insert(Range.openClosed(exchangeRate.getBegin(), exchangeRate.getEnd()), 1d);
		}
		exchangeRates.put(currencyIsoCode, wrapper);
		return exchangeRates;
	}

	private Map<String, ExchangeRatesWrapper> insert(String currencyIsoCode, String exchangeRatePeriod)
			throws ParseException, IOException {
		final ExchangeRate exchangeRate = extract(exchangeRatePeriod);
		dao.add(currencyIsoCode, exchangeRate.getBegin(), exchangeRate.getEnd(), 1d);
		return dao.findAll();
	}

	private String add(String currencyIsoCode, String... exchangeRatePeriods) throws ParseException {
		for (String exchangeRatePeriod : exchangeRatePeriods) {
			final ExchangeRate exchangeRate = extract(exchangeRatePeriod);
			dao.add(currencyIsoCode, exchangeRate.getBegin(), exchangeRate.getEnd(), 1d);
		}
		return currencyIsoCode;
	}

	private ExchangeRate extract(String exchangeRatePeriod) throws ParseException {
		final String[] splittedExchangeRatePeriod = exchangeRatePeriod.split("-");
		final DateTime from = new DateTime(CsvExchangeRateLoader.SDF.parse(splittedExchangeRatePeriod[0]));
		final DateTime to = new DateTime(CsvExchangeRateLoader.SDF.parse(splittedExchangeRatePeriod[1]));
		ExchangeRate rate = new ExchangeRate();
		rate.setBegin(from);
		rate.setEnd(to);

		return rate;
	}

}
