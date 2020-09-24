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
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassificationCode;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarClass;

@RunWith(JMockit.class)
public class CalendarClassReferenceTest {
	@Injectable
	private Require require;
	
	@Test
	public void getters() {
		val calendarCls = new ReferenceCalendarClass(new ClassificationCode("0001"));
		NtsAssert.invokeGetters(calendarCls);
		
	}
	
	/**
	 * create CalendarClss success
	 */
	@Test
	public void createCalendarClss_success() {
		val calendarCls = new ReferenceCalendarClass(new ClassificationCode("0001"));
		assertThat(calendarCls.getClsCode().v()).isEqualTo("0001");
		
	}
	
	/**
	 *  check BusinessDaysCalendarType
	 *  excepted: BusinessDaysCalendarType.CLASSSICATION
	 */	
	@Test
	public void check_BusinessDaysCalendarType() {
		val calendarCls = new ReferenceCalendarClass(new ClassificationCode("0001"));
		assertThat(calendarCls.getBusinessDaysCalendarType()).isEqualTo(BusinessDaysCalendarType.CLASSSICATION);
	}
	
	/**
	 * get WorkdayDivision
	 * excepted：empty
	 */
	@Test
	public void getWorkdayDivisionIsEmpty() {
		val date = GeneralDate.ymd(2020, 9, 1); // GeneralDate.today()
		val calendarCls = new ReferenceCalendarClass(new ClassificationCode("0001"));
		new Expectations() {
			{
				require.getCalendarClassByDay((String)any, (GeneralDate)any);
				//result = Optional.empty();

			}
		};

		assertThat(calendarCls.getWorkdayDivision(require, date)).isEmpty();
	}
	
	/**
	 * get WorkdayDivision 
	 * excepted：WORKINGDAYS
	 */
	@Test
	public void getWorkdayDivision_WORKINGDAYS() {

		val calClass = CalendarClass.createFromJavaType("000000000000-0315", "0001", GeneralDate.today(), WorkdayDivision.WORKINGDAYS.value);
		val refCalClass = new ReferenceCalendarClass(new ClassificationCode(calClass.getClassId().v()));
		new Expectations() {
			{
				require.getCalendarClassByDay((String)any, (GeneralDate)any);
				result = Optional.of(calClass);
			}
		};

		assertThat(refCalClass.getWorkdayDivision(require, calClass.getDate()).get()).isEqualTo(calClass.getWorkDayDivision());
	}
	
	/**
	 * get WorkdayDivision 
	 * excepted：WORKINGDAYS
	 */
	@Test
	public void getWorkdayDivision_NON_WORKINGDAY_INLAW() {
		val date = GeneralDate.ymd(2020, 9, 1);
		val calendarCls = new ReferenceCalendarClass(new ClassificationCode("0001"));
		new Expectations() {
			{
				require.getCalendarClassByDay("0001", date);
				result = Optional.of(CalendarClass.createFromJavaType("000000000000-0315", "cls_id", date,
						WorkdayDivision.NON_WORKINGDAY_INLAW.value));

			}
		};

		assertThat(calendarCls.getWorkdayDivision(require, date).get()).isEqualTo(WorkdayDivision.NON_WORKINGDAY_INLAW);
	}
	
	
	/**
	 * get WorkdayDivision 
	 * excepted：NON_WORKINGDAY_EXTRALEGAL
	 */
	@Test
	public void getWorkdayDivision_NON_WORKINGDAY_EXTRALEGAL() {
		val date = GeneralDate.ymd(2020, 9, 1);
		val calendarCls = new ReferenceCalendarClass(new ClassificationCode("0001"));
		new Expectations() {
			{
				require.getCalendarClassByDay("0001", date);
				result = Optional.of(CalendarClass.createFromJavaType("000000000000-0315", "cls_id", date,
						WorkdayDivision.NON_WORKINGDAY_EXTRALEGAL.value));

			}
		};

		assertThat(calendarCls.getWorkdayDivision(require, date).get()).isEqualTo(WorkdayDivision.NON_WORKINGDAY_EXTRALEGAL);
	}

}
