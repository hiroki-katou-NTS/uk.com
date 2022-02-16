package nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalSettingInformation;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmationRootType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.GetSelfApprovalSettingsDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.GetSelfApprovalSettingsDomainService.Require;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApproverOperationSettings;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.OperationMode;

@RunWith(JMockit.class)
public class GetSelfApprovalSettingsDomainServiceTest {

	@Injectable
	private Require require;

	/**
	 * [1]取得する
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testGet() {

		// given
		ApproverOperationSettings operationSetting = DomainServiceTestHelper.mockApproverOperationSettings();
		PersonApprovalRoot personApprovalRoot = DomainServiceTestHelper
				.mockPersonApprovalRoot(OperationMode.PERSON_IN_CHARGE);
		personApprovalRoot.setApprovalId("dummy");
		new Expectations() {
			{
				require.getApprovalPhases(Arrays.asList("dummy"));
				result = Arrays.asList(DomainServiceTestHelper.mockApprovalPhase());
			};
			{
				require.getOperationSetting();
				result = Optional.of(operationSetting);
			};
			{
				require.getPersonApprovalRoots(DomainServiceTestHelper.SID, (GeneralDate) any,
						(List<ApplicationType>) any, (List<ConfirmationRootType>) any);
				result = Arrays.asList(personApprovalRoot);
			};
		};

		// when
		List<ApprovalSettingInformation> datas = GetSelfApprovalSettingsDomainService.get(require,
				DomainServiceTestHelper.SID);

		// then
		assertThat(datas).isNotEmpty();
		assertThat(datas.get(0).getApprovalPhases()).isNotEmpty();
		new Verifications() {
			{
				require.getPersonApprovalRoots(DomainServiceTestHelper.SID, (GeneralDate) any, Collections.emptyList(),
						Collections.emptyList());
				times = 1;
			};
		};
	}

	/**
	 * [1]取得する
	 * 
	 * $自分の承認者の運用設定.isPresent() = false
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testGetOperationSettingEmpty() {

		// given
		PersonApprovalRoot personApprovalRoot = DomainServiceTestHelper
				.mockPersonApprovalRoot(OperationMode.PERSON_IN_CHARGE);
		personApprovalRoot.setApprovalId("dummy");
		new Expectations() {
			{
				require.getApprovalPhases(Arrays.asList("dummy"));
				result = Arrays.asList(DomainServiceTestHelper.mockApprovalPhase());
			};
			{
				require.getPersonApprovalRoots(DomainServiceTestHelper.SID, (GeneralDate) any,
						(List<ApplicationType>) any, (List<ConfirmationRootType>) any);
				result = Arrays.asList(personApprovalRoot);
			};
		};

		// when
		List<ApprovalSettingInformation> datas = GetSelfApprovalSettingsDomainService.get(require,
				DomainServiceTestHelper.SID);

		// then
		assertThat(datas).isNotEmpty();
		assertThat(datas.get(0).getApprovalPhases()).isNotEmpty();
		new Verifications() {
			{
				require.getPersonApprovalRoots(DomainServiceTestHelper.SID, (GeneralDate) any,
						Arrays.asList(ApplicationType.values()), Arrays.asList(ConfirmationRootType.values()));
				times = 1;
			};
		};
	}

	/**
	 * [1]取得する
	 * 
	 * $個人別承認ルートList = empty && $次の履歴.isPresent() = true
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testGetPersonalApprovalRootsEmptyAndHasNextHistory() {

		// given
		GeneralDate baseDate = GeneralDate.today().addDays(1);
		ApproverOperationSettings operationSetting = DomainServiceTestHelper.mockApproverOperationSettings();
		PersonApprovalRoot personApprovalRoot = DomainServiceTestHelper
				.mockPersonApprovalRoot(OperationMode.PERSON_IN_CHARGE);
		personApprovalRoot.getApprRoot().getHistoryItems().get(0).changeSpan(new DatePeriod(baseDate, baseDate));
		personApprovalRoot.setApprovalId("dummy");
		new Expectations() {
			{
				require.getApprovalPhases(Arrays.asList("dummy"));
				result = Arrays.asList(DomainServiceTestHelper.mockApprovalPhase());
			};
			{
				require.getOperationSetting();
				result = Optional.of(operationSetting);
			};
			{
				require.getPersonApprovalRoots(DomainServiceTestHelper.SID, baseDate, (List<ApplicationType>) any,
						(List<ConfirmationRootType>) any);
				result = Arrays.asList(personApprovalRoot);
			};
			{
				require.getSmallestHistFromBaseDate(DomainServiceTestHelper.SID, (GeneralDate) any,
						(List<ApplicationType>) any, (List<ConfirmationRootType>) any);
				result = Optional.of(personApprovalRoot);
			};
		};

		// when
		List<ApprovalSettingInformation> datas = GetSelfApprovalSettingsDomainService.get(require,
				DomainServiceTestHelper.SID);

		// then
		assertThat(datas).isNotEmpty();
		assertThat(datas.get(0).getApprovalPhases()).isNotEmpty();
		new Verifications() {
			{
				require.getPersonApprovalRoots(DomainServiceTestHelper.SID, (GeneralDate) any, Collections.emptyList(),
						Collections.emptyList());
				times = 2;
			};
		};
	}

	/**
	 * [1]取得する
	 * 
	 * $個人別承認ルートList = empty && $次の履歴.isPresent() = false
	 */
	@Test
	public void testGetPersonalApprovalRootsEmptyAndNextHistoryEmpty() {

		// given
		GeneralDate baseDate = GeneralDate.today().addDays(1);
		ApproverOperationSettings operationSetting = DomainServiceTestHelper.mockApproverOperationSettings();
		PersonApprovalRoot personApprovalRoot = DomainServiceTestHelper
				.mockPersonApprovalRoot(OperationMode.PERSON_IN_CHARGE);
		personApprovalRoot.getApprRoot().getHistoryItems().get(0).changeSpan(new DatePeriod(baseDate, baseDate));
		personApprovalRoot.setApprovalId("dummy");
		new Expectations() {
			{
				require.getApprovalPhases(Collections.emptyList());
				result = Arrays.asList(DomainServiceTestHelper.mockApprovalPhase());
			};
			{
				require.getOperationSetting();
				result = Optional.of(operationSetting);
			};
		};

		// when
		List<ApprovalSettingInformation> datas = GetSelfApprovalSettingsDomainService.get(require,
				DomainServiceTestHelper.SID);

		// then
		assertThat(datas).isEmpty();
		new Verifications() {
			{
				require.getPersonApprovalRoots(DomainServiceTestHelper.SID, (GeneralDate) any, Collections.emptyList(),
						Collections.emptyList());
				times = 1;
			};
		};
	}

