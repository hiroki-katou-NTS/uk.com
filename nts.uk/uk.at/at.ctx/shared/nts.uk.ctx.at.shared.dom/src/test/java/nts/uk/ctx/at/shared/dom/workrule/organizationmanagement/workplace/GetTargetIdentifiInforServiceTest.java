package nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.common.EmployeeId;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.GetTargetIdentifiInforService.Require;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.adapter.EmpOrganizationImport;

@RunWith(JMockit.class)
public class GetTargetIdentifiInforServiceTest {
	
	@Injectable
	private Require require;

	/**
	 * require.社員の所属組織を取得する( 基準日, list: 社員ID ) is not empty
	 * 職場グループID is empty
	 */
	@Test
	public void testGet() {
		GeneralDate referenceDate = GeneralDate.today();
		String empId ="employeeId";
		List<EmpOrganizationImport> listEmpOrganizationImport = Arrays.asList(new EmpOrganizationImport(new EmployeeId(empId), Optional.empty(), Optional.empty(), "workplaceId", Optional.empty()));
		new Expectations() {
			{
				require.getEmpOrganization(referenceDate, Arrays.asList(empId));
				result = listEmpOrganizationImport;
				
			}
		};
		TargetOrgIdenInfor datas= GetTargetIdentifiInforService.get(require, referenceDate, empId);
		assertThat(datas.getUnit()).isEqualTo(TargetOrganizationUnit.WORKPLACE);
		assertThat(datas.getWorkplaceGroupId().isPresent()).isFalse();
		assertThat(datas.getWorkplaceId().get()).isEqualTo(listEmpOrganizationImport.get(0).getWorkplaceId());
		
	}
	
	/**
	 * require.社員の所属組織を取得する( 基準日, list: 社員ID ) is not empty
	 * 職場グループID is not empty
	 */
	@Test
	public void testGet_1() {
		GeneralDate referenceDate = GeneralDate.today();
		String empId ="employeeId";
		List<EmpOrganizationImport> listEmpOrganizationImport = Arrays.asList(new EmpOrganizationImport(new EmployeeId(empId), Optional.empty(), Optional.empty(), "workplaceId", Optional.of("workplaceGroupId")));
		new Expectations() {
			{
				require.getEmpOrganization(referenceDate, Arrays.asList(empId));
				result = listEmpOrganizationImport;
				
			}
		};
		TargetOrgIdenInfor datas= GetTargetIdentifiInforService.get(require, referenceDate, empId);
		assertThat(datas.getUnit()).isEqualTo(TargetOrganizationUnit.WORKPLACE_GROUP);
		assertThat(datas.getWorkplaceId().isPresent()).isFalse();
		assertThat(datas.getWorkplaceGroupId().get()).isEqualTo(listEmpOrganizationImport.get(0).getWorkplaceGroupId().get());
		
	}

}
