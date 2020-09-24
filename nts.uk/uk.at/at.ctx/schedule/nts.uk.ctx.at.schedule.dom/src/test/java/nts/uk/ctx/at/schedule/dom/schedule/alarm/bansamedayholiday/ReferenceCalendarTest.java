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
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarCompany;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarWorkplace;

@RunWith(JMockit.class)
public class ReferenceCalendarTest {
	@Injectable
	private Require require;
	
	/**
	 * ReferenceCalendarClass(参照分類) test getter
	 */
	@Test
	public void gettersOfClassfication() {
		val calendarCls = new ReferenceCalendarClass(new ClassificationCode("0001"));
		NtsAssert.invokeGetters(calendarCls);
		
	}
	
	/**
	 * ReferenceCalendarWorkplace(参照職場) test getter
	 */
	@Test
	public void gettersOfWorkplace() {
		val calendarWorkplace = new ReferenceCalendarWorkplace("DUMMY");
		NtsAssert.invokeGetters(calendarWorkplace);
		
	}
	
	/**
	 * create Reference Calendar Classification success
	 */
	@Test
	public void createRefCalendarClass_success() {
		val calendarClass = new ReferenceCalendarClass(new ClassificationCode("0001"));
		assertThat(calendarClass.getClassCode().v()).isEqualTo("0001");
		
	}
	
	/**
	 * create Reference Calendar Workplace success
	 */
	@Test
	public void createRefCalendarWorkplace_success() {
		val calendarWorkplace = new ReferenceCalendarWorkplace("DUMMY");
		assertThat(calendarWorkplace.getWorkplaceID()).isEqualTo("DUMMY");
		
	}
	
	/**
	 * check BusinessDaysCalendarType	
	 * excepted：COMPANY
	 */
	@Test
	public void check_BusinessDaysCalendarType_COMPANY() {
		val calendarCom = new ReferenceCalendarCompany();
		assertThat(calendarCom.getBusinessDaysCalendarType()).isEqualTo(BusinessDaysCalendarType.COMPANY);
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
	 *  check BusinessDaysCalendarType
	 *  excepted: WORKPLACE
	 */
	@Test
	public void check_BusinessDaysCalendarType_WORKPLACE() {
		val calendarWorkplace = new ReferenceCalendarWorkplace("DUMMY");
		assertThat(calendarWorkplace.getBusinessDaysCalendarType()).isEqualTo(BusinessDaysCalendarType.WORKPLACE);
	}
	
	/**
	 * get WorkdayDivision
	 * excepted：empty
	 */
	@Test
	public void getWorkdayDivisionIsEmpty() {
		val calendarCls = new ReferenceCalendarClass(new ClassificationCode("0001"));
		new Expectations() {
			{
				require.getCalendarClassByDay((String)any, (GeneralDate)any);

			}
		};

		assertThat(calendarCls.getWorkdayDivision(require, GeneralDate.today())).isEmpty();
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
	 * excepted：NON_WORKINGDAY_INLAW
	 */
	@Test
	public void getWorkdayDivision_NON_WORKINGDAY_INLAW() {
		val calCom = new CalendarCompany("000000000000-0315", GeneralDate.today(), WorkdayDivision.NON_WORKINGDAY_INLAW);
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
	 * excepted：NON_WORKINGDAY_EXTRALEGAL
	 */
	@Test
	public void getWorkdayDivision_NON_WORKINGDAY_EXTRALEGAL() {
		val calWorkplace = CalendarWorkplace.createFromJavaType("DUMMY", GeneralDate.today(), WorkdayDivision.NON_WORKINGDAY_EXTRALEGAL.value);
		val refCalWorkplace = new ReferenceCalendarWorkplace("DUMMY");
		new Expectations() {
			{
				require.getCalendarWorkplaceByDay((String) any, (GeneralDate)any);
				result = Optional.of(calWorkplace);
			}
		};

		assertThat(refCalWorkplace.getWorkdayDivision(require, calWorkplace.getDate()).get()).isEqualTo(calWorkplace.getWorkDayDivision());
	}

}