	/**
	 * [1]取得する
	 * 
	 * $承認フェーズfilter !＝ $承認フェーズList
	 */
	@Test
	@SuppressWarnings("unchecked")
	public void testGetFiltered() {

		// given
		ApproverOperationSettings operationSetting = DomainServiceTestHelper.mockApproverOperationSettings();
		PersonApprovalRoot personApprovalRoot = DomainServiceTestHelper
				.mockPersonApprovalRoot(OperationMode.PERSON_IN_CHARGE);
		personApprovalRoot.setApprovalId("dummy");
		ApprovalPhase approvalPhase = DomainServiceTestHelper.mockApprovalPhase();
		approvalPhase.setPhaseOrder(0);
		new Expectations() {
			{
				require.getApprovalPhases(Arrays.asList("dummy"));
				result = Arrays.asList(approvalPhase);
			};
			{
				require.getOperationSetting();
				result = Optional.of(operationSetting);
			};
			{
				require.getPersonApprovalRoots(DomainServiceTestHelper.SID, (GeneralDate) any,
						(List<ApplicationType>) any, (List<ConfirmationRootType>) any);
				result = Arrays.asList(personApprovalRoot);
			};
		};

		// when
		List<ApprovalSettingInformation> datas = GetSelfApprovalSettingsDomainService.get(require,
				DomainServiceTestHelper.SID);

		// then
		assertThat(datas).isNotEmpty();
		assertThat(datas.get(0).getApprovalPhases()).isNull();
		new Verifications() {
			{
				require.getPersonApprovalRoots(DomainServiceTestHelper.SID, (GeneralDate) any, Collections.emptyList(),
						Collections.emptyList());
				times = 1;
			};
		};
	}
}
