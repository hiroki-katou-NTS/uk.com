package nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.limitworktime;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;

@RunWith(JMockit.class)
public class MaxDayOfWorkTimeOrganizationTest {
	@Test
	public void getters() {
		val maxNumberDayOfPeriodsOrg = MaxDayOfWorkTimeHelper.DUMMY_ORG;

		NtsAssert.invokeGetters(maxNumberDayOfPeriodsOrg);

	}
	
	@Test
	public void create_maxNumberOfWorkingDayOfPeriodsCom_success() {
		val maxNumberDayOfPeriodsOrg = MaxDayOfWorkTimeHelper.DUMMY_ORG;
		
		assertThat(maxNumberDayOfPeriodsOrg.getTargeOrg().getUnit()).isEqualTo(TargetOrganizationUnit.WORKPLACE);
		assertThat(maxNumberDayOfPeriodsOrg.getTargeOrg().getWorkplaceId().get()).isEqualTo("DUMMY");
		assertThat(maxNumberDayOfPeriodsOrg.getCode().v()).isEqualTo("003");
		assertThat(maxNumberDayOfPeriodsOrg.getName().v()).isEqualTo("シフト１");
	}
}
