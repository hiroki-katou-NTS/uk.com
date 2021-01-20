package nts.uk.ctx.office.dom.favorite.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.office.dom.favorite.adapter.EmployeeBasicImport;
import nts.uk.ctx.office.dom.favorite.adapter.EmployeeJobHistImport;
import nts.uk.ctx.office.dom.favorite.adapter.SequenceMasterImport;
import nts.uk.ctx.office.dom.favorite.adapter.WorkplaceInforImport;
import nts.uk.ctx.office.dom.favorite.service.PersonalInfomationDomainService.Require;
import static org.assertj.core.api.Assertions.assertThat;
@RunWith(JMockit.class)
public class PersonalInfomationDomainServiceTest {

	@Injectable
	private Require require;

	// Map<String, WorkplaceInforImport> empty
	@Test
	public void getPersonalInfomationTest1() {
		//given
		//[R-1]
		Map<String, String> employeesWorkplaceId = new HashMap<>();
		employeesWorkplaceId.put("key", "value");
		List<String> sIds = new ArrayList<>();
		sIds.add("sid");
		List<String> workSplaceId = new ArrayList<>();
		sIds.add("value");
		//[R-2]
		WorkplaceInforImport info = new WorkplaceInforImport(
				"workplaceId",
				"hierarchyCode",
				"workplaceCode",
				"workplaceName",
				"workplaceDisplayName",
				"workplaceGenericName",
				"workplaceExternalCode"
				);
		Map<String, WorkplaceInforImport> workplaceInfor = new HashMap<>();
		workplaceInfor.put("key", info);
		
		//[R-3]
		EmployeeJobHistImport emp = EmployeeJobHistImport.builder().build();
		Map<String, EmployeeJobHistImport> positionBySidsAndBaseDate = new HashMap<>();
		positionBySidsAndBaseDate.put("key", emp);
		
		//[R-4]
		SequenceMasterImport sequence = new SequenceMasterImport();
		List<SequenceMasterImport> rankOfPosition = new ArrayList<>();
		rankOfPosition.add(sequence);
		
		//[R-5] 
		EmployeeBasicImport empImport = new EmployeeBasicImport("sid", "pid", "name", "code");
		Map<String, EmployeeBasicImport> personalInformation = new HashMap<>();
		personalInformation.put("key", empImport);
		new Expectations() {
			{
				require.getEmployeesWorkplaceId(sIds, (GeneralDate) any);
				result = employeesWorkplaceId;
			}
			{
				require.getWorkplaceInfor(workSplaceId, (GeneralDate) any);
				result = workplaceInfor;
			}
			{
				require.getPositionBySidsAndBaseDate(sIds, (GeneralDate) any);
				result = positionBySidsAndBaseDate;
			}
			{
				require.getRankOfPosition();
				result = rankOfPosition;
			}
			{
				require.getPersonalInformation(sIds);
				result = personalInformation;
			}
		};
		
		//then
		val result = PersonalInfomationDomainService.getPersonalInfomation(require, sIds, GeneralDate.today());
		
		
		assertThat(result).isNotEmpty();
	}

	// Map<String, WorkplaceInforImport> not empty
	public void getPersonalInfomationTest2() {
	
	}
}
