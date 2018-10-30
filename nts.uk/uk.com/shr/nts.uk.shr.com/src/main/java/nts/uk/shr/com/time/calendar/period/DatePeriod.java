package nts.uk.shr.com.time.calendar.period;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;

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
	
	@Override
	public List<YearMonth> yearMonthsBetween(){
		List<YearMonth> result = new ArrayList<>();
		YearMonth startYM = this.start().yearMonth();
		YearMonth endYM = this.end().yearMonth();
		while (startYM.lessThanOrEqualTo(endYM)) {
			result.add(startYM);
			startYM = startYM.addMonths(1);
		}
		return result;
	}

	@Override
	public List<GeneralDate> datesBetween(){
		List<GeneralDate> result = new ArrayList<>();
		GeneralDate start = this.start();
		while (start.beforeOrEquals(this.end())) {
			result.add(start);
			start = start.addDays(1);
		}
		return result;
	}
	
	public void forEachByMonths(int unitMonths, Consumer<DatePeriod> process) {
		
		GeneralDate currentEnd;
		for (GeneralDate currentStart = this.start();
				currentStart.beforeOrEquals(this.end());
				currentStart = currentEnd.increase()) {
			
			currentEnd = whichBefore(
					currentStart.addMonths(unitMonths).decrease(),
					this.end());
			
			DatePeriod currentPeriod = new DatePeriod(currentStart, currentEnd);
			
			process.accept(currentPeriod);
		}
	}
	
	private static GeneralDate whichBefore(GeneralDate a, GeneralDate b) {
		return a.beforeOrEquals(b) ? a : b;
	}
}
