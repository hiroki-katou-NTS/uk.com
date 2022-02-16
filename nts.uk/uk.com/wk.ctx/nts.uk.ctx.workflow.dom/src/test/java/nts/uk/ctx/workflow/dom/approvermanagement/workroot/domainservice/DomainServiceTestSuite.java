package nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
	ChangePersonalApprovalRootDomainServiceTest.class,
	CopyPersonalApprovalRootDomainServiceTest.class,
	CreateEmployeeInterimDataDomainServiceTest.class,
	CreateSelfApprovalRootDomainServiceTest.class,
	GetSelfApprovalSettingsDomainServiceTest.class,
	SetOperationModeDomainServiceTest.class,
	UpdateApprovalRootHistoryDomainServiceTest.class,
	UpdateSelfApprovalRootDomainServiceTest.class
})
public class DomainServiceTestSuite {

}
