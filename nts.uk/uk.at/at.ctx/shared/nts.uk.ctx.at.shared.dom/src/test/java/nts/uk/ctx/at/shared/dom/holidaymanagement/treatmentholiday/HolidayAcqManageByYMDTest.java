package nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

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
	public void test_make4Weeks() {
		HolidayAcqManageByYMD holidayAcqManageByYMD = new HolidayAcqManageByYMD(GeneralDate.today(), new FourWeekDays(4.0));
		DatePeriod datePeriod = holidayAcqManageByYMD.make4Weeks(GeneralDate.ymd(2020, 11, 11), GeneralDate.ymd(2020, 11, 12));
		assertThat(datePeriod).isEqualTo(new DatePeriod(GeneralDate.ymd(2020, 11, 11), GeneralDate.ymd(2020, 12, 8)));
	}
	
	
	@Test
	public void test_getStartDateType() {
		HolidayAcqManageByYMD holidayAcqManageByYMD = new HolidayAcqManageByYMD(GeneralDate.today(), new FourWeekDays(4.0));
		StartDateClassification result = holidayAcqManageByYMD.getStartDateType();
		
		assertThat(result).isEqualTo(StartDateClassification.SPECIFY_YMD);
	}
	
	@Test
	public void test_getManagementPeriod() {
		HolidayAcqManageByYMD holidayAcqManageByYMD = new HolidayAcqManageByYMD(GeneralDate.ymd(2020, 11, 11), new FourWeekDays(4.0));
		HolidayAcqManaPeriod result = holidayAcqManageByYMD.getManagementPeriod(require, GeneralDate.ymd(2020, 11, 11));
		
		assertThat(result.getHolidayDays().v()).isEqualTo(4.0);
		assertThat(result.getPeriod())
				.isEqualTo(new DatePeriod(GeneralDate.ymd(2020, 11, 11), GeneralDate.ymd(2020, 12, 8)));
	}

}
