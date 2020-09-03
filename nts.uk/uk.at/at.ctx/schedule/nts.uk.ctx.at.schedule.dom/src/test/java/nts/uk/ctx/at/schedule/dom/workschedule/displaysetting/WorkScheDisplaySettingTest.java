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
	
	// NOTE : today() = 2020/8/22

	//1. InitDispMonth.NEXT_MONTH, 締め日＝22 => 期待値：2020/8/23～2020/9/22			
	@Test
	public void calcuInitDisplayPeriod_plusMonth_onClosingDate() {
		WorkScheDisplaySetting displaySetting = new WorkScheDisplaySetting(
				"companyID", // dummy
				InitDispMonth.NEXT_MONTH, 
				new OneMonth(DateInMonth.of(GeneralDate.today().day()))); // dummy
		
		DatePeriod period = displaySetting.calcuInitDisplayPeriod();
		assertThat(period.start().month()).isEqualTo(GeneralDate.today().month());
		assertThat(period.start().addDays(-1).day()).isEqualTo(GeneralDate.today().day());
		assertThat(period.end().month()).isEqualTo(GeneralDate.today().addMonths(1).month());
		assertThat(period.end().day()).isEqualTo(GeneralDate.today().day());
	}
	
	//2. InitDispMonth.NEXT_MONTH, 締め日＝21 => 期待値：2020/9/22～2020/10/21
	@Test
	public void calcuInitDisplayPeriod_plusMonth_beforeClosingDate() {
		WorkScheDisplaySetting displaySetting = new WorkScheDisplaySetting(
				"companyID", // dummy
				InitDispMonth.NEXT_MONTH, 
				new OneMonth(DateInMonth.of(GeneralDate.today().addDays(-1).day()))); // dummy
		
		DatePeriod period = displaySetting.calcuInitDisplayPeriod();
		assertThat(period.start().month()).isEqualTo(GeneralDate.today().addMonths(1).month());
		assertThat(period.start().day()).isEqualTo(GeneralDate.today().day());
		assertThat(period.end().month()).isEqualTo(GeneralDate.today().addMonths(2).month());
		assertThat(period.end().day()).isEqualTo(GeneralDate.today().addDays(-1).day());
		
	}
	
	//3. InitDispMonth.NEXT_MONTH, 締め日＝末日 => 期待値：2020/9/1～2020/9/30
	@Test
	public void calcuInitDisplayPeriod_plusMonth() {
		WorkScheDisplaySetting displaySetting = new WorkScheDisplaySetting(
				"companyID", // dummy
				InitDispMonth.NEXT_MONTH, 
				new OneMonth(DateInMonth.lastDay())); // dummy
		
		DatePeriod period = displaySetting.calcuInitDisplayPeriod();
		assertThat(period.end().month() - 1).isEqualTo(GeneralDate.today().month());
		assertThat(period.start().month() - 1).isEqualTo(GeneralDate.today().month());
		assertThat(period.start().day()).isEqualTo(1);
	}

	//4. InitDispMonth.CURRENT_MONTH, 締め日＝末日 => 期待値：2020/8/1～2020/8/31
	@Test
	public void calcuInitDisplayPeriod_currentMonth() {
		WorkScheDisplaySetting displaySetting = new WorkScheDisplaySetting(
				"companyID", // dummy
				InitDispMonth.CURRENT_MONTH, 
				new OneMonth(DateInMonth.lastDay())); // dummy
		
		DatePeriod period2 = displaySetting.calcuInitDisplayPeriod();
		assertThat(period2.start().month()).isEqualTo(GeneralDate.today().month());
		assertThat(period2.start().day()).isEqualTo(1);
		assertThat(period2.end().month()).isEqualTo(GeneralDate.today().month());
	}
	
	//5. InitDispMonth.CURRENT_MONTH, 締め日＝22 => 期待値：2020/7/23～2020/8/22
	@Test
	public void calcuInitDisplayPeriod_currentMonth_onClosingDate() {
		WorkScheDisplaySetting displaySetting = new WorkScheDisplaySetting(
				"companyID", // dummy
				InitDispMonth.CURRENT_MONTH, 
				new OneMonth(DateInMonth.of(GeneralDate.today().day()))); // dummy
		
		DatePeriod period2 = displaySetting.calcuInitDisplayPeriod();
		assertThat(period2.start().addMonths(1).month()).isEqualTo(GeneralDate.today().month());
		assertThat(period2.start().addDays(-1).day()).isEqualTo(GeneralDate.today().day());
		assertThat(period2.end().month()).isEqualTo(GeneralDate.today().month());
		assertThat(period2.end().day()).isEqualTo(GeneralDate.today().day());
	}
	
	//6. InitDispMonth.CURRENT_MONTH, 締め日＝21 => 期待値：2020/8/22～2020/9/21
	@Test
	public void calcuInitDisplayPeriod_currentMonth_beforeClosingDate() {
		WorkScheDisplaySetting displaySetting = new WorkScheDisplaySetting(
				"companyID", // dummy
				InitDispMonth.CURRENT_MONTH, 
				new OneMonth(DateInMonth.of(GeneralDate.today().addDays(-1).day()))); // dummy
		
		DatePeriod period2 = displaySetting.calcuInitDisplayPeriod();
		assertThat(period2.start().month()).isEqualTo(GeneralDate.today().month());
		assertThat(period2.start().day()).isEqualTo(GeneralDate.today().day());
		assertThat(period2.end().addMonths(-1).month()).isEqualTo(GeneralDate.today().month());
		assertThat(period2.end().addDays(1).day()).isEqualTo(GeneralDate.today().day());
	}
	
	@Test
	public void getter(){
		NtsAssert.invokeGetters(WorkScheDisplaySettingHelper.getWorkSche());
	}
	
}
