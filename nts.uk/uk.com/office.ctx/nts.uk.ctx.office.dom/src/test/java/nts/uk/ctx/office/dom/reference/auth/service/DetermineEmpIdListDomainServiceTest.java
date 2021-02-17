package nts.uk.ctx.office.dom.reference.auth.service;

import java.util.ArrayList;
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

	/**
	 * 在席照会で参照できる権限の指定 が  ない
	 */
	@Test
	public void determineReferencedTest1_1() {
		// given
		List<String> sids = new ArrayList<String>();
		sids.add("sid");
		
		// [R-1]
		Optional<SpecifyAuthInquiry> specifyAuthInquiry = Optional.empty();
		
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
	
	/**
	 * 在席照会で参照できる権限の指定があるが、見られる職位IDListがEmpty
	 */
	@Test
	public void determineReferencedTest1_2() {
		// given
		List<String> sids = new ArrayList<String>();
		sids.add("sid");
		
		// [R-1]
		Optional<SpecifyAuthInquiry> specifyAuthInquiry = DetermineEmpIdListDomainServiceTestHelper.mockRequireGetByCidAndRoleId();
		
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
		List<String> sids = new ArrayList<String>();
		sids.add("loginSid");
		
		// [R-1]
		Optional<SpecifyAuthInquiry> specifyAuthInquiry = DetermineEmpIdListDomainServiceTestHelper.mockRequireGetByCidAndRoleId("positionIdSeen");
		
		// [R-2]
		Map<String, EmployeeJobHistImport> positionBySidsAndBaseDate = DetermineEmpIdListDomainServiceTestHelper.mockRequireGetPositionBySidsAndBaseDate("jobTitleId");
		
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
				"roleId", "loginSid");
		
		// then
		assertThat(result).isEmpty();
	}

	// 見られる職位ID is not Empty
	//// $ not equals ログイン社員ID &&　見られる職位ID not contain($職位Map.get($).職位ID)
	@Test
	public void determineReferencedTest3() {
		// given
		List<String> sids = new ArrayList<String>();
		sids.add("loginSid");
		
		// [R-1]
		Optional<SpecifyAuthInquiry> specifyAuthInquiry = DetermineEmpIdListDomainServiceTestHelper.mockRequireGetByCidAndRoleId("positionIdSeen");
		
		// [R-2]
		Map<String, EmployeeJobHistImport> positionBySidsAndBaseDate = DetermineEmpIdListDomainServiceTestHelper.mockRequireGetPositionBySidsAndBaseDate("jobTitleId");
		
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
				"roleId", "notLoginSid");
		
		// then
		assertThat(result).isEmpty();
	}
	
	// 見られる職位ID is not Empty
	//// $ not equals ログイン社員ID &&　見られる職位ID contain($職位Map.get($).職位ID)
	@Test
	public void determineReferencedTest4() {
		// given
		List<String> sids = new ArrayList<String>();
		sids.add("loginSid");
		
		// [R-1]
		Optional<SpecifyAuthInquiry> specifyAuthInquiry = DetermineEmpIdListDomainServiceTestHelper.mockRequireGetByCidAndRoleId("positionIdSeen");
		
		// [R-2]
		Map<String, EmployeeJobHistImport> positionBySidsAndBaseDate = DetermineEmpIdListDomainServiceTestHelper.mockRequireGetPositionBySidsAndBaseDate("positionIdSeen");
		
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
				"roleId", "notLoginSid");
		
		// then
		assertThat(result).isNotEmpty();
	}
}
