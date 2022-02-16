package nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice;

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
		AtomTask atomTask = CopyPersonalApprovalRootDomainService.copy(require, DomainServiceTestHelper.CID,
				DomainServiceTestHelper.SID, targetSid, baseDate);

		// then
		NtsAssert.atomTask(() -> atomTask);
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
		AtomTask atomTask = CopyPersonalApprovalRootDomainService.copy(require, DomainServiceTestHelper.CID,
				DomainServiceTestHelper.SID, targetSid, baseDate);

		// then
		NtsAssert.atomTask(() -> atomTask);
	}
}
