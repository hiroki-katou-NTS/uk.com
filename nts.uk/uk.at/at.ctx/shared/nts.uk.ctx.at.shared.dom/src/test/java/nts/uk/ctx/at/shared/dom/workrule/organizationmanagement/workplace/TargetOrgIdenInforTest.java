package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMasterOrgHelper;

@RunWith(JMockit.class)
public class TargetOrgIdenInforTest {

	@Test
	public void testTargetOrgIdenInfor_1() {
		TargetOrganizationUnit unit = TargetOrganizationUnit.WORKPLACE;// dummy
		String workplaceId = null;
		String workplaceGroupId = null;
		TargetOrgIdenInfor targetOrgIdenInfor = new TargetOrgIdenInfor(unit, workplaceId, workplaceGroupId);
		assertThat(targetOrgIdenInfor.getUnit()).isEqualTo(unit);
		assertThat(targetOrgIdenInfor.getWorkplaceId().isPresent()).isFalse();
		assertThat(targetOrgIdenInfor.getWorkplaceGroupId().isPresent()).isFalse();
	}

	@Test
	public void testTargetOrgIdenInfor_2() {
		TargetOrganizationUnit unit = TargetOrganizationUnit.WORKPLACE;// dummy
		String workplaceId = "workplaceId";
		String workplaceGroupId = "workplaceGroupId";
		TargetOrgIdenInfor targetOrgIdenInfor = new TargetOrgIdenInfor(unit, workplaceId, workplaceGroupId);
		assertThat(targetOrgIdenInfor.getUnit()).isEqualTo(unit);
		assertThat(targetOrgIdenInfor.getWorkplaceId().isPresent()).isTrue();
		assertThat(targetOrgIdenInfor.getWorkplaceGroupId().isPresent()).isTrue();
	}

	@Test
	public void getters() {
		TargetOrgIdenInfor targetOrgIdenInfor = ShiftMasterOrgHelper.getTargetOrgIdenInforEmpty();
		NtsAssert.invokeGetters(targetOrgIdenInfor);
	}

}
