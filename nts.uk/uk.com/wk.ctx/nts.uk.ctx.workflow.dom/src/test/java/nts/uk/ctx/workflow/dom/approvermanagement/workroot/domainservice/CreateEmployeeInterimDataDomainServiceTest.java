package nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Injectable;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.CreateEmployeeInterimDataDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.CreateEmployeeInterimDataDomainService.Require;
import nts.uk.ctx.workflow.dom.resultrecord.RecordRootType;

@RunWith(JMockit.class)
public class CreateEmployeeInterimDataDomainServiceTest {

	@Injectable
	private Require require;

	/**
	 * [1]作成する
	 */
	@Test
	public void testCreate() {

		// given
		GeneralDate startDate = GeneralDate.today();

		// when
		CreateEmployeeInterimDataDomainService.create(require, DomainServiceTestHelper.CID, DomainServiceTestHelper.SID,
				startDate);
		
		// then
		new Verifications() {
			{
				require.createDailyApprover(DomainServiceTestHelper.SID, (RecordRootType) any, startDate, startDate);
				times = 2;
			};
		};
	}
}
