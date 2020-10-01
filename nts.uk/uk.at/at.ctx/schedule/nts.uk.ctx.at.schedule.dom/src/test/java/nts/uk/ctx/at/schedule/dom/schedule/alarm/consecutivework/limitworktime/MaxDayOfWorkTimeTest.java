package nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
/**
 * UnitTest: 就業時間帯の期間内上限勤務
 * @author lan_lt
 *
 */
@RunWith(JMockit.class)
public class MaxDayOfWorkTimeTest {
	
	@Test
	public void getters() {
		val maxDayOfWorkTime =  new MaxDayOfWorkTime(
				Arrays.asList(new WorkTimeCode("001"),
						new WorkTimeCode("002"),
						new WorkTimeCode("003")
						),
				new MaxDay(5));

		NtsAssert.invokeGetters(maxDayOfWorkTime);

	}
	
	@Test
	public void create_maxDayOfWorkTime_success() {
		val workTimeCodes = Arrays.asList(new WorkTimeCode("001"),
				new WorkTimeCode("002"),
				new WorkTimeCode("003")
				);
		val maxDayOfWorkTime =  new MaxDayOfWorkTime(workTimeCodes, new MaxDay(5));

		assertThat(maxDayOfWorkTime.getMaxDay().v()).isEqualTo(5);
		assertThat(maxDayOfWorkTime.getWorkTimeCodeList()).containsExactlyInAnyOrderElementsOf(workTimeCodes);

	}
	
}
