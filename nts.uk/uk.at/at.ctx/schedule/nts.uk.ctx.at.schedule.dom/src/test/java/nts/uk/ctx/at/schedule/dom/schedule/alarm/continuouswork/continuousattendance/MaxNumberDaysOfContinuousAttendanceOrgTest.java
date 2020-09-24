package nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.continuousattendance;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.NumberOfConsecutiveDays;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;

@RunWith(JMockit.class)
public class MaxNumberDaysOfContinuousAttendanceOrgTest {
	@Test
	public void getters() {
		MaxNumberDaysOfContinuousAttendanceOrg maxNoDaysOfContinuousAttOrg = new MaxNumberDaysOfContinuousAttendanceOrg(
				TargetOrgIdenInfor.creatIdentifiWorkplace("517ef7f8-77d0-4eb0-b539-05e03a23f9e5"),
				new MaxNumberDaysOfContinuousAttendance(new NumberOfConsecutiveDays(5)));

		NtsAssert.invokeGetters(maxNoDaysOfContinuousAttOrg);

	}
	
	@Test
	public void create_maxNumberDaysOfContinuousAttendanceOrg_success() {
		MaxNumberDaysOfContinuousAttendanceOrg maxNoDaysOfContinuousAttOrg = new MaxNumberDaysOfContinuousAttendanceOrg(
				TargetOrgIdenInfor.creatIdentifiWorkplace("517ef7f8-77d0-4eb0-b539-05e03a23f9e5"),
				new MaxNumberDaysOfContinuousAttendance(new NumberOfConsecutiveDays(5)));
		
		assertThat(maxNoDaysOfContinuousAttOrg.getTargeOrg().getWorkplaceId().get()).isEqualTo("517ef7f8-77d0-4eb0-b539-05e03a23f9e5");
		
		assertThat(maxNoDaysOfContinuousAttOrg.getTargeOrg().getUnit()).isEqualTo(TargetOrganizationUnit.WORKPLACE);
		
		assertThat(maxNoDaysOfContinuousAttOrg.getNumberOfDays().getNumberOfDays().v()).isEqualTo(5);
	
	}

}
