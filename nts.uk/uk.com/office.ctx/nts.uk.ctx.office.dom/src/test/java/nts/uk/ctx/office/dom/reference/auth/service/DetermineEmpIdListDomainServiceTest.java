package nts.uk.ctx.office.dom.reference.auth.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.assertj.core.api.Assertions.assertThat;
import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.office.dom.favorite.adapter.EmployeeJobHistImport;
import nts.uk.ctx.office.dom.reference.auth.SpecifyAuthInquiry;
import nts.uk.ctx.office.dom.reference.auth.service.DetermineEmpIdListDomainService.Require;

@RunWith(JMockit.class)
public class DetermineEmpIdListDomainServiceTest {

	@Injectable
	private Require require;

	// 見られる職位ID is Empty
	@Test
	public void determineReferencedTest1() {
		// given
		// [R-1]
		Optional<SpecifyAuthInquiry> specifyAuthInquiry = Optional.ofNullable(SpecifyAuthInquiry.builder().build());
		
		// [R-2]
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
		List<String> sids = new ArrayList<String>();
		sids.add("sid");
		
		new Expectations() {
			{
				require.getByCidAndRoleId("cid", "roleId");
				result = specifyAuthInquiry;
			}
		};
		
		// when
		val result = DetermineEmpIdListDomainService.determineReferenced(require, sids, GeneralDate.today(), "cid",
				"roleId", "sid");
		
		// then
		assertThat(result).isEmpty();
	}

	// 見られる職位ID is not Empty
	//// $ equals ログイン 社員ID
	@Test
	public void determineReferencedTest2() {
		// given
		// [R-1]
		List<String> positionIdSeens = new ArrayList<String>();
		positionIdSeens.add("positionIdSeen");
		Optional<SpecifyAuthInquiry> specifyAuthInquiry = Optional
				.ofNullable(SpecifyAuthInquiry.builder()
						.cid("cid")
						.employmentRoleId("employmentRoleId")
						.positionIdSeen(positionIdSeens)
						.build());
		
		// [R-2]
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
		List<String> sids = new ArrayList<String>();
		sids.add("sid");
		
		new Expectations() {
			{
				require.getByCidAndRoleId("cid", "roleId");
				result = specifyAuthInquiry;
			}
			{
				require.getPositionBySidsAndBaseDate(sids, GeneralDate.today());
				result = positionBySidsAndBaseDate;
			}
		};
		
		// when
		val result = DetermineEmpIdListDomainService.determineReferenced(require, sids, GeneralDate.today(), "cid",
				"roleId", "sid");
		
		// then
		assertThat(result).isEmpty();
	}

	// 見られる職位ID is not Empty
	//// $ not equals ログイン社員ID &&　見られる職位ID not contain($職位Map.get($).職位ID)
	@Test
	public void determineReferencedTest3() {
		// given
		// [R-1]
		List<String> positionIdSeens = new ArrayList<String>();
		positionIdSeens.add("positionIdSeen");
		Optional<SpecifyAuthInquiry> specifyAuthInquiry = Optional
				.ofNullable(SpecifyAuthInquiry.builder()
						.cid("cid")
						.employmentRoleId("employmentRoleId")
						.positionIdSeen(positionIdSeens)
						.build());
		
		// [R-2]
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
		List<String> sids = new ArrayList<String>();
		sids.add("sid");
		
		new Expectations() {
			{
				require.getByCidAndRoleId("cid", "roleId");
				result = specifyAuthInquiry;
			}
			{
				require.getPositionBySidsAndBaseDate(sids, GeneralDate.today());
				result = positionBySidsAndBaseDate;
			}
		};
		
		// when
		val result = DetermineEmpIdListDomainService.determineReferenced(require, sids, GeneralDate.today(), "cid",
				"roleId", "sid1");
		
		// then
		assertThat(result).isEmpty();
	}
	
	// 見られる職位ID is not Empty
	//// $ not equals ログイン社員ID &&　見られる職位ID contain($職位Map.get($).職位ID)
	@Test
	public void determineReferencedTest4() {
		// given
		// [R-1]
		List<String> positionIdSeens = new ArrayList<String>();
		positionIdSeens.add("positionIdSeen");
		Optional<SpecifyAuthInquiry> specifyAuthInquiry = Optional
				.ofNullable(SpecifyAuthInquiry.builder()
						.cid("cid")
						.employmentRoleId("employmentRoleId")
						.positionIdSeen(positionIdSeens)
						.build());
		
		// [R-2]
		EmployeeJobHistImport emp = EmployeeJobHistImport.builder()
				.employeeId("employeeId")
				.jobTitleID("positionIdSeen")
				.jobTitleName("jobTitleName")
				.sequenceCode("sequenceCode")
				.startDate(GeneralDate.today())
				.endDate(GeneralDate.today())
				.jobTitleCode("jobTitleCode")
				.build();
		Map<String, EmployeeJobHistImport> positionBySidsAndBaseDate = new HashMap<>();
		positionBySidsAndBaseDate.put("sid", emp);
		List<String> sids = new ArrayList<String>();
		sids.add("sid");
		
		new Expectations() {
			{
				require.getByCidAndRoleId("cid", "roleId");
				result = specifyAuthInquiry;
			}
			{
				require.getPositionBySidsAndBaseDate(sids, GeneralDate.today());
				result = positionBySidsAndBaseDate;
			}
		};
		
		// when
		val result = DetermineEmpIdListDomainService.determineReferenced(require, sids, GeneralDate.today(), "cid",
				"roleId", "sid1");
		
		// then
		assertThat(result).isNotEmpty();
	}
}
