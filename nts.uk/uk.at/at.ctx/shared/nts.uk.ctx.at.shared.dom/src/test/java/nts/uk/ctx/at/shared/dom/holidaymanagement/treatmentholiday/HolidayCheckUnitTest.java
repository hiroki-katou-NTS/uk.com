package nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

public class HolidayCheckUnitTest {

	@Test
	public void getters() {
		HolidayCheckUnit holidayCheckUnit = HolidayCheckUnit.valueOf(1);
		NtsAssert.invokeGetters(holidayCheckUnit);
	}
	
	@Test
	public void test() {
		HolidayCheckUnit holidayCheckUnit = HolidayCheckUnit.valueOf(0);
		assertThat(holidayCheckUnit).isEqualTo(HolidayCheckUnit.ONE_WEEK);
		holidayCheckUnit = HolidayCheckUnit.valueOf(1);
		assertThat(holidayCheckUnit).isEqualTo(HolidayCheckUnit.FOUR_WEEK);
	}

}
