package nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalSettingInformation;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.CreatePersonalApprovalRootDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.CreatePersonalApprovalRootDomainService.Require;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.param.ApprovalSettingParam;

@RunWith(JMockit.class)
public class CreatePersonalApprovalRootDomainServiceTest {

	@Injectable
	private Require require;

	/**
	 * [1]作成して登録する
	 */
	@Test
	public void testCreateAndRegister() {

		// given
		ApprovalSettingParam param = DomainServiceTestHelper.mockSettingParam();

		// when
		AtomTask atomTask = CreatePersonalApprovalRootDomainService.createAndRegister(require,
				DomainServiceTestHelper.CID, DomainServiceTestHelper.SID, param);

		// then
		NtsAssert.atomTask(() -> atomTask);
	}

	/**
	 * [2]作成する
	 */
	@Test
	public void testCreate() {

		// given
		ApprovalSettingParam param = DomainServiceTestHelper.mockSettingParam();

		// when
		ApprovalSettingInformation data = CreatePersonalApprovalRootDomainService
				.createData(DomainServiceTestHelper.CID, DomainServiceTestHelper.SID, param);
		
		// then
		assertThat(data.getApprovalPhases()).isNotEmpty();
		assertThat(data.getPersonApprovalRoot()).isNotNull();
	}
}
