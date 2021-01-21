package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.CopyShiftMasterByOrgService.Require;

@RunWith(JMockit.class)
public class CopyShiftMasterByOrgServiceTest {

	@Injectable
	private Require require;

	/**
	 * if checkExists == true && overwrite == false
	 */
	@Test
	public void testCopyShiftMasterByOrg_1() {
		String companyId = "companyID"; // dummy
		 
		TargetOrgIdenInfor targetOrg1 =TargetOrgIdenInfor.creatIdentifiWorkplace("workplaceId") ;
		ShiftMasterOrganization shiftMaterOrg = new ShiftMasterOrganization(companyId, targetOrg1, Arrays.asList("123"));
		TargetOrgIdenInfor targetOrg = TargetOrgIdenInfor.creatIdentifiWorkplace("workplaceId1") ;
		boolean overwrite = false;

		new Expectations() {
			{
				require.exists(companyId, targetOrg);
				result = true;
			}
		};

		
		
		assertThat(CopyShiftMasterByOrgService
					.copyShiftMasterByOrg(require, companyId, shiftMaterOrg, targetOrg, overwrite).isPresent()).isFalse();

	}

	/**
	 * if checkExists == true && overwrite == true
	 */
	@Test
	public void testCopyShiftMasterByOrg_2() {
		String companyId = "companyID"; // dummy
		TargetOrgIdenInfor targetOrg1 = TargetOrgIdenInfor.creatIdentifiWorkplace("workplaceId") ;
		ShiftMasterOrganization shiftMaterOrg = new ShiftMasterOrganization(companyId, targetOrg1, Arrays.asList("123"));
		TargetOrgIdenInfor targetOrg =  TargetOrgIdenInfor.creatIdentifiWorkplace("workplaceId1") ;
		boolean overwrite = true;

		new Expectations() {
			{
				require.exists(companyId, targetOrg);
				result = true;

			}
		};

		ShiftMasterOrganization shiftMaterOrgNew = shiftMaterOrg.copy(targetOrg);
		NtsAssert.atomTask(
				() -> CopyShiftMasterByOrgService
						.copyShiftMasterByOrg(require, companyId, shiftMaterOrg, targetOrg, overwrite).get(),
				any -> require.delete(companyId, targetOrg), any -> require.insert(shiftMaterOrgNew));

	}

	/**
	 * if checkExists == false
	 */
	@Test
	public void testCopyShiftMasterByOrg_3() {
		String companyId = "companyID"; // dummy
		TargetOrgIdenInfor targetOrg1 =  TargetOrgIdenInfor.creatIdentifiWorkplace("workplaceId") ;
		ShiftMasterOrganization shiftMaterOrg = new ShiftMasterOrganization(companyId, targetOrg1, Arrays.asList("123"));
		TargetOrgIdenInfor targetOrg =  TargetOrgIdenInfor.creatIdentifiWorkplace("workplaceId1") ;
		boolean overwrite = true; // dummy

		new Expectations() {
			{
				require.exists(companyId, targetOrg);
				result = false;

			}
		};

		ShiftMasterOrganization shiftMaterOrgNew = shiftMaterOrg.copy(targetOrg);
		NtsAssert.atomTask(
				() -> CopyShiftMasterByOrgService
						.copyShiftMasterByOrg(require, companyId, shiftMaterOrg, targetOrg, overwrite).get(),
				any -> require.insert(shiftMaterOrgNew));

	}

}
