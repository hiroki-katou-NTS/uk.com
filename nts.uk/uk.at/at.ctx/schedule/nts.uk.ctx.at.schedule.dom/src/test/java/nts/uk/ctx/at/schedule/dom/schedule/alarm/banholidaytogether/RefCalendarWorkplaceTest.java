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
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.BusinessDaysCalendarType;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.ReferenceCalendar.Require;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.ReferenceCalendarWorkplace;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarWorkplace;
/**
 * 営業日カレンダーの参照先(職場)
 * @author lan_lt
 *
 */
@RunWith(JMockit.class)
public class RefCalendarWorkplaceTest {
	@Injectable
	private Require require;
	
	@Test
	public void getters() {
		val calendarWorkplace = new ReferenceCalendarWorkplace("DUMMY");
		NtsAssert.invokeGetters(calendarWorkplace);
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
	 *  稼働日区分を取得する
	 *  check BusinessDaysCalendarType
	 *  excepted: WORKPLACE
	 */
	@Test
	public void check_BusinessDaysCalendarType_WORKPLACE() {
		val calendarWorkplace = new ReferenceCalendarWorkplace("DUMMY");
		assertThat(calendarWorkplace.getBusinessDaysCalendarType()).isEqualTo(BusinessDaysCalendarType.WORKPLACE);
	}
	
	/**
	 * 稼働日区分を取得する
	 * 期待値： empty
	 * excepted: empty
	 */
	@Test
	public void getWorkdayDivisionIsEmpty() {
		val refCalWorkplace = new ReferenceCalendarWorkplace("DUMMY");
		new Expectations() {
			{
				require.getCalendarWorkplaceByDay((String) any, (GeneralDate)any);
			}
		};

		assertThat(refCalWorkplace.getWorkdayDivision(require, GeneralDate.today())).isEmpty();
	}
	
	/**
	 * 稼働日区分を取得する
	 * 期待値： 稼働日 OR 法定休日 OR 法定外休日
	 * excepted: WORKINGDAYS OR NON_WORKINGDAY_INLAW OR NON_WORKINGDAY_EXTRALEGAL
	 */
	@Test
	public void getWorkdayDivision_WORKINGDAYS() {
		val calWorkplace = CalendarWorkplace.createFromJavaType("DUMMY", GeneralDate.today(), WorkdayDivision.WORKINGDAYS.value);
		val refCalWorkplace = new ReferenceCalendarWorkplace("DUMMY");
		new Expectations() {
			{
				require.getCalendarWorkplaceByDay((String) any, (GeneralDate)any);
				result = Optional.of(calWorkplace);
			}
		};

		WorkdayDivision actual = refCalWorkplace.getWorkdayDivision(require, calWorkplace.getDate()).get();
		assertThat(actual).isEqualTo(calWorkplace.getWorkDayDivision());
	}

}
