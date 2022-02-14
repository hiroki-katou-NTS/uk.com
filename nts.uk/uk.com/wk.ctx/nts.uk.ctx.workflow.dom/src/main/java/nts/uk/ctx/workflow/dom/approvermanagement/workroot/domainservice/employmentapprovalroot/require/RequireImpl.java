package nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.require;

import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSetting;
import nts.uk.ctx.workflow.dom.approvermanagement.setting.ApprovalSettingRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApplicationType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalSettingInformation;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmationRootType;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.require.RequireService.Require;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApproverOperationSettings;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.operationsettings.ApproverOperationSettingsRepository;
import nts.uk.ctx.workflow.dom.resultrecord.RecordRootType;
import nts.uk.ctx.workflow.dom.resultrecord.service.CreateDailyApprover;
import nts.uk.shr.com.context.AppContexts;

@AllArgsConstructor
public class RequireImpl implements Require {

	private PersonApprovalRootRepository personApprovalRootRepository;
	private ApprovalPhaseRepository approvalPhaseRepository;
	private ApproverOperationSettingsRepository approverOperationSettingsRepository;
	private ApprovalSettingRepository approvalSettingRepository;
	private CreateDailyApprover createDailyApprover;

	@Override
	public List<PersonApprovalRoot> getHistoryWithStartDate(String sid, GeneralDate baseDate) {
		String cid = AppContexts.user().companyId();
		return this.personApprovalRootRepository.getHistWithStartAfterBaseDate(cid, sid, baseDate);
	}

	@Override
	public List<PersonApprovalRoot> getHistoryWithEndDate(String sid, GeneralDate baseDate) {
		String cid = AppContexts.user().companyId();
		return this.personApprovalRootRepository.getHistWithEndAfterBaseDate(cid, sid, baseDate);
	}

	@Override
	public void updatePersonApprovalRoot(PersonApprovalRoot personApprovalRoot) {
		this.personApprovalRootRepository.updatePsApprovalRoot(personApprovalRoot);
	}

	@Override
	public void createDailyApprover(String sid, RecordRootType recordRootType, GeneralDate recordDate,
			GeneralDate closureStartDate) {
		this.createDailyApprover.createDailyApprover(sid, recordRootType, recordDate, closureStartDate);
	}

	@Override
	public List<ApprovalSettingInformation> getApprovalInfos(String cid, String sid, GeneralDate baseDate) {
		return this.personApprovalRootRepository.getHistIncludeBaseDate(cid, sid, baseDate);
	}

	@Override
	public List<ApprovalPhase> getApprovalPhases(String cid, List<String> approvalIds) {
		return this.approvalPhaseRepository.getFromApprovalIds(cid, approvalIds);
	}

	@Override
	public List<PersonApprovalRoot> getPersonApprovalRoots(String cid, String sid, DatePeriod period) {
		return this.personApprovalRootRepository.getHistFromBaseDate(cid, sid, period);
	}

	@Override
	public void deletePersonApprovalRoots(List<String> approvalIds) {
		this.personApprovalRootRepository.deleteHistFromApprovals(approvalIds);
	}

	@Override
	public void insertAll(PersonApprovalRoot personApprovalRoot, List<ApprovalPhase> approvalPhases) {
		this.personApprovalRootRepository.insertPersonApprovalRootAndPhases(personApprovalRoot, approvalPhases);
	}

	@Override
	public List<PersonApprovalRoot> getPersonApprovalRoots(String sid, GeneralDate baseDate,
			List<ApplicationType> appTypes, List<ConfirmationRootType> confirmationRootTypes) {
		String cid = AppContexts.user().companyId();
		return this.personApprovalRootRepository.getPersonApprovalRoots(cid, sid, baseDate, appTypes, confirmationRootTypes);
	}

	@Override
	public List<ApprovalPhase> getApprovalPhases(List<String> approvalIds) {
		String cid = AppContexts.user().companyId();
		return this.approvalPhaseRepository.getFromApprovalIds(cid, approvalIds);
	}

	@Override
	public Optional<ApproverOperationSettings> getOperationSetting() {
		String cid = AppContexts.user().companyId();
		return this.approverOperationSettingsRepository.get(cid);
	}

	@Override
	public Optional<PersonApprovalRoot> getSmallestHistFromBaseDate(String sid, GeneralDate baseDate,
			List<ApplicationType> appTypes, List<ConfirmationRootType> confirmTypes) {
		String cid = AppContexts.user().companyId();
		return this.personApprovalRootRepository.GetSmallestHistFromBaseDate(cid, sid, baseDate, appTypes, confirmTypes);
	}

	@Override
	public Optional<ApprovalSetting> getApprovalSetting(String cid) {
		return this.approvalSettingRepository.getApprovalByComId(cid);
	}

	@Override
	public void insertApprovalSetting(ApprovalSetting domain) {
		this.approvalSettingRepository.insert(domain);
	}

	@Override
	public void updateApprovalSetting(ApprovalSetting domain) {
		this.approvalSettingRepository.update(domain);
	}

	@Override
	public Optional<ApproverOperationSettings> getOperationSetting(String cid) {
		return this.approverOperationSettingsRepository.get(cid);
	}

	@Override
	public void insertOperationSetting(ApproverOperationSettings domain) {
		this.approverOperationSettingsRepository.insert(domain);
	}

	@Override
	public void updateOperationSetting(ApproverOperationSettings domain) {
		this.approverOperationSettingsRepository.update(domain);
	}
}
