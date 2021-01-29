package nts.uk.ctx.office.dom.favorite.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

	/**
	 * 職場情報 not empty
	 */
	@Test
	public void getPersonalInfomationTest1() {
		//given
		//[R-1]
		Map<String, String> employeesWorkplaceId = new HashMap<>();
		employeesWorkplaceId.put("sid", "wspId");
		List<String> sIds = new ArrayList<>();
		sIds.add("sid");
		List<String> workSplaceId = new ArrayList<>();
		workSplaceId.add("wspId");
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
		workplaceInfor.put("sid", info);
		
		//[R-3]
		EmployeeJobHistImport emp = EmployeeJobHistImport.builder()
				.employeeId("employeeId")
				.jobTitleID("jobTitleID")
				.jobTitleName("jobTitleName")
				.sequenceCode("sequenceCode")
				.startDate(GeneralDate.today())
				.endDate(GeneralDate.today())
				.jobTitleCode("jobTitleCode")
				.build();
		Map<String, EmployeeJobHistImport> positionBySidsAndBaseDate = new HashMap<>();
		positionBySidsAndBaseDate.put("sid", emp);
		
		//[R-4]
		SequenceMasterImport sequence = new SequenceMasterImport("companyId", 0, "sequenceCode", "sequenceName");
		List<SequenceMasterImport> rankOfPosition = new ArrayList<>();
		rankOfPosition.add(sequence);
		
		//[R-5] 
		EmployeeBasicImport empImport = new EmployeeBasicImport("sid", "pid", "name", "code");
		Map<String, EmployeeBasicImport> personalInformation = new HashMap<>();
		personalInformation.put("sid", empImport);
		new Expectations() {
			{
				require.getEmployeesWorkplaceId(sIds, GeneralDate.today());
				result = employeesWorkplaceId;
			}
			{
				require.getWorkplaceInfor(workSplaceId, GeneralDate.today());
				result = workplaceInfor;
			}
			{
				require.getPositionBySidsAndBaseDate(sIds, GeneralDate.today());
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
		
		//when
		val result = PersonalInfomationDomainService.getPersonalInfomation(require, sIds, GeneralDate.today());
		
		//then
		assertThat(result).isNotEmpty();
	}

	/**
	 * 職場情報 empty
	 */
	@Test
	public void getPersonalInfomationTest2() {
		//given
		//[R-1]
		Map<String, String> employeesWorkplaceId = new HashMap<>();
		employeesWorkplaceId.put("sid2", "wspId2");
		List<String> sIds = new ArrayList<>();
		sIds.add("sid");
		List<String> workSplaceId = new ArrayList<>();
		workSplaceId.add("wspId2");
		//[R-2]
		WorkplaceInforImport info = new WorkplaceInforImport(
				"workplaceId2",
				"hierarchyCode2",
				"workplaceCode2",
				"workplaceName2",
				"workplaceDisplayName2",
				"workplaceGenericName2",
				"workplaceExternalCode2"
				);
		Map<String, WorkplaceInforImport> workplaceInfor = new HashMap<>();
		workplaceInfor.put("sid2", info);
		
		//[R-3]
		EmployeeJobHistImport emp = EmployeeJobHistImport.builder()
				.employeeId("employeeId2")
				.jobTitleID("jobTitleID2")
				.jobTitleName("jobTitleName2")
				.sequenceCode("sequenceCode2")
				.startDate(GeneralDate.today())
				.endDate(GeneralDate.today())
				.jobTitleCode("jobTitleCode2")
				.build();
		Map<String, EmployeeJobHistImport> positionBySidsAndBaseDate = new HashMap<>();
		positionBySidsAndBaseDate.put("sid2", emp);
		
		//[R-4]
		SequenceMasterImport sequence = new SequenceMasterImport("companyId2", 0, "sequenceCode2", "sequenceName2");
		List<SequenceMasterImport> rankOfPosition = new ArrayList<>();
		rankOfPosition.add(sequence);
		
		//[R-5] 
		EmployeeBasicImport empImport = new EmployeeBasicImport("sid2", "pid2", "name2", "code2");
		Map<String, EmployeeBasicImport> personalInformation = new HashMap<>();
		personalInformation.put("sid2", empImport);
		new Expectations() {
			{
				require.getEmployeesWorkplaceId(sIds, GeneralDate.today());
				result = employeesWorkplaceId;
			}
			{
				require.getWorkplaceInfor(workSplaceId, GeneralDate.today());
				result = workplaceInfor;
			}
			{
				require.getPositionBySidsAndBaseDate(sIds, GeneralDate.today());
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
		
		//when
		val result = PersonalInfomationDomainService.getPersonalInfomation(require, sIds, GeneralDate.today());
		
		//then
		assertThat(result).isNotEmpty();
	}
}
