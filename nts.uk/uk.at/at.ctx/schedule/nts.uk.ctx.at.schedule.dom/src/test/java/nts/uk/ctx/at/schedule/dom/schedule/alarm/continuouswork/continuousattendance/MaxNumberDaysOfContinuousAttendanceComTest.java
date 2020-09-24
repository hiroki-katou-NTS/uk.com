package nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.continuousattendance;

import static org.assertj.core.api.Assertions.assertThat;

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
				"000000000000-0315",
				new MaxNumberDaysOfContinuousAttendance(new NumberOfConsecutiveDays(5)));

		NtsAssert.invokeGetters(maxNoDaysOfContinuousAttCom);

	}
	
	@Test
	public void create_maxNumberDaysOfContinuousAttendanceCompany_success() {
		MaxNumberDaysOfContinuousAttendanceCom maxNoDaysOfContinuousAttCom = new MaxNumberDaysOfContinuousAttendanceCom(
				"000000000000-0315",
				new MaxNumberDaysOfContinuousAttendance(new NumberOfConsecutiveDays(5)));
		
		assertThat(maxNoDaysOfContinuousAttCom.getCompanyId()).isEqualTo("000000000000-0315");
		
		assertThat(maxNoDaysOfContinuousAttCom.getNumberOfDays().getNumberOfDays().v()).isEqualTo(5);
	
	}
}
