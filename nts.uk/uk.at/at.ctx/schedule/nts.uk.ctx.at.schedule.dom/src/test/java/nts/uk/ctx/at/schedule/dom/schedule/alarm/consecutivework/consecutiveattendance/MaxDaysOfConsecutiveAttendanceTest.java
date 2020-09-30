package nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveattendance;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.ConsecutiveNumberOfDays;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutivework.MaxDaysOfConsecutiveAttendance;

@RunWith(JMockit.class)
public class MaxDaysOfConsecutiveAttendanceTest {
	@Test
	public void getters() {
		MaxDaysOfConsecutiveAttendance maxNoDaysOfContAtt =  new MaxDaysOfConsecutiveAttendance(new ConsecutiveNumberOfDays(5));

		NtsAssert.invokeGetters(maxNoDaysOfContAtt);

	}
	
	@Test
	public void create_maxNumberDaysOfContinuousAttendance_success() {
		MaxDaysOfConsecutiveAttendance maxNoDaysOfContAtt =  new MaxDaysOfConsecutiveAttendance(new ConsecutiveNumberOfDays(5));
		
		assertThat(maxNoDaysOfContAtt.getNumberOfDays().v()).isEqualTo(5);
	
	}
}
