package nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.continuousworktime;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.NumberOfConsecutiveDays;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;

@RunWith(JMockit.class)
public class MaxNumberOfContinuousWorktimeTest {
	@Test
	public void getters() {
		val maxNoDaysOfContWorkTime =  new MaxNumberOfContinuousWorktime(
				Arrays.asList(new WorkTimeCode("001"),
						new WorkTimeCode("002"),
						new WorkTimeCode("003")
						),
				new NumberOfConsecutiveDays(5));

		NtsAssert.invokeGetters(maxNoDaysOfContWorkTime);

	}
	
	@Test
	public void create_maxNumberDaysOfContinuousAttendance_success() {
		val workTimeCodes = Arrays.asList(new WorkTimeCode("001"),
				new WorkTimeCode("002"),
				new WorkTimeCode("003")
				);
		val maxDaysContWorkTime =  new MaxNumberOfContinuousWorktime(
				workTimeCodes,
				new NumberOfConsecutiveDays(5));

		assertThat(maxDaysContWorkTime.getNumberOfDays().v()).isEqualTo(5);
		assertThat(
				MaxNumberDaysOfContinuousWorkTimeHelper.convertToWorkTimeCode(maxDaysContWorkTime.getWorktimeCodeLst()))
						.containsExactlyInAnyOrderElementsOf(
								MaxNumberDaysOfContinuousWorkTimeHelper.convertToWorkTimeCode(workTimeCodes));
	
	}
	

}
