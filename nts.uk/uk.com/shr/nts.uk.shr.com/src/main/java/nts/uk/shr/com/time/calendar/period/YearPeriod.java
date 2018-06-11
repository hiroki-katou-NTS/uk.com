package nts.uk.shr.com.time.calendar.period;

import java.util.ArrayList;
import java.util.List;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.time.calendar.Year;

public class YearPeriod extends GeneralPeriod<YearPeriod, Year> {
	
	private static final Year MAX = new Year(9999);

	public YearPeriod(Year start, Year end) {
		super(start, end);
	}

	@Override
	public YearPeriod newSpan(Year newStart, Year newEnd) {
		return new YearPeriod(newStart, newEnd);
	}

	@Override
	protected Year max() {
		return MAX;
	}

	@Override
	protected List<YearMonth> yearMonthsBetween() {
		val results = new ArrayList<YearMonth>();
		YearMonth current = YearMonth.of(this.start().v(), 1);
		final YearMonth endYM = YearMonth.of(this.end().v(), 12);
		while (current.lessThanOrEqualTo(endYM)) {
			results.add(current);
			current = current.addMonths(1);
		}
		return results;
	}

	@Override
	protected List<GeneralDate> datesBetween() {
		val results = new ArrayList<GeneralDate>();
		GeneralDate current = GeneralDate.ymd(this.start().v(), 1, 1);
		final GeneralDate endDate = GeneralDate.ymd(this.end().v(), 12, 31);
		while (current.beforeOrEquals(endDate)) {
			results.add(current);
			current = current.addDays(1);
		}
		return results;
	}

}
