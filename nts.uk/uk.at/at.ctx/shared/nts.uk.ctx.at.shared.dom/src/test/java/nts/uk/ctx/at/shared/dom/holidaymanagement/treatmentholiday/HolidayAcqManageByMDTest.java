package nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.days.FourWeekDays;
import nts.uk.ctx.at.shared.dom.common.days.WeeklyDays;
import nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday.HolidayAcqManageByYMDTest.Helper;
import nts.uk.shr.com.time.calendar.MonthDay;
@RunWith(JMockit.class)
public class HolidayAcqManageByMDTest {
	
	@Injectable
	private HolidayAcquisitionManagement.Require require;
	

	@Test
	public void testGetter() {
		HolidayAcqManageByMD holidayAcqManageByMD = new HolidayAcqManageByMD(new MonthDay(12, 31), new FourWeekDays(4.0), new WeeklyDays(1.0));
		NtsAssert.invokeGetters(holidayAcqManageByMD);
	}
	
	@Test
	public void test_getUnitManagementPeriod() {
		HolidayAcqManageByMD holidayAcqManageByMD = new HolidayAcqManageByMD(new MonthDay(12, 31), new FourWeekDays(4.0), new WeeklyDays(1.0));
		HolidayCheckUnit holidayCheckUnit = holidayAcqManageByMD.getUnitManagementPeriod();
		assertThat(holidayCheckUnit).isEqualTo(HolidayCheckUnit.FOUR_WEEK);
	}
	
	@Test
	public void testGetManagementPeriod() {
		
		val holidayAcqManageByMD = new HolidayAcqManageByMD(	new MonthDay(4, 1)//起算月日
															,	new FourWeekDays(4.0)//4週間の休日日数
															,	new WeeklyDays(1.0)//最終週の休日日数
															);
		
		Map<GeneralDate, HolidayAcqManaPeriod> expecteds  = new HashMap<GeneralDate, HolidayAcqManaPeriod>() {
			
			private static final long serialVersionUID = 1L;
			
		{
			//基準日 < 起算日
			put(GeneralDate.ymd(2019, 4, 1), Helper.createHolidayAcqManaPeriod(GeneralDate.ymd(2019, 4, 1), GeneralDate.ymd(2019, 4, 28), 4.0));
			put(GeneralDate.ymd(2019, 3, 1), Helper.createHolidayAcqManaPeriod(GeneralDate.ymd(2019, 2, 3), GeneralDate.ymd(2019, 3, 2), 4.0));
			put(GeneralDate.ymd(2019, 3, 3), Helper.createHolidayAcqManaPeriod(GeneralDate.ymd(2019, 3, 3), GeneralDate.ymd(2019, 3, 31), 5.0));
			put(GeneralDate.ymd(2019, 3, 31), Helper.createHolidayAcqManaPeriod(GeneralDate.ymd(2019, 3, 3), GeneralDate.ymd(2019, 3, 31), 5.0));
			//基準日 >=  起算日
			put(GeneralDate.ymd(2021, 4, 1), Helper.createHolidayAcqManaPeriod(GeneralDate.ymd(2021, 4, 1), GeneralDate.ymd(2021, 4, 28), 4.0));
			put(GeneralDate.ymd(2022, 3, 1), Helper.createHolidayAcqManaPeriod(GeneralDate.ymd(2022, 2, 3), GeneralDate.ymd(2022, 3, 2), 4.0));
			put(GeneralDate.ymd(2022, 3, 3), Helper.createHolidayAcqManaPeriod(GeneralDate.ymd(2022, 3, 3), GeneralDate.ymd(2022, 3, 31), 5.0));
			put(GeneralDate.ymd(2022, 3, 31), Helper.createHolidayAcqManaPeriod(GeneralDate.ymd(2022, 3, 3), GeneralDate.ymd(2022, 3, 31), 5.0));
		}};
		
		expecteds.forEach( (baseDate, expected) -> {
			//Act
			HolidayAcqManaPeriod result = holidayAcqManageByMD.getManagementPeriod( require, baseDate );
			
			//Assert
			assertThat( result.getPeriod().start()).isEqualTo( expected.getPeriod().start() );
			assertThat( result.getPeriod().end()).isEqualTo( expected.getPeriod().end() );
			assertThat( result.getHolidayDays()).isEqualTo( expected.getHolidayDays() );
		
		});
	}

}
