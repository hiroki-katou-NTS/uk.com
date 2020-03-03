package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;

@RunWith(JMockit.class)
public class ShiftMasterOrganizationTest {
	
	@Test
	public void ShiftMasterOrganization_inv1_emptyList() {
		
		NtsAssert.systemError(() -> {
			
			new ShiftMasterOrganization(
					null,  //dummy
					ShiftMasterOrgHelper.getTargetOrgIdenInforEmpty(), //dummy
					Collections.emptyList());
		});
	}
	
	@Test
	public void ShiftMasterOrganization_inv2_duplicated() {
		List<String> listShiftMaterCode =new ArrayList<>();
		listShiftMaterCode.add("123");//dummy
		listShiftMaterCode.add("123");//dummy
		NtsAssert.systemError(() -> {
			new ShiftMasterOrganization(
					null,  //dummy
					ShiftMasterOrgHelper.getTargetOrgIdenInforEmpty(), //dummy
					listShiftMaterCode);
		});
	}
	

	@Test
	public void testChange() {
		List<String> listShiftMaterCode =new ArrayList<>();
		listShiftMaterCode.add("123");//dummy
		listShiftMaterCode.add("124");//dummy
		ShiftMasterOrganization shiftMasterOrg = ShiftMasterOrgHelper.getShiftMasterOrgEmpty();
		shiftMasterOrg.change(listShiftMaterCode);
		assertThat(shiftMasterOrg.getListShiftMaterCode()).isEqualTo(listShiftMaterCode);
	}

	@Test
	public void testCopy() {
		ShiftMasterOrganization shiftMasterOrg = ShiftMasterOrgHelper.getShiftMasterOrgEmpty();
		TargetOrgIdenInfor targetOrgIdenInforNew = new TargetOrgIdenInfor(
				TargetOrganizationUnit.WORKPLACE_GROUP, //dummy
				"workplaceId", //dummy
				"workplaceGroupId");//dummy
		ShiftMasterOrganization newShiftMasterOrganization = shiftMasterOrg.copy(targetOrgIdenInforNew);
		assertThat(newShiftMasterOrganization.getCompanyId()).isEqualTo(shiftMasterOrg.getCompanyId());
		assertThat(newShiftMasterOrganization.getListShiftMaterCode()).isEqualTo(shiftMasterOrg.getListShiftMaterCode());
		assertThat(newShiftMasterOrganization.getTargetOrg()).isEqualTo(targetOrgIdenInforNew);
	}

	@Test
	public void getters() {
		ShiftMasterOrganization shiftMasterOrg = ShiftMasterOrgHelper.getShiftMasterOrgEmpty();
		NtsAssert.invokeGetters(shiftMasterOrg);
	}
}
