package nts.uk.ctx.at.schedule.dom.schedule.alarm.bansamedayholiday;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.bansamedayholiday.ReferenceCalendar.Require;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarCompany;


@RunWith(JMockit.class)
public class CalendarCompanyReferenceTest {
	@Injectable
	private Require require;
	
	/**
	 * check BusinessDaysCalendarType	
	 * 結果：COMPANY
	 */
	@Test
	public void check_BusinessDaysCalendarType() {
		val calendarCom = new ReferenceCalendarCompany();
		assertThat(calendarCom.getBusinessDaysCalendarType()).isEqualTo(BusinessDaysCalendarType.COMPANY);
	}
	
	/**
	 * get WorkdayDivision
	 * 結果：empty
	 */
	@Test
	public void getWorkdayDivisionIsEmpty() {
		val date = GeneralDate.ymd(2020, 9, 1);
		val calendarCompany = new ReferenceCalendarCompany();
		new Expectations() {
			{
				require.getCalendarCompanyByDay(date);
				result = Optional.empty();

			}
		};

		assertThat(calendarCompany.getWorkdayDivision(require, date).isPresent()).isFalse();
	}
	
	/**
	 * get WorkdayDivision 結果：WORKINGDAYS
	 */
	@Test
	public void getWorkdayDivision_WORKINGDAYS() {
		val date = GeneralDate.ymd(2020, 9, 1);
		val calendarCompany = new ReferenceCalendarCompany();
		new Expectations() {
			{
				require.getCalendarCompanyByDay(date);
				result = Optional.of(new CalendarCompany("000000000000-0315", date, WorkdayDivision.WORKINGDAYS));

			}
		};

		assertThat(calendarCompany.getWorkdayDivision(require, date).get()).isEqualTo(WorkdayDivision.WORKINGDAYS);
	}
	
	/**
	 * get WorkdayDivision 結果：WORKINGDAYS
	 */
	@Test
	public void getWorkdayDivision_NON_WORKINGDAY_INLAW() {
		val date = GeneralDate.ymd(2020, 9, 1);
		val calendarCompany = new ReferenceCalendarCompany();
		new Expectations() {
			{
				require.getCalendarCompanyByDay(date);
				result = Optional.of(new CalendarCompany("000000000000-0315", date, WorkdayDivision.NON_WORKINGDAY_INLAW));

			}
		};

		assertThat(calendarCompany.getWorkdayDivision(require, date).get()).isEqualTo(WorkdayDivision.NON_WORKINGDAY_INLAW);
	}
	
	
	/**
	 * get WorkdayDivision 結果：NON_WORKINGDAY_EXTRALEGAL
	 */
	@Test
	public void getWorkdayDivision_NON_WORKINGDAY_EXTRALEGAL() {
		val date = GeneralDate.ymd(2020, 9, 1);
		val calendarCompany = new ReferenceCalendarCompany();
		new Expectations() {
			{
				require.getCalendarCompanyByDay(date);
				result = Optional.of(new CalendarCompany("000000000000-0315", date, WorkdayDivision.NON_WORKINGDAY_EXTRALEGAL));

			}
		};

		assertThat(calendarCompany.getWorkdayDivision(require, date).get()).isEqualTo(WorkdayDivision.NON_WORKINGDAY_EXTRALEGAL);
	}

}
