package nts.uk.ctx.at.schedule.dom.workschedule.displaysetting;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.DateInMonth;
import nts.arc.time.calendar.OneMonth;
import nts.arc.time.calendar.period.DatePeriod;

@RunWith(JMockit.class)
public class WorkScheDisplaySettingTest {
	@Test
	public void getter(){
		NtsAssert.invokeGetters(WorkScheDisplaySettingHelper.getWorkSche());
	}
	
	@Test
	public void calcuInitDisplayPeriod_plusMonth() {
		WorkScheDisplaySetting displaySetting = new WorkScheDisplaySetting(
				"companyID", // dummy
				InitDispMonth.NEXT_MONTH, 
				new OneMonth(new DateInMonth(01, false))); // dummy
		GeneralDate baseDate = GeneralDate.ymd(2020, 06, 01);
		
		displaySetting.calcuInitDisplayPeriod();
		// if InitDispMonth is not NEXT_MONTH add 1 month
		baseDate = baseDate.addMonths(1);

		DatePeriod period = displaySetting.getEndDay().periodOf(baseDate);
		
		GeneralDate targetDate = GeneralDate.ymd(2020, 07, 01);
		assertThat(period.end().equals(targetDate)).isTrue();
	}
	
	@Test
	public void calcuInitDisplayPeriod_nonPlusMonth() {
		WorkScheDisplaySetting displaySetting = new WorkScheDisplaySetting(
				"companyID", // dummy
				InitDispMonth.CURRENT_MONTH, 
				new OneMonth(new DateInMonth(01, false))); // dummy
		GeneralDate baseDate = GeneralDate.ymd(2020, 06, 01);
		
		displaySetting.calcuInitDisplayPeriod();
		
		DatePeriod period = displaySetting.getEndDay().periodOf(baseDate);
			
		GeneralDate targetDate = GeneralDate.ymd(2020, 07, 01);
		assertThat(period.end().equals(targetDate)).isFalse();
		
	}
}
