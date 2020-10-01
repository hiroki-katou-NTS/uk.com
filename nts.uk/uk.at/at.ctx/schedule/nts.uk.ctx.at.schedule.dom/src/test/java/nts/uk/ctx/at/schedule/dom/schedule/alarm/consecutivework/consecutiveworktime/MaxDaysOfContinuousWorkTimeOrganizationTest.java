package nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.ConsecutiveNumberOfDays;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
/**
 * UnitTest: 組織の就業時間帯の連続勤務できる上限日数
 * @author lan_lt
 *
 */
@RunWith(JMockit.class)
public class MaxDaysOfContinuousWorkTimeOrganizationTest {
	@Test
	public void getters() {
		val maxDaysContiAttOrg = MaxNumberDaysOfContinuousWorkTimeHelper.DUMMY_ORG;
		NtsAssert.invokeGetters(maxDaysContiAttOrg);

	}
	
	@Test
	public void create_maxNumberDaysOfContinuousWorkTimeOrg_success() {
		val targetOrg = TargetOrgIdenInfor.creatIdentifiWorkplace("DUMMY");
		val maxDaysConsWorkTime = new MaxDaysOfConsecutiveWorkTime(
				Arrays.asList(new WorkTimeCode("001"), new WorkTimeCode("002"), new WorkTimeCode("003")),
				new ConsecutiveNumberOfDays(5));
		val maxDaysContiAttOrg = new MaxDaysOfContinuousWorkTimeOrganization(
				targetOrg, new ConsecutiveWorkTimeCode("003"), 
				new ConsecutiveWorkTimeName("name"), maxDaysConsWorkTime);

		assertThat(maxDaysContiAttOrg.getTargeOrg()).isEqualTo(targetOrg);
		assertThat(maxDaysContiAttOrg.getCode().v()).isEqualTo("003");
		assertThat(maxDaysContiAttOrg.getName().v()).isEqualTo("name");
		assertThat(maxDaysContiAttOrg.getMaxDaysContiWorktime()).isEqualTo(maxDaysConsWorkTime);
	
	}
}
