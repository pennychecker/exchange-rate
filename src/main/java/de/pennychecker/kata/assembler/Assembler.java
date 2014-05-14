package de.pennychecker.kata.assembler;

import java.util.Map;

import com.google.common.collect.Range;
import com.google.common.collect.Table;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;

import de.pennychecker.kata.converter.Converter;
import de.pennychecker.kata.converter.ExchangeRatesConverter;
import de.pennychecker.kata.model.ExchangeRates;
import de.pennychecker.kata.repo.ExchangeRatesDao;
import de.pennychecker.kata.repo.ExchangeRatesRepo;
import de.pennychecker.kata.repo.IExchangeRatesDao;
import de.pennychecker.kata.repo.IExchangeRatesRepo;

public class Assembler extends AbstractModule{

	@Override
	protected void configure() {
		bind(IExchangeRatesDao.class).to(ExchangeRatesDao.class);
		bind(IExchangeRatesRepo.class).to(ExchangeRatesRepo.class);
		bind(new TypeLiteral<Converter<Table<String, Range<Long>, Double>, Map<String, ExchangeRates>>>(){}).to(ExchangeRatesConverter.class);
		
	}
	
}
