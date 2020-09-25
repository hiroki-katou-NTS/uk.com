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
public class RefCalendarCompanyTest {
	@Injectable
	private Require require;
	
	/**
	 * check BusinessDaysCalendarType	
	 * excepted：COMPANY
	 */
	@Test
	public void check_BusinessDaysCalendarType() {
		val calendarCmp= new ReferenceCalendarCompany();
		assertThat(calendarCmp.getBusinessDaysCalendarType()).isEqualTo(BusinessDaysCalendarType.COMPANY);
	}
	
	/**
	 * get WorkdayDivision
	 * excepted: WORKPLACE
	 */
	@Test
	public void getWorkdayDivisionIsEmpty() {
		val refCalCom = new ReferenceCalendarCompany();
		new Expectations() {
			{
				require.getCalendarCompanyByDay((GeneralDate)any);

			}
		};

		assertThat(refCalCom.getWorkdayDivision(require, GeneralDate.today())).isEmpty();
	}
	
	/**
	 * get WorkdayDivision 
	 * excepted: WORKINGDAYS
	 */
	@Test
	public void getWorkdayDivision_WORKINGDAYS() {
		val calCom = new CalendarCompany("000000000000-0315", GeneralDate.today(), WorkdayDivision.WORKINGDAYS);
		val refCalCom = new ReferenceCalendarCompany();
		new Expectations() {
			{
				require.getCalendarCompanyByDay((GeneralDate)any);
				result = Optional.of(calCom);

			}
		};

		assertThat(refCalCom.getWorkdayDivision(require, calCom.getDate()).get()).isEqualTo(calCom.getWorkDayDivision());
	}
	
	/**
	 * get WorkdayDivision 
	 * excepted: NON_WORKINGDAY_INLAW
	 */
	@Test
	public void getWorkdayDivision_NON_WORKINGDAY_INLAW() {
		val calCom = new CalendarCompany("000000000000-0315", GeneralDate.today(), WorkdayDivision.NON_WORKINGDAY_INLAW);
		val refCalCom = new ReferenceCalendarCompany();
		new Expectations() {
			{
				require.getCalendarCompanyByDay((GeneralDate) any);
				result = Optional.of(calCom);

			}
		};

		assertThat(refCalCom.getWorkdayDivision(require, calCom.getDate()).get()).isEqualTo(calCom.getWorkDayDivision());
	}
	
	
	/**
	 * get WorkdayDivision 
	 * excepted： NON_WORKINGDAY_EXTRALEGAL
	 */
	@Test
	public void getWorkdayDivision_NON_WORKINGDAY_EXTRALEGAL() {
		val calCom = new CalendarCompany("000000000000-0315", GeneralDate.today(), WorkdayDivision.NON_WORKINGDAY_EXTRALEGAL);
		val refCalCom = new ReferenceCalendarCompany();
		new Expectations() {
			{
				require.getCalendarCompanyByDay((GeneralDate) any);
				result = Optional.of(calCom);

			}
		};

		assertThat(refCalCom.getWorkdayDivision(require, calCom.getDate()).get()).isEqualTo(calCom.getWorkDayDivision());	}

}
