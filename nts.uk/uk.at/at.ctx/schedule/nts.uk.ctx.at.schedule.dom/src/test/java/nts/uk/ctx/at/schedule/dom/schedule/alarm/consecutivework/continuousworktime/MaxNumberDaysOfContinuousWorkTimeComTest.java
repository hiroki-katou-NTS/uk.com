package nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.continuousworktime;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;

@RunWith(JMockit.class)
public class MaxNumberDaysOfContinuousWorkTimeComTest {
	@Test
	public void getters() {
		val maxNoDaysOfContinuousAttOrg = MaxNumberDaysOfContinuousWorkTimeHelper.DUMMY;

		NtsAssert.invokeGetters(maxNoDaysOfContinuousAttOrg);

	}

	@Test
	public void create_maxNumberDaysOfContinuousWorkTimeCom_success() {
		val maxNoDaysOfContinuousAttOrg = MaxNumberDaysOfContinuousWorkTimeHelper.DUMMY;
		
		assertThat(maxNoDaysOfContinuousAttOrg.getCode().v()).isEqualTo("003");
		assertThat(maxNoDaysOfContinuousAttOrg.getName().v()).isEqualTo("name");
	}
}
