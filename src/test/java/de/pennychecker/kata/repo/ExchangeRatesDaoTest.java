package de.pennychecker.kata.repo;

import java.io.IOException;
import java.text.ParseException;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Inject;

import de.pennychecker.kata.assembler.Assembler;
import de.pennychecker.kata.model.ExchangeRates;

public class ExchangeRatesDaoTest {
	
	@Inject
	private IExchangeRatesDao dao = new ExchangeRatesDao();
	
	@Before
	public void setup() {
		this.dao = Guice.createInjector(new Assembler()).getInstance(IExchangeRatesDao.class);
	}

	@Test
	public void testFind() throws IOException, ParseException {
		final Map<String, ExchangeRates> actual = dao.find();
		Assert.assertEquals(4, actual.size());
	}

}
