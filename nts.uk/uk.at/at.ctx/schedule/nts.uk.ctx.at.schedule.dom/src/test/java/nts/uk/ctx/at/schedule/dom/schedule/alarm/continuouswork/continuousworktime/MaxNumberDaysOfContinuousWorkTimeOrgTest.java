package nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.continuousworktime;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;

@RunWith(JMockit.class)
public class MaxNumberDaysOfContinuousWorkTimeOrgTest {
	@Test
	public void getters() {
		val maxDaysContiAttOrg = MaxNumberDaysOfContinuousWorkTimeHelper.DUMMY_ORG;

		NtsAssert.invokeGetters(maxDaysContiAttOrg);

	}
	
	@Test
	public void create_maxNumberDaysOfContinuousWorkTimeOrg_success() {
		val maxDaysContiAttOrg = MaxNumberDaysOfContinuousWorkTimeHelper.DUMMY_ORG;

		assertThat(maxDaysContiAttOrg.getTargeOrg().getUnit()).isEqualTo(TargetOrganizationUnit.WORKPLACE);
		assertThat(maxDaysContiAttOrg.getTargeOrg().getWorkplaceId().get()).isEqualTo("DUMMY");
		assertThat(maxDaysContiAttOrg.getCode().v()).isEqualTo("003");
		assertThat(maxDaysContiAttOrg.getName().v()).isEqualTo("name");
	
	}
}
