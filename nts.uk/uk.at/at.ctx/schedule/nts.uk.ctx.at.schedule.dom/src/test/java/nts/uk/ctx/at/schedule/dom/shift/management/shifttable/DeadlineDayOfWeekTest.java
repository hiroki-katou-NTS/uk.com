package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.DayOfWeek;

public class DeadlineDayOfWeekTest {
	
	@Test
	public void testGetter() {
		DeadlineDayOfWeek target = new DeadlineDayOfWeek(DeadlineWeekAtr.ONE_WEEK_AGO, DayOfWeek.SUNDAY);
		
		NtsAssert.invokeGetters(target);
	}
	
	@Test
	public void test_getLastDeadlineWithWeekAtr_oneWeekAgo() {
		
		DeadlineDayOfWeek setting = new DeadlineDayOfWeek(DeadlineWeekAtr.ONE_WEEK_AGO, DayOfWeek.THURSDAY);
		
		GeneralDate result = setting.getLastDeadlineWithWeekAtr(GeneralDate.ymd(2020, 10, 4));
		
		assertThat(result).isEqualTo(GeneralDate.ymd(2020, 10, 1));
	}
	
	@Test
	public void test_getLastDeadlineWithWeekAtr_twoWeekAgo() {
		
		DeadlineDayOfWeek setting = new DeadlineDayOfWeek(DeadlineWeekAtr.TWO_WEEK_AGO, DayOfWeek.THURSDAY);
		
		GeneralDate result = setting.getLastDeadlineWithWeekAtr(GeneralDate.ymd(2020, 10, 4));
		
		assertThat(result).isEqualTo(GeneralDate.ymd(2020, 9, 24));
	}
	
	@Test
	public void test_getMostRecentDeadlineIncludeTargetDate_targetNotEqualToDayOfWeek() {
		
		DeadlineDayOfWeek setting = new DeadlineDayOfWeek(DeadlineWeekAtr.ONE_WEEK_AGO, DayOfWeek.THURSDAY);
		
		GeneralDate result = setting.getMostRecentDeadlineIncludeTargetDate(GeneralDate.ymd(2020, 10, 7));
		
		assertThat(result).isEqualTo(GeneralDate.ymd(2020, 10, 8));
		
	}
	
	@Test
	public void test_getMostRecentDeadlineIncludeTargetDate_targetEqualToDayOfWeek() {
		
		DeadlineDayOfWeek setting = new DeadlineDayOfWeek(DeadlineWeekAtr.ONE_WEEK_AGO, DayOfWeek.THURSDAY);
		
		GeneralDate result = setting.getMostRecentDeadlineIncludeTargetDate(GeneralDate.ymd(2020, 10, 8));
		
		assertThat(result).isEqualTo(GeneralDate.ymd(2020, 10, 8));
		
	}

}
