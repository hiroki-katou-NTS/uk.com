package nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.require;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSettingRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.adapter.GetEmployeeInDesignatedAdapter;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.adapter.GetReferenceWorkplaceListAdapter;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.ChangePersonalApprovalRootDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.CopyPersonalApprovalRootDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.CreateEmployeeInterimDataDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.CreatePersonalApprovalRootDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.CreateSelfApprovalRootDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.GetReferenceEmployeesDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.GetSelfApprovalSettingsDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.SetOperationModeDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.UpdateApprovalRootHistoryDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.UpdateSelfApprovalRootDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApproverOperationSettingsRepository;
import nts.uk.ctx.workflow.dom.resultrecord.service.CreateDailyApprover;

@Stateless
public class RequireService {

	@Inject
	private PersonApprovalRootRepository personApprovalRootRepository;

	@Inject
	private ApprovalPhaseRepository approvalPhaseRepository;

	@Inject
	private ApproverOperationSettingsRepository approverOperationSettingsRepository;

	@Inject
	private ApprovalSettingRepository approvalSettingRepository;

	@Inject
	private CreateDailyApprover createDailyApprover;

	@Inject
	private GetReferenceWorkplaceListAdapter getReferenceWorkplaceListAdapter;

	@Inject
	private GetEmployeeInDesignatedAdapter getEmployeeInDesignatedAdapter;

	public Require createRequire() {
		return new RequireImpl(personApprovalRootRepository, approvalPhaseRepository,
				approverOperationSettingsRepository, approvalSettingRepository, createDailyApprover,
				getReferenceWorkplaceListAdapter, getEmployeeInDesignatedAdapter);
	}

	public interface Require
			extends ChangePersonalApprovalRootDomainService.Require, CopyPersonalApprovalRootDomainService.Require,
			CreateEmployeeInterimDataDomainService.Require, CreatePersonalApprovalRootDomainService.Require,
			UpdateApprovalRootHistoryDomainService.Require, SetOperationModeDomainService.Require,
			GetSelfApprovalSettingsDomainService.Require, CreateSelfApprovalRootDomainService.Require,
			UpdateSelfApprovalRootDomainService.Require, GetReferenceEmployeesDomainService.Require {

	}
}
