package de.pennychecker.kata.assembler;

import java.util.Map;

import org.joda.time.DateTime;

import com.google.common.collect.Range;
import com.google.common.collect.Table;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;

import de.pennychecker.kata.converter.Converter;
import de.pennychecker.kata.converter.ExchangeRatesConverter;
import de.pennychecker.kata.model.ExchangeRatesWrapper;
import de.pennychecker.kata.repo.CsvExchangeRateLoader;
import de.pennychecker.kata.repo.ExchangeRateLoader;
import de.pennychecker.kata.repo.ExchangeRatesDao;
import de.pennychecker.kata.repo.ExchangeRatesRepo;
import de.pennychecker.kata.repo.IExchangeRatesDao;
import de.pennychecker.kata.repo.IExchangeRatesRepo;

public class Assembler extends AbstractModule {

	@Override
	protected void configure() {
		bind(ExchangeRateLoader.class).to(CsvExchangeRateLoader.class);
		bind(IExchangeRatesDao.class).to(ExchangeRatesDao.class);
		bind(IExchangeRatesRepo.class).to(ExchangeRatesRepo.class);
		bind(new TypeLiteral<Converter<Table<String, Range<DateTime>, Double>, Map<String, ExchangeRatesWrapper>>>() {
		}).to(ExchangeRatesConverter.class);

	}

}
