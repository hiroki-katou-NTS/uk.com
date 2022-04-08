package nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.UpdateApprovalRootHistoryDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.UpdateApprovalRootHistoryDomainService.Require;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.OperationMode;

@RunWith(JMockit.class)
public class UpdateApprovalRootHistoryDomainServiceTest {

	@Injectable
	private Require require;

	/**
	 * [1]登録する
	 */
	@Test
	public void testRegister() {

		// given
		GeneralDate baseDate = GeneralDate.today();
		new Expectations() {
			{
				require.getHistoryWithStartDate(DomainServiceTestHelper.SID, baseDate);
				result = Arrays
						.asList(DomainServiceTestHelper.mockPersonApprovalRoot(OperationMode.SUPERIORS_EMPLOYEE));
			};
			{
				require.getHistoryWithEndDate(DomainServiceTestHelper.SID, baseDate);
				result = Arrays.asList(DomainServiceTestHelper.mockPersonApprovalRoot(OperationMode.PERSON_IN_CHARGE));
			};
		};

		// when
		List<AtomTask> atomTasks = UpdateApprovalRootHistoryDomainService.register(require, DomainServiceTestHelper.SID,
				baseDate);

		// then
		assertThat(atomTasks.size()).isEqualTo(2);
		NtsAssert.atomTask(() -> atomTasks.get(0), any -> require.deletePersonApprovalRoots(any.get()));
		NtsAssert.atomTask(() -> atomTasks.get(1), any -> require.updatePersonApprovalRoot(any.get()));
	}

	/**
	 * [1]登録する
	 * 
	 * $個人別承認ルートList = empty
	 */
	@Test
	public void testRegisterApproveListEmpty() {

		// given
		GeneralDate baseDate = GeneralDate.today();
		new Expectations() {

			{
				require.getHistoryWithEndDate(DomainServiceTestHelper.SID, baseDate);
				result = Arrays.asList(DomainServiceTestHelper.mockPersonApprovalRoot(OperationMode.PERSON_IN_CHARGE));
			};
		};

		// when
		List<AtomTask> atomTasks = UpdateApprovalRootHistoryDomainService.register(require, DomainServiceTestHelper.SID,
				baseDate);

		// then
		assertThat(atomTasks.size()).isEqualTo(1);
		NtsAssert.atomTask(() -> atomTasks.get(0), any -> require.updatePersonApprovalRoot(any.get()));
	}

	/**
	 * [1]登録する
	 * 
	 * 前の履歴List = empty
	 */
	@Test
	public void testRegisterPreviousHistoriesEmpty() {
		
		// given
		GeneralDate baseDate = GeneralDate.today();
		new Expectations() {
			{
				require.getHistoryWithStartDate(DomainServiceTestHelper.SID, baseDate);
				result = Arrays
						.asList(DomainServiceTestHelper.mockPersonApprovalRoot(OperationMode.SUPERIORS_EMPLOYEE));
			};
		};

		// when
		List<AtomTask> atomTasks = UpdateApprovalRootHistoryDomainService.register(require, DomainServiceTestHelper.SID,
				baseDate);

		// then
		assertThat(atomTasks.size()).isEqualTo(1);
		NtsAssert.atomTask(() -> atomTasks.get(0), any -> require.deletePersonApprovalRoots(any.get()));
	}
}
