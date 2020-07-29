package nts.uk.ctx.at.schedule.dom.workschedule.displaysetting;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

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
	public void calcuInitDisplayPeriod_plusMonth() {
		WorkScheDisplaySetting displaySetting = new WorkScheDisplaySetting(
				"companyID", // dummy
				InitDispMonth.valueOf(InitDispMonth.NEXT_MONTH.value), 
				new OneMonth(new DateInMonth(01, false))); // dummy
		
		DatePeriod period = displaySetting.calcuInitDisplayPeriod();
		assertEquals(period.end().month(), GeneralDate.today().addMonths(1).month());
	}
	

	@Test
	public void calcuInitDisplayPeriod_nonPlusMonth() {
		WorkScheDisplaySetting displaySetting = new WorkScheDisplaySetting(
				"companyID", // dummy
				InitDispMonth.valueOf(InitDispMonth.CURRENT_MONTH.value), 
				new OneMonth(new DateInMonth(10, true))); // dummy
		
		DatePeriod period2 = displaySetting.calcuInitDisplayPeriod();
		assertNotEquals(period2.end().month(), GeneralDate.today().addMonths(1).month());
	}
	
	@Test
	public void getter(){
		NtsAssert.invokeGetters(WorkScheDisplaySettingHelper.getWorkSche());
	}
	
}
