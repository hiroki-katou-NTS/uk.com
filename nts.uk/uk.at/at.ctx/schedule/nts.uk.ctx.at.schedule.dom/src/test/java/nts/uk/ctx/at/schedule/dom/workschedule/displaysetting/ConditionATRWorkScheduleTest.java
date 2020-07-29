package nts.uk.ctx.at.schedule.dom.workschedule.displaysetting;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;


@RunWith(JMockit.class)
public class ConditionATRWorkScheduleTest {

	@Test
	public void getter(){
		ConditionATRWorkSchedule conditionATRWorkSchedule = ConditionATRWorkSchedule.valueOf(1);
		NtsAssert.invokeGetters(conditionATRWorkSchedule);
	}
	@Test
	public void test() {
		ConditionATRWorkSchedule conditionATRWorkSchedule0 = ConditionATRWorkSchedule.valueOf(0);
		assertThat(conditionATRWorkSchedule0).isEqualTo(ConditionATRWorkSchedule.INSURANCE_STATUS);
		ConditionATRWorkSchedule conditionATRWorkSchedule1 = ConditionATRWorkSchedule.valueOf(1);
		assertThat(conditionATRWorkSchedule1).isEqualTo(ConditionATRWorkSchedule.TEAM);
		ConditionATRWorkSchedule conditionATRWorkSchedule2 = ConditionATRWorkSchedule.valueOf(2);
		assertThat(conditionATRWorkSchedule2).isEqualTo(ConditionATRWorkSchedule.RANK);
		ConditionATRWorkSchedule conditionATRWorkSchedule3 = ConditionATRWorkSchedule.valueOf(3);
		assertThat(conditionATRWorkSchedule3).isEqualTo(ConditionATRWorkSchedule.QUALIFICATION);
		ConditionATRWorkSchedule conditionATRWorkSchedule4 = ConditionATRWorkSchedule.valueOf(4);
		assertThat(conditionATRWorkSchedule4).isEqualTo(ConditionATRWorkSchedule.LICENSE_ATR);
		
	}
}
