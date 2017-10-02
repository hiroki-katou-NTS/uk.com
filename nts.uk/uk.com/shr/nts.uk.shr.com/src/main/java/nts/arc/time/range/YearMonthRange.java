package nts.arc.time.range;

import java.time.YearMonth;

import lombok.RequiredArgsConstructor;
import nts.gul.util.range.ComparableRange;

@RequiredArgsConstructor
public class YearMonthRange implements ComparableRange<YearMonthRange, YearMonth> {

	private final YearMonth start;
	private final YearMonth end;
	
	@Override
	public YearMonth start() {
		return this.start;
	}

	@Override
	public YearMonth end() {
		return this.end;
	}

	@Override
	public YearMonth startNext(boolean isIncrement) {
		return this.start.plusMonths(isIncrement ? 1 : -1);
	}

	@Override
	public YearMonth endNext(boolean isIncrement) {
		return this.end.plusMonths(isIncrement ? 1 : -1);
	}

	@Override
	public YearMonthRange newSpan(YearMonth newStart, YearMonth newEnd) {
		return new YearMonthRange(newStart, newEnd);
	}

}
