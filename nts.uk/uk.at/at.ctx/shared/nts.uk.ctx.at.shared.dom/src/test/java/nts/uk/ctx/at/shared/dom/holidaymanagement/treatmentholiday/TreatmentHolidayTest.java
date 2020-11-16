package nts.uk.ctx.at.shared.dom.holidaymanagement.treatmentholiday;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.common.days.FourWeekDays;
import nts.uk.ctx.at.shared.dom.common.days.WeeklyDays;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.com.time.calendar.MonthDay;

@RunWith(JMockit.class)
public class TreatmentHolidayTest {
	
	@Injectable
	private TreatmentHoliday.Require require;

	@Test
	public void testGetter() {
		WeeklyHolidayAcqMana weeklyHolidayAcqMana = new WeeklyHolidayAcqMana(new WeeklyDays(1.0));
		TreatmentHoliday treatmentHoliday = new TreatmentHoliday("companyId", NotUseAtr.NOT_USE, weeklyHolidayAcqMana);
		NtsAssert.invokeGetters(treatmentHoliday);
	}
	
	/**
	 * if 休日取得管理  == 1週間単位の休日取得管理 TODO:Chờ team a Lẩu
	 */
	@Test
	public void test_getNumberHoliday_1() {
		WeeklyHolidayAcqMana weeklyHolidayAcqMana = new WeeklyHolidayAcqMana(new WeeklyDays(1.0));
		TreatmentHoliday treatmentHoliday = new TreatmentHoliday("companyId", NotUseAtr.NOT_USE, weeklyHolidayAcqMana);
		
//		HolidayAcqManaPeriod resultHolidayAcqManaPeriod = new HolidayAcqManaPeriod(new DatePeriod(GeneralDate.today(), GeneralDate.today().addMonths(1)),
//				new FourWeekDays(4.0) );
//		new MockUp<HolidayAcquisitionManagement>() {
//			@Mock
//			public HolidayAcqManaPeriod getManagementPeriod(Require require,GeneralDate ymd) {
//				return resultHolidayAcqManaPeriod;
//			}
//		};
		
		HolidayNumberManagement result = treatmentHoliday.getNumberHoliday(require, GeneralDate.today());
		assertThat( result.getAddNonstatutoryHolidays()).isEqualTo(treatmentHoliday.getAddNonstatutoryHolidays());
		//TODO:assertThat( result.getPeriod()).isEqualTo(new DatePeriod(GeneralDate.today(), GeneralDate.today().addDays(6))); Chờ domain bên a Lẩu
		assertThat( result.getHolidayDays().v()).isEqualTo(weeklyHolidayAcqMana.getWeeklyDays().v());
	}
	/**
	 * if 休日取得管理  == 月日起算の休日取得管理
	 */
	@Test
	public void test_getNumberHoliday_2() {
		HolidayAcqManageByMD holidayAcqManageByMD = new HolidayAcqManageByMD(new MonthDay(12, 31), new FourWeekDays(4.0), new WeeklyDays(1.0));
		TreatmentHoliday treatmentHoliday = new TreatmentHoliday("companyId", NotUseAtr.NOT_USE, holidayAcqManageByMD);
		
		HolidayNumberManagement result = treatmentHoliday.getNumberHoliday(require, GeneralDate.ymd(2020, 11, 11));
		assertThat( result.getAddNonstatutoryHolidays()).isEqualTo(treatmentHoliday.getAddNonstatutoryHolidays());
		assertThat( result.getPeriod()).isEqualTo(new DatePeriod(GeneralDate.ymd(2020, 12, 31), GeneralDate.ymd(2021, 1, 27))); 
		assertThat( result.getHolidayDays()).isEqualTo(holidayAcqManageByMD.getFourWeekHoliday());
	}
	
	/**
	 * if 休日取得管理  == 年月日起算の休日取得管理
	 */
	@Test
	public void test_getNumberHoliday_3() {
		HolidayAcqManageByYMD holidayAcqManageByYMD = new HolidayAcqManageByYMD(GeneralDate.ymd(2020, 11, 11), new FourWeekDays(4.0));
		TreatmentHoliday treatmentHoliday = new TreatmentHoliday("companyId", NotUseAtr.USE, holidayAcqManageByYMD);
		
		HolidayNumberManagement result = treatmentHoliday.getNumberHoliday(require, GeneralDate.ymd(2020, 11, 11));
		assertThat( result.getAddNonstatutoryHolidays()).isEqualTo(treatmentHoliday.getAddNonstatutoryHolidays());
		assertThat( result.getPeriod()).isEqualTo(new DatePeriod(GeneralDate.ymd(2020, 11, 11), GeneralDate.ymd(2020, 12, 8))); 
		assertThat( result.getHolidayDays()).isEqualTo(holidayAcqManageByYMD.getFourWeekHoliday());
	}

}
