package nts.uk.ctx.workflow.dom.approvermanagement.workroot;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApprovalLevelNoTest;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApproverOperationSettingsTest;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApproverSettingScreenInforTest;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.SettingTypeUsedTest;

@RunWith(Suite.class)
@SuiteClasses({
	PersonApprovalRootTest.class,
	EmploymentAppHistoryItemTest.class,
	ApproverTest.class,
	ApprovalRootTest.class,
	ApprovalPhaseTest.class,
	ApproverOperationSettingsTest.class,
	ApproverSettingScreenInforTest.class,
	SettingTypeUsedTest.class,
	ApprovalSettingTest.class,
	ApproverRegisterSetTest.class,
	ApprovalLevelNoTest.class
})
public class PersonApprovalRootTestSuite {

}
