package nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.UpdateSelfApprovalRootDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.UpdateSelfApprovalRootDomainService.Require;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.param.ApprovalSettingParam;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.OperationMode;
import nts.uk.ctx.workflow.dom.resultrecord.RecordRootType;

@RunWith(JMockit.class)
public class UpdateSelfApprovalRootDomainServiceTest {

	@Injectable
	private Require require;

	/**
	 * [1]変更登録する
	 */
	@Test
	public void testRegister() {

		// given
		List<ApprovalSettingParam> params = Arrays.asList(DomainServiceTestHelper.mockSettingParam());
		DatePeriod period = DomainServiceTestHelper.PERIOD;
		new Expectations() {
			{
				require.getPersonApprovalRoots(DomainServiceTestHelper.CID, DomainServiceTestHelper.SID, period);
				result = Arrays.asList(DomainServiceTestHelper.mockPersonApprovalRoot(OperationMode.PERSON_IN_CHARGE));
			};
		};

		// when
		List<AtomTask> atomTasks = UpdateSelfApprovalRootDomainService.register(require, DomainServiceTestHelper.CID,
				DomainServiceTestHelper.SID, params, period);

		// then
		assertThat(atomTasks.size()).isEqualTo(2);
		NtsAssert.atomTask(() -> atomTasks.get(0), any -> require.deletePersonApprovalRoots(any.get()));
		NtsAssert.atomTask(() -> atomTasks.get(1), any -> require.insertAll(any.get(), any.get()));
		new Verifications() {
			{
				require.createDailyApprover(DomainServiceTestHelper.SID, (RecordRootType) any, (GeneralDate) any,
						(GeneralDate) any);
				times = 2;
			}
		};
	}

	/**
	 * [1]変更登録する
	 * 
	 * 期間.開始日＜＝年月日#今日() = false
	 */
	@Test
	public void testRegisterFutureBaseDate() {

		// given
		List<ApprovalSettingParam> params = Arrays.asList(DomainServiceTestHelper.mockSettingParam());
		DatePeriod period = new DatePeriod(GeneralDate.today().addDays(1), GeneralDate.today().addDays(1));
		new Expectations() {
			{
				require.getPersonApprovalRoots(DomainServiceTestHelper.CID, DomainServiceTestHelper.SID, period);
				result = Arrays.asList(DomainServiceTestHelper.mockPersonApprovalRoot(OperationMode.PERSON_IN_CHARGE));
			};
		};

		// when
		List<AtomTask> atomTasks = UpdateSelfApprovalRootDomainService.register(require, DomainServiceTestHelper.CID,
				DomainServiceTestHelper.SID, params, period);

		// then
		assertThat(atomTasks.size()).isEqualTo(2);
		NtsAssert.atomTask(() -> atomTasks.get(0), any -> require.deletePersonApprovalRoots(any.get()));
		NtsAssert.atomTask(() -> atomTasks.get(1), any -> require.insertAll(any.get(), any.get()));
		new Verifications() {
			{
				require.createDailyApprover(DomainServiceTestHelper.SID, (RecordRootType) any, (GeneralDate) any,
						(GeneralDate) any);
				times = 0;
			}
		};
	}
}
