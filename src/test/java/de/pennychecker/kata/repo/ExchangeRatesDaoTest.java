package de.pennychecker.kata.repo;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Inject;

import de.pennychecker.kata.assembler.Assembler;
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

	

}
