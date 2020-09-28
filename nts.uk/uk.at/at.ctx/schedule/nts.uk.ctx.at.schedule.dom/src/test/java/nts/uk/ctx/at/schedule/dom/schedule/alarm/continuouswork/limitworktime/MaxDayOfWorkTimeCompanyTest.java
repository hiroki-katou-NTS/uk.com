package nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.limitworktime;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
@RunWith(JMockit.class)
public class MaxDayOfWorkTimeCompanyTest {
	@Test
	public void getters() {
		val maxNumberDayOfPeriodsCom = MaxDayOfWorkTimeHelper.DUMMY;

		NtsAssert.invokeGetters(maxNumberDayOfPeriodsCom);

	}
	
	@Test
	public void create_maxNumberOfWorkingDayOfPeriodsCom_success() {
		val maxNumberDayOfPeriodsCom = MaxDayOfWorkTimeHelper.DUMMY;
		
		assertThat(maxNumberDayOfPeriodsCom.getCode().v()).isEqualTo("003");
		assertThat(maxNumberDayOfPeriodsCom.getName().v()).isEqualTo("シフト１");
	}
}
