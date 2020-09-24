package nts.uk.ctx.at.schedule.dom.schedule.alarm.bansamedayholiday;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.bansamedayholiday.ReferenceCalendar.Require;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarWorkplace;

@RunWith(JMockit.class)
public class CalendarWorkplaceReferenceTest {
	@Injectable
	private Require require;
	
	@Test
	public void getters() {
		val calendarWorkplace = new ReferenceCalendarWorkplace("wkpId");
		NtsAssert.invokeGetters(calendarWorkplace);
		
	}
	
	/**
	 * create Calendar workplace success
	 */
	@Test
	public void createCalendarWorkplace_success() {
		val calendarWorkplace = new ReferenceCalendarWorkplace("wkpId"); 
		assertThat(calendarWorkplace.getWorkplaceID()).isEqualTo("wkpId");
		
	}
	
	/**
	 *  check BusinessDaysCalendarType
	 *  excepted: BusinessDaysCalendarType.WORKPLACE
	 */
	@Test
	public void check_BusinessDaysCalendarType() {
		val calendarWorkplace = new ReferenceCalendarWorkplace("wkpId");
		assertThat(calendarWorkplace.getBusinessDaysCalendarType()).isEqualTo(BusinessDaysCalendarType.WORKPLACE);
	}
	
	/**
	 * get WorkdayDivision
	 * 結果：empty
	 */
	@Test
	public void getWorkdayDivisionIsEmpty() {
		val date = GeneralDate.ymd(2020, 9, 1);
		val calendarWorkplace = new ReferenceCalendarWorkplace("wkpId");
		new Expectations() {
			{
				require.getCalendarWorkplaceByDay(calendarWorkplace.getWorkplaceID(), date);
				result = Optional.empty();

			}
		};

		assertThat(calendarWorkplace.getWorkdayDivision(require, date).isPresent()).isFalse();
	}
	
	/**
	 * get WorkdayDivision 結果：WORKINGDAYS
	 */
	@Test
	public void getWorkdayDivision_WORKINGDAYS() {
		val date = GeneralDate.ymd(2020, 9, 1);
		val calendarWorkplace = new ReferenceCalendarWorkplace("wkpId");
		new Expectations() {
			{
				require.getCalendarWorkplaceByDay(calendarWorkplace.getWorkplaceID(), date);
				result = Optional.of(CalendarWorkplace.createFromJavaType("wkpId", date, WorkdayDivision.WORKINGDAYS.value));

			}
		};

		assertThat(calendarWorkplace.getWorkdayDivision(require, date).get()).isEqualTo(WorkdayDivision.WORKINGDAYS);
	}
	
	/**
	 * get WorkdayDivision 結果：WORKINGDAYS
	 */
	@Test
	public void getWorkdayDivision_NON_WORKINGDAY_INLAW() {
		val date = GeneralDate.ymd(2020, 9, 1);
		val calendarWorkplace = new ReferenceCalendarWorkplace("wkpId");
		new Expectations() {
			{
				require.getCalendarWorkplaceByDay(calendarWorkplace.getWorkplaceID(), date);
				result = Optional.of(CalendarWorkplace.createFromJavaType("wkpId", date, WorkdayDivision.NON_WORKINGDAY_INLAW.value));

			}
		};

		assertThat(calendarWorkplace.getWorkdayDivision(require, date).get()).isEqualTo(WorkdayDivision.NON_WORKINGDAY_INLAW);
	}
	
	
	/**
	 * get WorkdayDivision 結果：NON_WORKINGDAY_EXTRALEGAL
	 */
	@Test
	public void getWorkdayDivision_NON_WORKINGDAY_EXTRALEGAL() {
		val date = GeneralDate.ymd(2020, 9, 1);
		val calendarWorkplace = new ReferenceCalendarWorkplace("wkpId");
		new Expectations() {
			{
				require.getCalendarWorkplaceByDay(calendarWorkplace.getWorkplaceID(), date);
				result = Optional.of(CalendarWorkplace.createFromJavaType("wkpId", date, WorkdayDivision.NON_WORKINGDAY_EXTRALEGAL.value));

			}
		};

		assertThat(calendarWorkplace.getWorkdayDivision(require, date).get()).isEqualTo(WorkdayDivision.NON_WORKINGDAY_EXTRALEGAL);
	}

}
