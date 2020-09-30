package nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveattendance;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.ConsecutiveNumberOfDays;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutivework.MaxDaysOfConsecutiveAttendance;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutivework.MaxDaysOfConsecutiveAttendanceCompany;

@RunWith(JMockit.class)
public class MaxDaysOfConsecutiveAttendanceComTest {
	@Test
	public void getters() {
		MaxDaysOfConsecutiveAttendanceCompany maxDaysOfConsecutiveAttCom = new MaxDaysOfConsecutiveAttendanceCompany(
				new MaxDaysOfConsecutiveAttendance(new ConsecutiveNumberOfDays(5)));

		NtsAssert.invokeGetters(maxDaysOfConsecutiveAttCom);

	}

	@Test
	public void create_maxDaysOfConsecuitveAttCom_success() {
		val maxDaysOfConsAtt = new MaxDaysOfConsecutiveAttendance(new ConsecutiveNumberOfDays(5));
		MaxDaysOfConsecutiveAttendanceCompany maxDaysOfConsecutiveAttCom = new MaxDaysOfConsecutiveAttendanceCompany(
				maxDaysOfConsAtt);

		assertThat(maxDaysOfConsecutiveAttCom.getNumberOfDays()).isEqualTo(maxDaysOfConsAtt);

	}
}
