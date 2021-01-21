package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.GetEmpCanReferBySpecOrganizationService.Require;
@RunWith(JMockit.class)
public class GetEmpCanReferBySpecOrganizationServiceTest {

	@Injectable
	private Require require;
	
	/**
	 * WORKPLACE_GROUP
	 * require.職場グループで参照可能な所属社員を取得する( 基準日, 社員ID, 職場グループ ) is empty
	 * require.社員を並び替える( 社員IDリスト, システム区分.就業, null, 基準日, null )	is empty
	 */
	@Test
	public void testGetListEmpID() {
		GeneralDate referenceDate = GeneralDate.today();
		String epmloyeeId = "epmloyeeId";
		TargetOrgIdenInfor targetOrgIdenInfor =TargetOrgIdenInfor.creatIdentifiWorkplaceGroup("workplaceGroupId");
		new Expectations() {
			{
				require.getReferableEmp(referenceDate, epmloyeeId, targetOrgIdenInfor.getWorkplaceGroupId().get());
				
			}
		};
		List<String> listData = GetEmpCanReferBySpecOrganizationService.getListEmpID(require, referenceDate, epmloyeeId, targetOrgIdenInfor);
		assertThat(listData.isEmpty()).isTrue();
	}
	
	/**
	 * WORKPLACE_GROUP
	 * require.職場グループで参照可能な所属社員を取得する( 基準日, 社員ID, 職場グループ ) not empty
	 * require.社員を並び替える( 社員IDリスト, システム区分.就業, null, 基準日, null )	not empty
	 */
	@Test
	public void testGetListEmpID_1() {
		GeneralDate referenceDate = GeneralDate.today();
		String epmloyeeId = "epmloyeeId";
		TargetOrgIdenInfor targetOrgIdenInfor =TargetOrgIdenInfor.creatIdentifiWorkplaceGroup("workplaceGroupId");
		List<String> listEmpIdByGetReferableEmp = Arrays.asList("emp1","emp2","emp9","emp3","emp2","emp5","emp6","emp8","emp10");
		List<String> listSortEmployee = Arrays.asList("emp3","emp1","emp6","emp8","emp9","emp10");
		new Expectations() {
			{
				require.getReferableEmp(referenceDate, epmloyeeId, targetOrgIdenInfor.getWorkplaceGroupId().get());
				result = listEmpIdByGetReferableEmp;
				
				require.sortEmployee(listEmpIdByGetReferableEmp, 0, null, referenceDate, null);
				result = listSortEmployee;
			}
		};
		List<String> listData = GetEmpCanReferBySpecOrganizationService.getListEmpID(require, referenceDate, epmloyeeId, targetOrgIdenInfor);
		assertThat(listData).isEqualTo(listSortEmployee);
	}
	
	/**
	 * WORKPLACE
	 */
	@Test
	public void testGetListEmpID_2() {
		GeneralDate referenceDate = GeneralDate.today();
		String epmloyeeId = "epmloyeeId";
		TargetOrgIdenInfor targetOrgIdenInfor =TargetOrgIdenInfor.creatIdentifiWorkplace("workplaceId");
		String roleID = "roleId";
		List<String> listEmpId = Arrays.asList("emp1","emp2");
		
		new Expectations() {
			{
				require.getRoleID(referenceDate, epmloyeeId);
				result = roleID;
				
				require.searchEmployee((RegulationInfoEmpQuery) any , roleID);	
				result = listEmpId;
			}
		};
		List<String> listData = GetEmpCanReferBySpecOrganizationService.getListEmpID(require, referenceDate, epmloyeeId, targetOrgIdenInfor);
		assertThat( listData ).containsExactlyElementsOf( listEmpId );

	}

}
