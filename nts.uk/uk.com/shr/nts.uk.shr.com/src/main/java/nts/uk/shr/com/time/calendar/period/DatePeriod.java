package nts.uk.shr.com.time.calendar.period;

import nts.arc.time.GeneralDate;

public class DatePeriod extends GeneralPeriod<DatePeriod, GeneralDate> {
	
	private static final GeneralDate MAX = GeneralDate.ymd(9999, 12, 31);

	public DatePeriod(GeneralDate start, GeneralDate end) {
		super(start, end);
	}

	@Override
	public DatePeriod newSpan(GeneralDate newStart, GeneralDate newEnd) {
		return new DatePeriod(newStart, newEnd);
	}

	@Override
	protected GeneralDate max() {
		return MAX;
	}

}
