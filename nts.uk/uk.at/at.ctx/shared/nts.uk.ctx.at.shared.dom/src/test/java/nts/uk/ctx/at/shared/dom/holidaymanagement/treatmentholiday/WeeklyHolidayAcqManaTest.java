package nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.days.WeeklyDays;

public class WeeklyHolidayAcqManaTest {

	@Test
	public void testGetter() {
		WeeklyHolidayAcqMana weeklyHolidayAcqMana = new WeeklyHolidayAcqMana( new WeeklyDays(4.0));
		NtsAssert.invokeGetters(weeklyHolidayAcqMana);
	}
	
	@Test
	public void test_getUnitManagementPeriod() {
		WeeklyHolidayAcqMana weeklyHolidayAcqMana = new WeeklyHolidayAcqMana( new WeeklyDays(4.0));
		HolidayCheckUnit result = weeklyHolidayAcqMana.getUnitManagementPeriod();
		assertThat(result).isEqualTo(HolidayCheckUnit.ONE_WEEK);
	}

}
