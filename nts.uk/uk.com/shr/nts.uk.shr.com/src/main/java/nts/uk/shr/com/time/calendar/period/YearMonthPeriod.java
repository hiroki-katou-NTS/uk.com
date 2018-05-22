package nts.uk.shr.com.time.calendar.period;

import java.util.ArrayList;
import java.util.List;

import nts.arc.time.GeneralDate;
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
	
	@Override
	public List<YearMonth> yearMonthsBetween(){
		List<YearMonth> result = new ArrayList<>();
		YearMonth startYM = this.start();
		while (startYM.lessThanOrEqualTo(this.end())) {
			result.add(startYM);
			startYM = startYM.addMonths(1);
		}
		return result;
	}

	@Override
	public List<GeneralDate> datesBetween(){
		List<GeneralDate> result = new ArrayList<>();
		GeneralDate start = GeneralDate.ymd(this.start().year(), this.start().month(), 1);
		int lastDateOfEndYM = java.time.YearMonth.of(this.end().year(), this.end().month()).lengthOfMonth();
		GeneralDate end = GeneralDate.ymd(this.end().year(), this.end().month(), lastDateOfEndYM);
		while (start.beforeOrEquals(end)) {
			result.add(start);
			start = start.addDays(1);
		}
		return result;
	}
}
