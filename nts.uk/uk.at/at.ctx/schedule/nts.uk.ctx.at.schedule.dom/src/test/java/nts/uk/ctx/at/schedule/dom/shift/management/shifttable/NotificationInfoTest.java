package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;

public class NotificationInfoTest {
	
	@Test
	public void test_getter() {
		
		NotificationInfo result = NotificationInfo.createWithoutNotify();
		NtsAssert.invokeGetters(result);
	}
	
	@Test
	public void test_createWithoutNotify() {
		
		NotificationInfo result = NotificationInfo.createWithoutNotify();
		
		assertThat(result.isNotify()).isFalse();
		assertThat(result.getDeadlineAndPeriod()).isEmpty();
	}
	
	@Test
	public void test_createNotification() {
		GeneralDate deadline = GeneralDate.ymd(2020, 10, 10);
		DatePeriod period = new DatePeriod( GeneralDate.ymd(2020, 10, 16), GeneralDate.ymd(2020, 11, 15));
		
		DeadlineAndPeriodOfWorkAvailability deadlineAndPeriod = new DeadlineAndPeriodOfWorkAvailability(deadline, period);
		
		NotificationInfo result = NotificationInfo.createNotification(deadlineAndPeriod);
		
		assertThat(result.isNotify()).isTrue();
		assertThat(result.getDeadlineAndPeriod().get().getDeadline()).isEqualTo(deadline);
		assertThat(result.getDeadlineAndPeriod().get().getPeriod()).isEqualTo(period);
	}

}
