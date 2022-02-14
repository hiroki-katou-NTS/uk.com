package nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.require;

import java.util.List;

import lombok.Builder;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalSettingInformation;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.ChangePersonalApprovalRootDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.CopyPersonalApprovalRootDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.CreateEmployeeInterimDataDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.CreatePersonalApprovalRootDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.UpdateApprovalRootHistoryDomainService;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.domainservice.employmentapprovalroot.CreatePersonalApprovalRootDomainService.Require;
import nts.uk.ctx.workflow.dom.resultrecord.RecordRootType;

@Builder
public class RequireImpl implements ChangePersonalApprovalRootDomainService.Require,
		CopyPersonalApprovalRootDomainService.Require,
		CreateEmployeeInterimDataDomainService.Require,
		CreatePersonalApprovalRootDomainService.Require,
		UpdateApprovalRootHistoryDomainService.Require {@Override
	public List<PersonApprovalRoot> getHistoryWithStartDate(String sid, GeneralDate baseDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PersonApprovalRoot> getHistoryWithEndDate(String sid, GeneralDate baseDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updatePersonApprovalRoot(PersonApprovalRoot personApprovalRoot) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void createDailyApprover(String sid, RecordRootType recordRootType, GeneralDate recordDate,
			GeneralDate closureStartDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ApprovalSettingInformation> getApprovalInfos(String cid, String sid, GeneralDate baseDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<ApprovalPhase> getApprovalPhases(String cid, List<String> approverIds) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PersonApprovalRoot> getPersonApprovalRoots(String cid, String sid, DatePeriod period) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletePersonApprovalRoots(List<String> approvalIds) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void insertAll(PersonApprovalRoot personApprovalRoot, List<ApprovalPhase> approvalPhases) {
		// TODO Auto-generated method stub
		
	}
}
