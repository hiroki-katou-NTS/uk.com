package nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.require;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSettingRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.ChangePersonalApprovalRootDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.CopyPersonalApprovalRootDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.CreateEmployeeInterimDataDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.CreatePersonalApprovalRootDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.GetSelfApprovalSettingsDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.SetOperationModeDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.UpdateApprovalRootHistoryDomainService;
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

	public Require createRequire() {
		return new RequireImpl(personApprovalRootRepository, approvalPhaseRepository,
				approverOperationSettingsRepository, approvalSettingRepository, createDailyApprover);
	}

	public interface Require extends ChangePersonalApprovalRootDomainService.Require,
			CopyPersonalApprovalRootDomainService.Require, CreateEmployeeInterimDataDomainService.Require,
			CreatePersonalApprovalRootDomainService.Require, UpdateApprovalRootHistoryDomainService.Require,
			SetOperationModeDomainService.Require, GetSelfApprovalSettingsDomainService.Require {

	}
}
