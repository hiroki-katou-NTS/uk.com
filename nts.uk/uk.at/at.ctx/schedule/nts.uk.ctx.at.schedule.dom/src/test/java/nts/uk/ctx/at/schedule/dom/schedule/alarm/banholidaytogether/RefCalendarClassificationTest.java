package nts.uk.ctx.at.schedule.dom.schedule.alarm.banholidaytogether;

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
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassificationCode;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.BusinessDaysCalendarType;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.ReferenceCalendar.Require;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.ReferenceCalendarClass;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarClass;
/**
 * Unit Test: 営業日カレンダーの参照先(分類)
 * @author lan_lt
 *
 */
@RunWith(JMockit.class)
public class RefCalendarClassificationTest {
	@Injectable
	private Require require;
	
	/**
	 * ReferenceCalendarClass(参照分類) test getter
	 */
	@Test
	public void getters() {
		val calendarCls = new ReferenceCalendarClass(new ClassificationCode("0001"));
		NtsAssert.invokeGetters(calendarCls);
		
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
	 * 
	 *  check BusinessDaysCalendarType
	 *  excepted: CLASSSICATION
	 */	
	@Test
	public void check_BusinessDaysCalendarType() {
		val calendarCls = new ReferenceCalendarClass(new ClassificationCode("0001"));
		assertThat(calendarCls.getBusinessDaysCalendarType()).isEqualTo(BusinessDaysCalendarType.CLASSSICATION);
	}
	
	
	/**
	 * 稼働日区分を取得する
	 * 期待値： empty
	 * excepted：empty
	 */
	@Test
	public void getWorkdayDivisionIsEmpty() {
		val calendarCls = new ReferenceCalendarClass(new ClassificationCode("0001"));
		new Expectations() {
			{
				require.getCalendarClassByDay((ClassificationCode)any, (GeneralDate)any);
			}
		};

		assertThat(calendarCls.getWorkdayDivision(require, GeneralDate.today())).isEmpty();
	}
	
	/**
	 * 稼働日区分を取得する
	 * 期待値： 稼働日 OR 法定休日 OR 法定外休日
	 * excepted: WORKINGDAYS OR NON_WORKINGDAY_INLAW OR NON_WORKINGDAY_EXTRALEGAL
	 */
	@Test
	public void getWorkdayDivision() {
		val calClass = CalendarClass.createFromJavaType("000000000000-0315", "0001", GeneralDate.today(), WorkdayDivision.WORKINGDAYS.value);
		val refCalClass = new ReferenceCalendarClass(new ClassificationCode(calClass.getClassId().v()));
		new Expectations() {
			{
				require.getCalendarClassByDay((ClassificationCode)any, (GeneralDate)any);
				result = Optional.of(calClass);
			}
		};

		WorkdayDivision actual = refCalClass.getWorkdayDivision(require, calClass.getDate()).get();
		assertThat(actual).isEqualTo(calClass.getWorkDayDivision());
	}

}
