package nts.uk.shr.com.time.calendar.period;

import static mockit.Deencapsulation.*;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import lombok.val;
import nts.arc.time.GeneralDate;

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
		
	}
}
