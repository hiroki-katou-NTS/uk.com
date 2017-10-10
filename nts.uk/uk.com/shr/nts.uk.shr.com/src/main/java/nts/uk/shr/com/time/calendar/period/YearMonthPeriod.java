package nts.uk.shr.com.time.calendar.period;

import nts.arc.time.YearMonth;

public class YearMonthPeriod extends GeneralPeriod<YearMonthPeriod, YearMonth> {
	
	private static final YearMonth MAX = YearMonth.of(9999, 12);

	public YearMonthPeriod(YearMonth start, YearMonth end) {
		super(start, end);
	}

	@Override
	public YearMonthPeriod newSpan(YearMonth newStart, YearMonth newEnd) {
		return new YearMonthPeriod(newStart, newEnd);
	}

	@Override
	protected YearMonth max() {
		return MAX;
	}

}
