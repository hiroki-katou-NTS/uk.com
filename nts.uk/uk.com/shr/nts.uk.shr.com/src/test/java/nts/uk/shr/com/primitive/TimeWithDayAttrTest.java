package nts.uk.shr.com.primitive;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import lombok.val;
import nts.uk.shr.com.enumcommon.DayAttr;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class TimeWithDayAttrTest {

	@Test
	public void thePresentDay() {
		val target1 = new TimeWithDayAttr(0);
		assertThat(target1.getDayDivision(), is(DayAttr.THE_PRESENT_DAY));
		assertThat(target1.hour(), is(0));
		assertThat(target1.minute(), is(0));
		
		val target2 = new TimeWithDayAttr(24 * 60 - 1);
		assertThat(target2.getDayDivision(), is(DayAttr.THE_PRESENT_DAY));
		assertThat(target2.hour(), is(23));
		assertThat(target2.minute(), is(59));
	}

	@Test
	public void theNextDay() {
		val target1 = new TimeWithDayAttr(24 * 60);
		assertThat(target1.getDayDivision(), is(DayAttr.THE_NEXT_DAY));
		assertThat(target1.hoursInDay(), is(0));
		assertThat(target1.hour(), is(24));
		assertThat(target1.minute(), is(0));
		
		val target2 = new TimeWithDayAttr(2 * 24 * 60 - 1);
		assertThat(target2.getDayDivision(), is(DayAttr.THE_NEXT_DAY));
		assertThat(target2.hoursInDay(), is(23));
		assertThat(target2.hour(), is(2 * 24 - 1));
		assertThat(target2.minute(), is(59));
	}

	@Test
	public void twoDaysLater() {
		val target1 = new TimeWithDayAttr(2 * 24 * 60);
		assertThat(target1.getDayDivision(), is(DayAttr.TWO_DAY_LATER));
		assertThat(target1.hoursInDay(), is(0));
		assertThat(target1.hour(), is(2 * 24));
		assertThat(target1.minute(), is(0));
		
		val target2 = new TimeWithDayAttr(3 * 24 * 60 - 1);
		assertThat(target2.getDayDivision(), is(DayAttr.TWO_DAY_LATER));
		assertThat(target2.hoursInDay(), is(23));
		assertThat(target2.hour(), is(3 * 24 - 1));
		assertThat(target2.minute(), is(59));
	}

	@Test
	public void thePreviousDay() {
		val target1 = new TimeWithDayAttr(-12 * 60);
		assertThat(target1.getDayDivision(), is(DayAttr.THE_PREVIOUS_DAY));
		assertThat(target1.hour(), is(12));
		assertThat(target1.minute(), is(0));
		
		val target2 = new TimeWithDayAttr(-1);
		assertThat(target2.getDayDivision(), is(DayAttr.THE_PREVIOUS_DAY));
		assertThat(target2.hour(), is(23));
		assertThat(target2.minute(), is(59));
	}

	@Test
	public void shiftWithLimit_plus() {
		val target = new TimeWithDayAttr(1200);
		val actual = target.shiftWithLimit(10);
		assertThat(actual, is(new TimeWithDayAttr(1210)));
	}
	
	@Test
	public void shiftWithLimit_minus() {
		val target = new TimeWithDayAttr(1200);
		val actual = target.shiftWithLimit(-10);
		assertThat(actual, is(new TimeWithDayAttr(1190)));
	}
	
	@Test
	public void shiftWithLimit_max() {
		val target = new TimeWithDayAttr(1200);
		val actual = target.shiftWithLimit(9999);
		assertThat(actual, is(TimeWithDayAttr.TWO_DAYS_LATER_2359));
	}
	
	@Test
	public void shiftWithLimit_min() {
		val target = new TimeWithDayAttr(1200);
		val actual = target.shiftWithLimit(-9999);
		assertThat(actual, is(TimeWithDayAttr.THE_PREVIOUS_DAY_1200));
	}
}
