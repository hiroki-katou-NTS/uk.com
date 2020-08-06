package nts.uk.ctx.bs.employee.dom.workplace.group.domainservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.bs.employee.dom.workplace.EmployeeAffiliation;
import nts.uk.ctx.bs.employee.dom.workplace.group.domainservice.GetAllEmpWhoBelongWorkplaceGroupService.Require;

@RunWith(JMockit.class)
public class GetAllEmpWhoBelongWorkplaceGroupServiceTest {

	@Injectable
	private Require require;
	
	/**
	 * require.職場グループに所属する職場を取得する( 職場グループID ) is empty
	 */
	@Test
	public void testGetAllEmp() {
		GeneralDate baseDate = GeneralDate.today();
		String workplaceGroupId =  "workplaceGroupId";
		
		new Expectations() {
			{
				require.getWorkplaceBelongsWorkplaceGroup(workplaceGroupId);
			}
		};
		List<EmployeeAffiliation> datas =  GetAllEmpWhoBelongWorkplaceGroupService.getAllEmp(require, baseDate, workplaceGroupId);
		assertThat(datas.isEmpty()).isTrue();
	}
	
	/**
	 * require.職場グループに所属する職場を取得する( 職場グループID ) is not empty
	 * require.職場の所属社員を取得する( 職場ID, 対象期間 ) is empty
	 */
	@Test
	public void testGetAllEmp_1() {
		GeneralDate baseDate = GeneralDate.today();
		String workplaceGroupId =  "workplaceGroupId";
		List<String> listWpkId = Arrays.asList("workplaceId");
		new Expectations() {
			{
				require.getWorkplaceBelongsWorkplaceGroup(workplaceGroupId);
				result = listWpkId;
				
				require.getEmployeesWhoBelongWorkplace(anyString, new DatePeriod(baseDate, baseDate));
			}
		};
		List<EmployeeAffiliation> datas =  GetAllEmpWhoBelongWorkplaceGroupService.getAllEmp(require, baseDate, workplaceGroupId);
		assertThat(datas.isEmpty()).isTrue();
	}
	
	/**
	 * require.職場グループに所属する職場を取得する( 職場グループID ) is not empty
	 * require.職場の所属社員を取得する( 職場ID, 対象期間 ) is not empty
	 */
	@Test
	public void testGetAllEmp_2() {
		GeneralDate baseDate = GeneralDate.today();
		String workplaceGroupId =  "workplaceGroupId";
		List<String> listWpkId = Arrays.asList("workplaceId");
		List<EmployeeInfoData> listEmployeeInfoData = Arrays.asList(new EmployeeInfoData("empId", "empCd", "empName") ); 
		
		new Expectations() {
			{
				require.getWorkplaceBelongsWorkplaceGroup(workplaceGroupId);
				result = listWpkId;
				
				require.getEmployeesWhoBelongWorkplace(anyString, new DatePeriod(baseDate, baseDate));
				result = listEmployeeInfoData;
			}
		};
		List<EmployeeAffiliation> datas =  GetAllEmpWhoBelongWorkplaceGroupService.getAllEmp(require, baseDate, workplaceGroupId);
		assertThat(datas).extracting(d -> d.getEmployeeID(), d -> d.getEmployeeCode().get().v(),
				d -> d.getBusinessName().get(), d -> d.getWorkplaceID(), d -> d.getWorkplaceGroupID().get())
				.containsExactly(tuple("empId","empCd","empName","workplaceId","workplaceGroupId"));
	}

}

