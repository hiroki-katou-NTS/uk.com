package nts.uk.shr.com.time.calendar.period;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;

public class GeneralPeriodTest {

	@Test
	public void isReversed() {
		val d1 = GeneralDate.ymd(2000, 1, 1);
		val d2 = GeneralDate.ymd(2000, 1, 2);

		assertThat(new SamplePeriod(d1, d1).isReversed(), is(false));
		assertThat(new SamplePeriod(d1, d2).isReversed(), is(false));
		assertThat(new SamplePeriod(d2, d1).isReversed(), is(true));
	}

	
	private static class SamplePeriod extends GeneralPeriod<SamplePeriod, GeneralDate> {

		SamplePeriod(GeneralDate start, GeneralDate end) {
			super(start, end);
		}

		@Override
		public SamplePeriod newSpan(GeneralDate newStart, GeneralDate newEnd) {
			return new SamplePeriod(newStart, newEnd);
		}

		@Override
		protected GeneralDate max() {
			return GeneralDate.max();
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
	}
}
