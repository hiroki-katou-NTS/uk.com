package nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSetting;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApproverRegisterSet;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.UseClassification;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalSettingInformation;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmationRootType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.EmploymentRootAtr;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.param.ApprovalRootInformation;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.param.ApprovalSettingParam;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.param.ApproverInformation;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApproverItemName;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApproverOperationSettings;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ItemNameInformation;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.OperationMode;

public class DomainServiceTestHelper {

	public static final String CID = "cid";
	public static final String SID = "sid";
	public static final DatePeriod PERIOD = new DatePeriod(GeneralDate.today(), GeneralDate.today());

	public static ApprovalSettingParam mockSettingParam() {
		List<ApproverInformation> approvalPhases = Arrays.asList(new ApproverInformation(0, "dummy1"),
				new ApproverInformation(1, "dummy2"));
		ApprovalRootInformation approvalRootInfo = new ApprovalRootInformation(EmploymentRootAtr.COMMON, PERIOD,
				Optional.empty(), Optional.empty());
		return new ApprovalSettingParam(approvalPhases, approvalRootInfo);
	}

	public static ApprovalSettingInformation mockSettingInfo() {
		List<ApprovalPhase> approvalPhases = Arrays.asList(mockApprovalPhase());
		PersonApprovalRoot personApprovalRoot = mockPersonApprovalRoot(OperationMode.SUPERIORS_EMPLOYEE);
		personApprovalRoot.getApprRoot().getHistoryItems().forEach(data -> data.setApprovalId("dummy"));
		return new ApprovalSettingInformation(approvalPhases, personApprovalRoot);
	}

	public static ApprovalPhase mockApprovalPhase() {
		return ApprovalPhase.createSimpleFromJavaType("dummy", 1, "dummy");
	}

	public static PersonApprovalRoot mockPersonApprovalRoot(OperationMode operationMode) {
		PersonApprovalRoot domain = new PersonApprovalRoot(CID, CID, PERIOD, EmploymentRootAtr.COMMON,
				ApplicationType.COMPLEMENT_LEAVE_APPLICATION, ConfirmationRootType.DAILY_CONFIRMATION);
		domain.setOperationMode(operationMode);
		return domain;
	}

	public static ApproverOperationSettings mockApproverOperationSettings() {
		ItemNameInformation itemNameInformation = mockItemNameInformation();
		return new ApproverOperationSettings(OperationMode.PERSON_IN_CHARGE, itemNameInformation);
	}

	public static ItemNameInformation mockItemNameInformation() {
		return new ItemNameInformation(new ApproverItemName("dummy"), new ApproverItemName("dummy"),
				new ApproverItemName("dummy"), new ApproverItemName("dummy"), new ApproverItemName("dummy"));
	}

	public static ApprovalSetting mockApprovalSetting() {
		ApproverRegisterSet approverRegisterSet = new ApproverRegisterSet(UseClassification.DO_USE,
				UseClassification.DO_USE, UseClassification.DO_USE);
		return new ApprovalSetting(CID, approverRegisterSet, true);
	}
}
