package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrganizationUnit;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.GetUsableShiftMasterService.Require;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.dto.ShiftMasterDto;

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

		assertThat(GetUsableShiftMasterService.getUsableShiftMaster(require, companyId,
				ShiftMasterOrgHelper.getTargetOrgIdenInforEmpty()).isEmpty()).isTrue();
	}
	
	/*
	 * if targetOrg != null
	 *  *get(会社ID, List<シフトマスタコード>).isPresent() == false
	 */
	@Test
	public void testGetUsableShiftMaster_3() {
		String companyId = "0001";
		TargetOrgIdenInfor targetOrgIdenInfor =  new TargetOrgIdenInfor(TargetOrganizationUnit.WORKPLACE,
				"workplaceId",
				"workplaceGroupId");
		ShiftMasterOrganization shiftMasterOrg =  new ShiftMasterOrganization(
				"companyId",
				targetOrgIdenInfor,
				Arrays.asList("123"));
		List<ShiftMasterDto> results = new ArrayList<>();
		results.add(new ShiftMasterDto());
		new Expectations() {
			{
				require.getByTargetOrg(companyId, (TargetOrgIdenInfor) any);
				result = Optional.of(shiftMasterOrg);
				
				require.getByListShiftMaterCd(companyId,shiftMasterOrg.getListShiftMaterCode());
				result = results;
			}
		};

		assertThat(GetUsableShiftMasterService.getUsableShiftMaster(require, companyId,
				ShiftMasterOrgHelper.getTargetOrgIdenInforEmpty())).isEqualTo(results);
	}
}
