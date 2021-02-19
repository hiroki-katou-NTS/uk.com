package nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
/**
 * UnitTest: 組織の就業時間帯の期間内上限勤務
 * @author lan_lt
 *
 */
@RunWith(JMockit.class)
public class MaxDayOfWorkTimeOrganizationTest {
	@Test
	public void getters() {
		val maxNumberDayOfPeriodsOrg = MaxDayOfWorkTimeHelper.DUMMY_ORG;

		NtsAssert.invokeGetters(maxNumberDayOfPeriodsOrg);

	}
	
	@Test
	public void create_maxDayOfWorkTimeOrg_success() {
		val targetOrg = TargetOrgIdenInfor.creatIdentifiWorkplace("DUMMY");		
		val workTimeCodes = Arrays.asList(new WorkTimeCode("001"),
				new WorkTimeCode("002"),
				new WorkTimeCode("003")
				);
		val maxDayOfWorkTime =  new MaxDayOfWorkTime(workTimeCodes, new MaxDay(5));
		val maxDayOfWorkTimeOrg = new MaxDayOfWorkTimeOrganization(
				TargetOrgIdenInfor.creatIdentifiWorkplace("DUMMY"),
				new MaxDayOfWorkTimeCode("code"), new MaxDayOfWorkTimeName("name"), maxDayOfWorkTime);
		
		assertThat(maxDayOfWorkTimeOrg.getTargeOrg()).isEqualTo(targetOrg);
		assertThat(maxDayOfWorkTimeOrg.getCode().v()).isEqualTo("code");
		assertThat(maxDayOfWorkTimeOrg.getName().v()).isEqualTo("name");
		assertThat(maxDayOfWorkTimeOrg.getMaxDayOfWorkTime()).isEqualTo(maxDayOfWorkTime);
	}
}
