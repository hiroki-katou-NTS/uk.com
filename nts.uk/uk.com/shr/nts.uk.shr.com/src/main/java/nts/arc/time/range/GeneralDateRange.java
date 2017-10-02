package nts.arc.time.range;

import lombok.RequiredArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.gul.util.range.ComparableRange;

@RequiredArgsConstructor
public class GeneralDateRange implements ComparableRange<GeneralDateRange, GeneralDate> {

	private final GeneralDate start;
	private final GeneralDate end;
	
	@Override
	public GeneralDate start() {
		return this.start;
	}

	@Override
	public GeneralDate end() {
		return this.end;
	}

	@Override
	public GeneralDate startNext(boolean isIncrement) {
		return this.start.addDays(isIncrement ? 1 : -1);
	}

	@Override
	public GeneralDate endNext(boolean isIncrement) {
		return this.end.addDays(isIncrement ? 1 : -1);
	}

	@Override
	public GeneralDateRange newSpan(GeneralDate newStart, GeneralDate newEnd) {
		return new GeneralDateRange(newStart, newEnd);
	}

}
