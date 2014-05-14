package de.pennychecker.kata.repo;

import java.io.IOException;
import java.text.ParseException;

import org.joda.time.DateTime;

import com.google.common.collect.Range;
import com.google.common.collect.Table;

public interface ExchangeRateLoader {
	Table<String, Range<DateTime>, Double> load() throws IOException, ParseException;

}
