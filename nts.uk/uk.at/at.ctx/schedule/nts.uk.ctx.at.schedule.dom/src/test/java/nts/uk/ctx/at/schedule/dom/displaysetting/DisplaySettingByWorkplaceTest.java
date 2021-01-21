package nts.uk.ctx.at.schedule.dom.displaysetting;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.DateInMonth;
import nts.arc.time.calendar.OneMonth;
import nts.arc.time.calendar.period.DatePeriod;

@RunWith(JMockit.class)
public class DisplaySettingByWorkplaceTest {
	
	// NOTE : today() = 2020/8/22
	private void today_mock() {
		new MockUp<GeneralDate>() {
	        @Mock
	        public GeneralDate today() {
	            return GeneralDate.ymd(2020, 8, 22);
	        }
	    };
	}

	
	//1. InitDispMonth.NEXT_MONTH, 締め日＝21 => 期待値：2020/9/22～2020/10/21
	@Test
	public void calcuInitDisplayPeriod_plusMonth_beforeClosingDate() {
		//today() = 2020/8/22
		this.today_mock();

		DisplaySettingByWorkplace displaySetting = new DisplaySettingByWorkplace(
				"companyID",
				InitDispMonth.NEXT_MONTH, 
				new OneMonth(DateInMonth.of(21)));
		
		DatePeriod period = displaySetting.calcuInitDisplayPeriod();
		
		assertThat (period.start()).isEqualTo(GeneralDate.ymd(2020, 9, 22));
		assertThat (period.end()).isEqualTo(GeneralDate.ymd(2020, 10, 21));
	}
	
	//2. InitDispMonth.NEXT_MONTH, 締め日＝22 => 期待値：2020/8/23～2020/9/22
	@Test
	public void calcuInitDisplayPeriod_plusMonth_onClosingDate() {
	    
		this.today_mock();
		
		DisplaySettingByWorkplace displaySetting = new DisplaySettingByWorkplace(
				"companyID",
				InitDispMonth.NEXT_MONTH, 
				new OneMonth(DateInMonth.of(22)));

		DatePeriod period = displaySetting.calcuInitDisplayPeriod();
		
		assertThat (period.start()).isEqualTo(GeneralDate.ymd(2020, 8, 23));
		assertThat (period.end()).isEqualTo(GeneralDate.ymd(2020, 9, 22));
	}
	
	//3. InitDispMonth.NEXT_MONTH, 締め日＝末日 => 期待値：2020/9/1～2020/9/30
	@Test
	public void calcuInitDisplayPeriod_plusMonth() {
		this.today_mock();
		
		DisplaySettingByWorkplace displaySetting = new DisplaySettingByWorkplace(
				"companyID",
				InitDispMonth.NEXT_MONTH, 
				new OneMonth(DateInMonth.lastDay()));
		
		DatePeriod period = displaySetting.calcuInitDisplayPeriod();

		assertThat (period.start()).isEqualTo(GeneralDate.ymd(2020, 9, 1));
		assertThat (period.end()).isEqualTo(GeneralDate.ymd(2020, 9, 30));
	}

	//4. InitDispMonth.CURRENT_MONTH, 締め日＝21 => 期待値：2020/8/22～2020/9/21
	@Test
	public void calcuInitDisplayPeriod_currentMonth_beforeClosingDate() {
		this.today_mock();
		
		DisplaySettingByWorkplace displaySetting = new DisplaySettingByWorkplace(
				"companyID",
				InitDispMonth.CURRENT_MONTH, 
				new OneMonth(DateInMonth.of(21)));
		
		DatePeriod period = displaySetting.calcuInitDisplayPeriod();
		
		assertThat (period.start()).isEqualTo(GeneralDate.ymd(2020, 8, 22));
		assertThat (period.end()).isEqualTo(GeneralDate.ymd(2020, 9, 21));
	}
	
	//5. InitDispMonth.CURRENT_MONTH, 締め日＝22 => 期待値：2020/7/23～2020/8/22
	@Test
	public void calcuInitDisplayPeriod_currentMonth_onClosingDate() {
		this.today_mock();
		
		DisplaySettingByWorkplace displaySetting = new DisplaySettingByWorkplace(
				"companyID",
				InitDispMonth.CURRENT_MONTH, 
				new OneMonth(DateInMonth.of(22)));
		
		DatePeriod period = displaySetting.calcuInitDisplayPeriod();
		
		assertThat (period.start()).isEqualTo(GeneralDate.ymd(2020, 7, 23));
		assertThat (period.end()).isEqualTo(GeneralDate.ymd(2020, 8, 22));
	}	

	//6. InitDispMonth.CURRENT_MONTH, 締め日＝末日 => 期待値：2020/8/1～2020/8/31
	@Test
	public void calcuInitDisplayPeriod_currentMonth() {
		this.today_mock();
		
		DisplaySettingByWorkplace displaySetting = new DisplaySettingByWorkplace(
				"companyID",
				InitDispMonth.CURRENT_MONTH, 
				new OneMonth(DateInMonth.lastDay()));
		
		DatePeriod period = displaySetting.calcuInitDisplayPeriod();
		
		assertThat (period.start()).isEqualTo(GeneralDate.ymd(2020, 8, 1));
		assertThat (period.end()).isEqualTo(GeneralDate.ymd(2020, 8, 31));
	}
	
	@Test
	public void getter(){
		NtsAssert.invokeGetters(DisplaySettingByWorkplaceHelper.getWorkSche());
	}
	
}
