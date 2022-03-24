package nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.CopyPersonalApprovalRootDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.CopyPersonalApprovalRootDomainService.Require;

@RunWith(JMockit.class)
public class CopyPersonalApprovalRootDomainServiceTest {

	@Injectable
	private Require require;

	/**
	 * [1]複写する
	 */
	@Test
	public void testCopy() {

		// given
		String targetSid = "targetSid";
		GeneralDate baseDate = GeneralDate.today();
		new Expectations() {
			{
				require.getApprovalInfos(DomainServiceTestHelper.CID, DomainServiceTestHelper.SID, baseDate);
				result = DomainServiceTestHelper.mockSettingInfo();
			};
		};

		// when
		List<AtomTask> atomTasks = CopyPersonalApprovalRootDomainService.copy(require, DomainServiceTestHelper.CID,
				DomainServiceTestHelper.SID, targetSid, baseDate);

		// then
		assertThat(atomTasks.size()).isEqualTo(1);
		NtsAssert.atomTask(() -> atomTasks.get(0), any -> require.insertAll(any.get(), any.get()));
	}

	/**
	 * [1]複写する
	 * 
	 * 複写元さんの履歴List = empty
	 */
	@Test
	public void testCopyParamsEmpty() {

		// given
		String targetSid = "targetSid";
		GeneralDate baseDate = GeneralDate.today();

		// when
		List<AtomTask> atomTasks = CopyPersonalApprovalRootDomainService.copy(require, DomainServiceTestHelper.CID,
				DomainServiceTestHelper.SID, targetSid, baseDate);

		// then
		assertThat(atomTasks).isEmpty();
	}
}
