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
	 * 1: 組織別シフトマスタ = require.組織別シフトマスタを取得する( 会社ID, 対象組織 ).ispresent == false 2:
	 * *getAll(会社ID)
	 * 
	 */
	@Test
	public void testGetUsableShiftMaster_2() {
		TargetOrgIdenInfor targetOrgIdenInfor =  TargetOrgIdenInfor.creatIdentifiWorkplace("workplaceId") ;
		List<ShiftMasterDto> results = new ArrayList<>();
		results.add(new ShiftMasterDto());
		new Expectations() {
			{
				require.getByTargetOrg((TargetOrgIdenInfor) any);

				require.getAllByCid();
				result = results;

			}
		};

		assertThat(GetUsableShiftMasterService.getUsableShiftMaster(require, targetOrgIdenInfor)).isEqualTo(results);
	}

	/*
	 * 1: 組織別シフトマスタ = require.組織別シフトマスタを取得する( 会社ID, 対象組織 ).ispresent == true 3:
	 * *get(会社ID, List<シフトマスタコード>)
	 * 
	 */
	@Test
	public void testGetUsableShiftMaster_3() {
		TargetOrgIdenInfor targetOrgIdenInfor = TargetOrgIdenInfor.creatIdentifiWorkplace("workplaceId") ;
		ShiftMasterOrganization shiftMasterOrg = new ShiftMasterOrganization("companyId", targetOrgIdenInfor,
				Arrays.asList("123"));
		List<ShiftMasterDto> results = new ArrayList<>();
		results.add(new ShiftMasterDto());
		new Expectations() {
			{
				require.getByTargetOrg((TargetOrgIdenInfor) any);
				result = Optional.of(shiftMasterOrg);

				require.getByListShiftMaterCd(shiftMasterOrg.getListShiftMaterCode());
				result = results;
			}
		};

		assertThat(GetUsableShiftMasterService.getUsableShiftMaster(require, targetOrgIdenInfor)).isEqualTo(results);
	}
}
