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
		
		Map<GeneralDate, HolidayAcqManaPeriod> expected  = new HashMap<GeneralDate, HolidayAcqManaPeriod>() {
			
			private static final long serialVersionUID = 1L;
			
		{
			
			put(	GeneralDate.ymd(2021, 4, 1)
				,	Helper.createHolidayAcqManaPeriod(GeneralDate.ymd(2021, 4, 1), GeneralDate.ymd(2021, 4, 28), 4.0)
					);
			
			put(	GeneralDate.ymd(2021, 4, 28)
					,	Helper.createHolidayAcqManaPeriod(GeneralDate.ymd(2021, 4, 1), GeneralDate.ymd(2021, 4, 28), 4.0)
						);
			
			put(	GeneralDate.ymd(2021, 4, 29)
					,	Helper.createHolidayAcqManaPeriod(GeneralDate.ymd(2021, 4, 29), GeneralDate.ymd(2021, 5, 26), 4.0)
						);
			
			put(	GeneralDate.ymd(2021, 5, 1)
					,	Helper.createHolidayAcqManaPeriod(GeneralDate.ymd(2021, 4, 29), GeneralDate.ymd(2021, 5, 26), 4.0)
						);
			
			put(	GeneralDate.ymd(2021, 6, 1)
					,	Helper.createHolidayAcqManaPeriod(GeneralDate.ymd(2021, 5, 27), GeneralDate.ymd(2021, 6, 23), 4.0)
						);
			
			put(	GeneralDate.ymd(2021, 7, 1)
					,	Helper.createHolidayAcqManaPeriod(GeneralDate.ymd(2021, 6, 24), GeneralDate.ymd(2021, 7, 21), 4.0)
						);
			
			put(	GeneralDate.ymd(2021, 8, 1)
					,	Helper.createHolidayAcqManaPeriod(GeneralDate.ymd(2021, 7, 22), GeneralDate.ymd(2021, 8, 18), 4.0)
						);
			
			put(	GeneralDate.ymd(2021, 9, 1)
					,	Helper.createHolidayAcqManaPeriod(GeneralDate.ymd(2021, 8, 19), GeneralDate.ymd(2021, 9, 15), 4.0)
						);
			
			put(	GeneralDate.ymd(2021, 10, 1)
					,	Helper.createHolidayAcqManaPeriod(GeneralDate.ymd(2021, 9, 16), GeneralDate.ymd(2021, 10, 13), 4.0)
						);
			
			put(	GeneralDate.ymd(2021, 11, 1)
					,	Helper.createHolidayAcqManaPeriod(GeneralDate.ymd(2021, 10, 14), GeneralDate.ymd(2021, 11, 10), 4.0)
						);
			
			
			put(	GeneralDate.ymd(2021, 12, 1)
					,	Helper.createHolidayAcqManaPeriod(GeneralDate.ymd(2021, 11, 11), GeneralDate.ymd(2021, 12, 8), 4.0)
						);
			
			put(	GeneralDate.ymd(2022, 1, 1)
					,	Helper.createHolidayAcqManaPeriod(GeneralDate.ymd(2021, 12, 9), GeneralDate.ymd(2022, 1, 5), 4.0)
						);
			
			put(	GeneralDate.ymd(2022, 2, 1)
					,	Helper.createHolidayAcqManaPeriod(GeneralDate.ymd(2022, 1, 6), GeneralDate.ymd(2022, 2, 2), 4.0)
						);
			
			put(	GeneralDate.ymd(2022, 3, 1)
					,	Helper.createHolidayAcqManaPeriod(GeneralDate.ymd(2022, 2, 3), GeneralDate.ymd(2022, 3, 2), 4.0)
						);
			
			put(	GeneralDate.ymd(2022, 3, 3)
					,	Helper.createHolidayAcqManaPeriod(GeneralDate.ymd(2022, 3, 3), GeneralDate.ymd(2022, 3, 31), 5.0)
						);
			
			put(	GeneralDate.ymd(2022, 3, 31)
					,	Helper.createHolidayAcqManaPeriod(GeneralDate.ymd(2022, 3, 3), GeneralDate.ymd(2022, 3, 31), 5.0)
						);
			
			put(	GeneralDate.ymd(2022, 4, 1)
					,	Helper.createHolidayAcqManaPeriod(GeneralDate.ymd(2022, 4, 1), GeneralDate.ymd(2022, 4, 28), 4.0)
						);
		}};
		
		expected.entrySet().forEach( entry -> {
			//Act
			HolidayAcqManaPeriod result = holidayAcqManageByMD.getManagementPeriod( require, entry.getKey() );
			
			//Assert
			assertThat( result.getPeriod()).isEqualTo( entry.getValue().getPeriod() );
			assertThat( result.getHolidayDays()).isEqualTo( entry.getValue().getHolidayDays() );
		
		});
	}

}
