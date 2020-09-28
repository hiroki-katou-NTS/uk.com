package nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.continuousattendance;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.NumberOfConsecutiveDays;

@RunWith(JMockit.class)
public class MaxNumberDaysOfContinuousAttendanceComTest {
	@Test
	public void getters() {
		MaxNumberDaysOfContinuousAttendanceCom maxNoDaysOfContinuousAttCom = new MaxNumberDaysOfContinuousAttendanceCom(
				new MaxNumberDaysOfContinuousAttendance(new NumberOfConsecutiveDays(5)));

		NtsAssert.invokeGetters(maxNoDaysOfContinuousAttCom);

	}
}
