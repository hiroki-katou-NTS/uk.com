package nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.ChangePersonalApprovalRootDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.ChangePersonalApprovalRootDomainService.Require;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.param.ApprovalSettingParam;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.OperationMode;

@RunWith(JMockit.class)
public class ChangePersonalApprovalRootDomainServiceTest {

	@Injectable
	private Require require;

	/**
	 * [1]変更する
	 */
	@Test
	public void testCreateFull() {

		// given
		List<ApprovalSettingParam> params = Arrays.asList(DomainServiceTestHelper.mockSettingParam());
		new Expectations() {
			{
				require.getPersonApprovalRoots(DomainServiceTestHelper.CID, DomainServiceTestHelper.SID,
						DomainServiceTestHelper.PERIOD);
				result = Arrays.asList(DomainServiceTestHelper.mockPersonApprovalRoot(OperationMode.PERSON_IN_CHARGE));
			}
		};

		// when
		List<AtomTask> atomTasks = ChangePersonalApprovalRootDomainService.create(require, DomainServiceTestHelper.CID,
				DomainServiceTestHelper.SID, DomainServiceTestHelper.PERIOD, params);

		// then
		assertThat(atomTasks.size()).isEqualTo(2);
		NtsAssert.atomTask(() -> atomTasks.get(0), any -> require.deletePersonApprovalRoots(any.get()));
		NtsAssert.atomTask(() -> atomTasks.get(1), any -> require.insertAll(any.get(), any.get()));
	}

	/**
	 * [1]変更する
	 * 
	 * $承認IDListDel = empty
	 */
	@Test
	public void testCreateNoDelete() {

		// given
		List<ApprovalSettingParam> params = Arrays.asList(DomainServiceTestHelper.mockSettingParam());

		// when
		List<AtomTask> atomTasks = ChangePersonalApprovalRootDomainService.create(require, DomainServiceTestHelper.CID,
				DomainServiceTestHelper.SID, DomainServiceTestHelper.PERIOD, params);

		// then
		assertThat(atomTasks.size()).isEqualTo(1);
		NtsAssert.atomTask(() -> atomTasks.get(0), any -> require.insertAll(any.get(), any.get()));
	}

	/**
	 * [1]変更する
	 * 
	 * 承認者設定パラメータList = empty
	 */
	@Test
	public void testCreateNoInsert() {

		// given
		List<ApprovalSettingParam> params = Collections.emptyList();
		new Expectations() {
			{
				require.getPersonApprovalRoots(DomainServiceTestHelper.CID, DomainServiceTestHelper.SID,
						DomainServiceTestHelper.PERIOD);
				result = Arrays.asList(DomainServiceTestHelper.mockPersonApprovalRoot(OperationMode.PERSON_IN_CHARGE));
			}
		};

		// when
		List<AtomTask> atomTasks = ChangePersonalApprovalRootDomainService.create(require, DomainServiceTestHelper.CID,
				DomainServiceTestHelper.SID, DomainServiceTestHelper.PERIOD, params);

		// then
		assertThat(atomTasks.size()).isEqualTo(1);
		NtsAssert.atomTask(() -> atomTasks.get(0), any -> require.deletePersonApprovalRoots(any.get()));
	}
}
