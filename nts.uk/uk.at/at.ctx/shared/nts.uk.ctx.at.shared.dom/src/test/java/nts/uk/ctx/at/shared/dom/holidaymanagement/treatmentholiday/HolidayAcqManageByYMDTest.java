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
		
		Map<GeneralDate, HolidayAcqManaPeriod> expecteds  = new HashMap<GeneralDate, HolidayAcqManaPeriod>() {
			private static final long serialVersionUID = 1L;
		{
			//起算日 > 基準日
			put(GeneralDate.ymd(2020, 10, 15), Helper.createHolidayAcqManaPeriod(GeneralDate.ymd(2020, 10, 15), GeneralDate.ymd(2020, 11, 11), 4.0));
			put(GeneralDate.ymd(2020, 11, 15), Helper.createHolidayAcqManaPeriod(GeneralDate.ymd(2020, 11, 12), GeneralDate.ymd(2020, 12, 9), 4.0));
			put(GeneralDate.ymd(2021, 3, 15), Helper.createHolidayAcqManaPeriod(GeneralDate.ymd(2021, 3, 4), GeneralDate.ymd(2021, 3, 31), 4.0));
			
			//起算日 <= 基準日
			put(GeneralDate.ymd(2021, 4, 15), Helper.createHolidayAcqManaPeriod(GeneralDate.ymd(2021, 4, 1), GeneralDate.ymd(2021, 4, 28), 4.0));
			put(GeneralDate.ymd(2021, 9, 15), Helper.createHolidayAcqManaPeriod(GeneralDate.ymd(2021, 8, 19), GeneralDate.ymd(2021, 9, 15), 4.0));
			put(GeneralDate.ymd(2022, 3, 15), Helper.createHolidayAcqManaPeriod(GeneralDate.ymd(2022, 3, 3), GeneralDate.ymd(2022, 3, 30), 4.0));
			
		}};
							
		expecteds.forEach( (baseDate, expected) -> {
			//Act
			HolidayAcqManaPeriod result = holidayAcqManageByYMD.getManagementPeriod( require, baseDate );
			
			//Assert
			assertThat( result.getPeriod().start()).isEqualTo( expected.getPeriod().start() );
			assertThat( result.getPeriod().end()).isEqualTo( expected.getPeriod().end() );
			assertThat( result.getHolidayDays()).isEqualTo( expected.getHolidayDays() );
		
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
