package nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.limitworktime;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;

@RunWith(JMockit.class)
public class MaxDayOfWorkTimeOrganizationTest {
	@Test
	public void getters() {
		MaxDayOfWorkTimeOrganization maxNumberOfWorkingDayOfPeriodsOrg = MaxDayOfWorkTimeHelper.DUMMY_ORG;

		NtsAssert.invokeGetters(maxNumberOfWorkingDayOfPeriodsOrg);

	}
	
	@Test
	public void create_maxNumberOfWorkingDayOfPeriodsCom_success() {
		MaxDayOfWorkTimeOrganization maxNumberOfWorkingDayOfPeriodsOrg = MaxDayOfWorkTimeHelper.DUMMY_ORG;
		
		assertThat(maxNumberOfWorkingDayOfPeriodsOrg).extracting(
			 org -> org.getTargeOrg().getUnit(),
			 org -> org.getTargeOrg().getWorkplaceId().get(),
			 org -> org.getCode().v(),
			 org -> org.getName().v(),
			 org -> org.getMaxNumberOfWorkingDayOfPeriods().getMaxNumberOfWorkingDay().v()
			 ).containsExactly(
					 TargetOrganizationUnit.WORKPLACE,
					 "517ef7f8-77d0-4eb0-b539-05e03a23f9e5",
					 "003",
					 "シフト１",
					 3);
	}
}
