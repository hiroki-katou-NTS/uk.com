package nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.ConsecutiveNumberOfDays;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
/**
 * Unit Test: 就業時間帯の連続勤務できる上限日数
 * @author lan_lt
 *
 */
@RunWith(JMockit.class)
public class MaxDaysOfConsecutiveWorkTimeTest {
	@Test
	public void getters() {
		val maxNoDaysOfContWorkTime =  new MaxDaysOfConsecutiveWorkTime(
				Arrays.asList(new WorkTimeCode("001"),
						new WorkTimeCode("002"),
						new WorkTimeCode("003")
						),
				new ConsecutiveNumberOfDays(5));

		NtsAssert.invokeGetters(maxNoDaysOfContWorkTime);

	}
	
	@Test
	public void create_maxNumberDaysOfContinuousAttendance_success() {
		val maxDays = new ConsecutiveNumberOfDays(5);
		val workTimeCodes = Arrays.asList(new WorkTimeCode("001"),
				new WorkTimeCode("002"),
				new WorkTimeCode("003")
				);
		val maxDaysContWorkTime =  new MaxDaysOfConsecutiveWorkTime(workTimeCodes, maxDays);

		assertThat(maxDaysContWorkTime.getNumberOfDays().v()).isEqualTo(5);
		assertThat(maxDaysContWorkTime.getWorkTimeCodes()).containsExactlyInAnyOrderElementsOf(workTimeCodes);
	
	}
}
