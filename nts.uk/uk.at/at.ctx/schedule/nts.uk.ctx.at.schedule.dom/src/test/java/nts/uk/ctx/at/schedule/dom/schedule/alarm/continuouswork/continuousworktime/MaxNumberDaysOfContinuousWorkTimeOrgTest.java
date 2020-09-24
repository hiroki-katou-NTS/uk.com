package nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.continuousworktime;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;

@RunWith(JMockit.class)
public class MaxNumberDaysOfContinuousWorkTimeOrgTest {
	@Test
	public void getters() {
				
		MaxNumberDaysOfContinuousWorkTimeOrg maxNoDaysOfContinuousAttOrg = MaxNumberDaysOfContinuousWorkTimeHelper.DUMMY_ORG;

		NtsAssert.invokeGetters(maxNoDaysOfContinuousAttOrg);

	}
	
	@Test
	public void create_maxNumberDaysOfContinuousWorkTimeOrg_success() {
				
		MaxNumberDaysOfContinuousWorkTimeOrg maxNoDaysOfContinuousAttOrg = MaxNumberDaysOfContinuousWorkTimeHelper.DUMMY_ORG;
		
		assertThat(maxNoDaysOfContinuousAttOrg).extracting(
				org -> org.getTargeOrg().getUnit(),
				org -> org.getTargeOrg().getWorkplaceId().get(),
				org -> org.getCode().v(),
				org -> org.getName().v(),
				org -> org.getMaxNumberDaysOfContinuousWorktime().getNumberOfDays().v()
				).containsExactly(
						TargetOrganizationUnit.WORKPLACE,
						"517ef7f8-77d0-4eb0-b539-05e03a23f9e5",
						"003",
						"通常勤務１",
						5);
		
		List<String> worktimeCdLst = maxNoDaysOfContinuousAttOrg.getMaxNumberDaysOfContinuousWorktime()
				.getWorktimeCodeLst().stream().map(c -> c.toString()).collect(Collectors.toList());
		
		assertThat(worktimeCdLst.equals(Arrays.asList("001", "002", "003")));
	
	}
}
