package nts.uk.ctx.at.schedule.dom.schedule.alarm.banholidaytogether;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.WorkdayDivision;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.BusinessDaysCalendarType;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.ReferenceCalendar.Require;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.ReferenceCalendarCompany;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.daycalendar.CalendarCompany;
/**
 * Unit Test: 営業日カレンダーの参照先(会社)
 * @author lan_lt
 *
 */

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
	 * 稼働日区分を取得する
	 * 期待値： empty
	 * excepted: empty
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
	 * 稼働日区分を取得する
	 * 期待値： 稼働日 OR 法定休日 OR 法定外休日
	 * excepted: WORKINGDAYS OR NON_WORKINGDAY_INLAW OR NON_WORKINGDAY_EXTRALEGAL
	 */
	@Test
	public void getWorkdayDivision() {
		val calCom = new CalendarCompany("000000000000-0315", GeneralDate.today(), WorkdayDivision.WORKINGDAYS);
		val refCalCom = new ReferenceCalendarCompany();
		new Expectations() {
			{
				require.getCalendarCompanyByDay((GeneralDate)any);
				result = Optional.of(calCom);
			}
		};

		val actual = refCalCom.getWorkdayDivision(require, calCom.getDate()).get();
		assertThat(actual).isEqualTo(calCom.getWorkDayDivision());
	}

}
