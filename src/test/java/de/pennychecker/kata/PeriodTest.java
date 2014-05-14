package de.pennychecker.kata;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Range;
import com.google.common.collect.TreeRangeMap;

public class PeriodTest {

	@Test
	public void test() {

		final DateTime closedLower1 = new DateTime(2010, 1, 1, 0, 0);
		final DateTime closedUpper1 = new DateTime(2010, 1, 10, 0, 0);

		final DateTime closedLower2 = new DateTime(2010, 2, 1, 0, 0);
		final DateTime closedUpper2 = new DateTime(2010, 2, 3, 0, 0);

		final DateTime closedLower3 = new DateTime(2010, 5, 1, 0, 0);
		final DateTime closedUpper3 = new DateTime(2010, 8, 1, 0, 0);

		final TreeRangeMap<DateTime, Double> actual = TreeRangeMap.create();
		actual.put(Range.closedOpen(closedLower1, closedUpper1), 1d);
		actual.put(Range.closedOpen(closedLower2, closedUpper2), 1d);
		actual.put(Range.closedOpen(closedLower3, closedUpper3), 1d);

		actual.put(Range.closedOpen(new DateTime(2010, 1, 5, 0, 0), new DateTime(2010, 7, 20, 0, 0)), 1d);

		final DateTime closedLower11 = new DateTime(2010, 1, 1, 0, 0);
		final DateTime closedUpper11 = new DateTime(2010, 1, 5, 0, 0);

		final DateTime closedLower22 = new DateTime(2010, 1, 5, 0, 0);
		final DateTime closedUpper22 = new DateTime(2010, 7, 20, 0, 0);

		final DateTime closedLower33 = new DateTime(2010, 7, 20, 0, 0);
		final DateTime closedUpper33 = new DateTime(2010, 8, 1, 0, 0);

		final TreeRangeMap<DateTime, Double> expected = TreeRangeMap.create();
		expected.put(Range.closedOpen(closedLower11, closedUpper11), 1d);
		expected.put(Range.closedOpen(closedLower22, closedUpper22), 1d);
		expected.put(Range.closedOpen(closedLower33, closedUpper33), 1d);

		Assert.assertEquals(expected, actual);

	}

}
