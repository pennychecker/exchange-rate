package de.pennychecker.kata;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableRangeMap;
import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;

import de.pennychecker.kata.model.ExchangeRates;

public class PeriodTest {

	private ExchangeRates exchangeRates;

	@Before
	public void setup() {
		final RangeMap<Long, Double> rangeSet = create("1-8", "13-19", "19-30", "37-66", "66-69", "69-85");
		exchangeRates = new ExchangeRates(rangeSet);
	}

	@Test
	public void insertWithoutOverlap() {
		final RangeMap<Long, Double> expectedRanges = create("1-8", "9-12", "13-19", "19-30", "37-66", "66-69", "69-85");
		final Range<Long> newRange = Range.closedOpen(9l, 12l);

		assertRanges(expectedRanges, newRange);
	}

	@Test
	public void insertRangeWithExactLowerBoundOverlap() {
		final RangeMap<Long, Double> expectedRanges = create("1-8", "8-12", "13-19", "19-30", "37-66", "66-69", "69-85");
		final Range<Long> newRange = Range.closedOpen(8l, 12l);

		assertRanges(expectedRanges, newRange);
	}

	@Test
	public void insertRangeWithExactUpperBoundOverlap() {
		final RangeMap<Long, Double> expectedRanges = create("1-8", "9-13", "13-19", "19-30", "37-66", "66-69", "69-85");
		final Range<Long> newRange = Range.closedOpen(9l, 13l);

		assertRanges(expectedRanges, newRange);
	}

	@Test
	public void insertRangeWithExactUpperLowerBoundOverlap() {
		final RangeMap<Long, Double> expectedRanges = create("1-8", "8-13", "13-19", "19-30", "37-66", "66-69", "69-85");
		final Range<Long> newRange = Range.closedOpen(8l, 13l);

		assertRanges(expectedRanges, newRange);
	}

	@Test
	public void overlapLowerAndUpper() {
		final RangeMap<Long, Double> expectedRanges = create("1-6", "6-10", "13-19", "19-30", "37-66", "66-69", "69-85");
		final Range<Long> newRange = Range.closedOpen(6l, 10l);

		assertRanges(expectedRanges, newRange);
	}

	@Test
	public void overlapOverRange() {
		final RangeMap<Long, Double> expectedRanges = create("1-8", "6-15", "15-19", "19-30", "37-66", "66-69", "69-85");
		final Range<Long> newRange = Range.closedOpen(6l, 15l);

		assertRanges(expectedRanges, newRange);
	}

	@Test
	public void overlapUpperAndLowerBound() {
		final RangeMap<Long, Double> expectedRanges = create("1-3", "3-80", "80-85");
		final Range<Long> newRange = Range.closedOpen(3l, 80l);

		assertRanges(expectedRanges, newRange);
	}

	private void assertRanges(final RangeMap<Long, Double> expectedRanges, final Range<Long> newRange) {
		exchangeRates.insert(newRange, 1d);
		final RangeMap<Long, Double> actualRanges = exchangeRates.entries();
		Assert.assertEquals(expectedRanges, actualRanges);
	}

	/**
	 * Helper method to create a RangeMap.
	 * 
	 * @param ranges
	 *            should have the pattern e.g. "1-6"
	 * @return RangeSet<Long>
	 */
	private RangeMap<Long, Double> create(String... ranges) {
		final TreeRangeMap<Long, Double> rangeSet = TreeRangeMap.create();
		for (String range : ranges) {
			final String[] closedRange = range.split("-");
			if (closedRange.length < 2) {
				throw new IllegalArgumentException("Wrong range format: " + range);
			}
			final Long closedLower = Long.valueOf(closedRange[0]);
			final Long closedUpper = Long.valueOf(closedRange[1]);
			rangeSet.put(Range.closedOpen(closedLower, closedUpper), 1d);
		}
		return ImmutableRangeMap.copyOf(rangeSet);
	}
}
