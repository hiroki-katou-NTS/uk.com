package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.GetUsableShiftMasterService.Require;

@RunWith(JMockit.class)
public class GetUsableShiftMasterServiceTest {
	@Injectable
	private Require require;

	/*
	 * if targetOrg == null
	 */
	@Test
	public void testGetUsableShiftMaster_1() {
		String companyId = "0001";

		GetUsableShiftMasterService.getUsableShiftMaster(require, companyId,
				null);
	}

	/*
	 * if targetOrg != null
	 *  *get(会社ID, List<シフトマスタコード>).isPresent() ==true
	 */
	@Test
	public void testGetUsableShiftMaster_2() {
		String companyId = "0001";
		new Expectations() {
			{
				require.getByTargetOrg(companyId, (TargetOrgIdenInfor) any);
				result = Optional.empty();
			}
		};

		GetUsableShiftMasterService.getUsableShiftMaster(require, companyId,
				ShiftMasterOrgHelper.getTargetOrgIdenInforEmpty());
	}
	
	/*
	 * if targetOrg != null
	 *  *get(会社ID, List<シフトマスタコード>).isPresent() == false
	 */
	@Test
	public void testGetUsableShiftMaster_3() {
		String companyId = "0001";
		new Expectations() {
			{
				require.getByTargetOrg(companyId, (TargetOrgIdenInfor) any);
				result = Optional.of(ShiftMasterOrgHelper.getShiftMasterOrgEmpty());
			}
		};

		GetUsableShiftMasterService.getUsableShiftMaster(require, companyId,
				ShiftMasterOrgHelper.getTargetOrgIdenInforEmpty());
	}
}
