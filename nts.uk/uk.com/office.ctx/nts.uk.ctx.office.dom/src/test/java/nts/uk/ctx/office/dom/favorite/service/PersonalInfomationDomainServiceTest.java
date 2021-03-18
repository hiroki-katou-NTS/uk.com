package nts.uk.ctx.office.dom.favorite.service;

import java.util.ArrayList;
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
		
		List<String> sIds = new ArrayList<>();
		sIds.add("sid");
		List<String> workSplaceId = new ArrayList<>();
		workSplaceId.add("wspId");
		
		//[R-1]
		Map<String, String> employeesWorkplaceId = FavoriteSpecifyDomainServiceTestHelper.mockRequireGetEmployeesWorkplaceId();
		
		//[R-2]
		Map<String, WorkplaceInforImport> workplaceInfor = FavoriteSpecifyDomainServiceTestHelper.mockRequireGetWorkplaceInfo();
		
		//[R-3]
		Map<String, EmployeeJobHistImport> positionBySidsAndBaseDate = FavoriteSpecifyDomainServiceTestHelper.mockRequireGetPositionBySidsAndBaseDate();
		
		//[R-4]
		List<SequenceMasterImport> rankOfPosition = FavoriteSpecifyDomainServiceTestHelper.mockRequireGetRankOfPosition();
		
		//[R-5] 
		Map<String, EmployeeBasicImport> personalInformation = FavoriteSpecifyDomainServiceTestHelper.mockRequireGetPersonalInformation();
		
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
		List<String> sIds = new ArrayList<>();
		sIds.add("sid2"); // In FavoriteSpecifyDomainServiceTestHelper we mocked all sid is [sid]. Here we have Sids = [sid2] that make 職場情報 empty
		List<String> workSplaceId = new ArrayList<>();
		workSplaceId.add("wspId");
		
		//[R-1]
		Map<String, String> employeesWorkplaceId = FavoriteSpecifyDomainServiceTestHelper.mockRequireGetEmployeesWorkplaceId();
		
		//[R-2]
		Map<String, WorkplaceInforImport> workplaceInfor = FavoriteSpecifyDomainServiceTestHelper.mockRequireGetWorkplaceInfo();
		
		//[R-3]
		Map<String, EmployeeJobHistImport> positionBySidsAndBaseDate = FavoriteSpecifyDomainServiceTestHelper.mockRequireGetPositionBySidsAndBaseDate();
		
		//[R-4]
		List<SequenceMasterImport> rankOfPosition = FavoriteSpecifyDomainServiceTestHelper.mockRequireGetRankOfPosition();
		
		//[R-5] 
		Map<String, EmployeeBasicImport> personalInformation = FavoriteSpecifyDomainServiceTestHelper.mockRequireGetPersonalInformation();
		
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
