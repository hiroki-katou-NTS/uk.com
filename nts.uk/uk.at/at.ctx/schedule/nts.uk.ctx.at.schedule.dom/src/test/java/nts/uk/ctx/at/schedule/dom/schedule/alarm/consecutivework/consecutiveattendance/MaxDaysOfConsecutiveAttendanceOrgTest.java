package nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveattendance;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.ConsecutiveNumberOfDays;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutivework.MaxDaysOfConsecutiveAttendance;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutivework.MaxDaysOfConsecutiveAttendanceOrganization;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

@RunWith(JMockit.class)
public class MaxDaysOfConsecutiveAttendanceOrgTest {
	@Test
	public void getters() {
		val maxNoDaysOfContinuousAttOrg = new MaxDaysOfConsecutiveAttendanceOrganization(
				TargetOrgIdenInfor.creatIdentifiWorkplace("DUMMY"),
				new MaxDaysOfConsecutiveAttendance(new ConsecutiveNumberOfDays(5)));

		NtsAssert.invokeGetters(maxNoDaysOfContinuousAttOrg);

	}
	
	@Test
	public void create_maxNumberDaysOfContinuousAttendanceOrg_success() {
		val targetOrg = TargetOrgIdenInfor.creatIdentifiWorkplace("DUMMY");
		val maxDaysOfConsAtt = new MaxDaysOfConsecutiveAttendance(new ConsecutiveNumberOfDays(5));
		val maxDaysConsAttOrg = new MaxDaysOfConsecutiveAttendanceOrganization(targetOrg, maxDaysOfConsAtt);
		
		assertThat(maxDaysConsAttOrg.getTargeOrg()).isEqualTo(targetOrg);
		assertThat(maxDaysConsAttOrg.getNumberOfDays()).isEqualTo(maxDaysOfConsAtt);
	}

}
