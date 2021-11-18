package nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import lombok.val;
import mockit.Injectable;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.days.FourWeekDays;

public class HolidayAcqManageByYMDTest {

	@Injectable
	private HolidayAcquisitionManagement.Require require;
	
	@Test
	public void testGetter() {
		HolidayAcqManageByYMD holidayAcqManageByYMD = new HolidayAcqManageByYMD(GeneralDate.today(), new FourWeekDays(4.0));
		NtsAssert.invokeGetters(holidayAcqManageByYMD);
	}
	
	@Test
	public void test_getUnitManagementPeriod() {
		HolidayAcqManageByYMD holidayAcqManageByYMD = new HolidayAcqManageByYMD(GeneralDate.today(), new FourWeekDays(4.0));
		HolidayCheckUnit holidayCheckUnit = holidayAcqManageByYMD.getUnitManagementPeriod();
		assertThat(holidayCheckUnit).isEqualTo(HolidayCheckUnit.FOUR_WEEK);
	}
	
	@Test
	public void test_getStartDateType() {
		HolidayAcqManageByYMD holidayAcqManageByYMD = new HolidayAcqManageByYMD(GeneralDate.today(), new FourWeekDays(4.0));
		StartDateClassification result = holidayAcqManageByYMD.getStartDateType();
		
		assertThat(result).isEqualTo(StartDateClassification.SPECIFY_YMD);
	}
	
	@Test
	public void testGetManagementPeriod() {
		
		val holidayAcqManageByYMD = new HolidayAcqManageByYMD(	GeneralDate.ymd(2021, 4, 1)//起算日
															,	new FourWeekDays(4.0)
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
				
				put(	GeneralDate.ymd(2022, 4, 1)
					,	Helper.createHolidayAcqManaPeriod(GeneralDate.ymd(2022, 3, 31), GeneralDate.ymd(2022, 4, 27), 4.0)
						);
		}};
							
		expected.entrySet().forEach( entry -> {
			//Act
			HolidayAcqManaPeriod result = holidayAcqManageByYMD.getManagementPeriod( require, entry.getKey() );
			
			//Assert
			assertThat( result.getPeriod()).isEqualTo( entry.getValue().getPeriod() );
			assertThat( result.getHolidayDays()).isEqualTo( entry.getValue().getHolidayDays() );
		
		});
		
	}
	
	public static class Helper {
		
		/**
		 * 休日取得の管理期間を作成する
		 * @param startDate 開始日
		 * @param endDate 終了日
		 * @param holidayDays 休日日数
		 * @return
		 */
		public static HolidayAcqManaPeriod createHolidayAcqManaPeriod( GeneralDate startDate, GeneralDate endDate, Double holidayDays) {
			
			return new HolidayAcqManaPeriod( new DatePeriod( startDate, endDate), new FourWeekDays(holidayDays));
		}
		
	}
}
