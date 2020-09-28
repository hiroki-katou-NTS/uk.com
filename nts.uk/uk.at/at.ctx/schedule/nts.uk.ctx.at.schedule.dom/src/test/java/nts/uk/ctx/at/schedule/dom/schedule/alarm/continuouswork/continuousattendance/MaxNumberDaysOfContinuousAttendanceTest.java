package nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.continuousattendance;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.NumberOfConsecutiveDays;

@RunWith(JMockit.class)
public class MaxNumberDaysOfContinuousAttendanceTest {
	@Test
	public void getters() {
		MaxNumberDaysOfContinuousAttendance maxNoDaysOfContAtt =  new MaxNumberDaysOfContinuousAttendance(new NumberOfConsecutiveDays(5));

		NtsAssert.invokeGetters(maxNoDaysOfContAtt);

	}
	
	@Test
	public void create_maxNumberDaysOfContinuousAttendance_success() {
		MaxNumberDaysOfContinuousAttendance maxNoDaysOfContAtt =  new MaxNumberDaysOfContinuousAttendance(new NumberOfConsecutiveDays(5));
		
		assertThat(maxNoDaysOfContAtt.getNumberOfDays().v()).isEqualTo(5);
	
	}
}
