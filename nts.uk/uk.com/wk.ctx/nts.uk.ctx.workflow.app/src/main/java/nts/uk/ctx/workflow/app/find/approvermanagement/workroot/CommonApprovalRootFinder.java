package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalBranch;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.Approver;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkAppApprovalRootRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class CommonApprovalRootFinder {
	@Inject
	private WorkAppApprovalRootRepository repo;
	//user contexts
	String companyId = AppContexts.user().companyId();
	
	public List<CommonApprovalRootDto> getAllCommonApprovalRoot(){
		List<CommonApprovalRootDto> lstAppRoot = new ArrayList<>();
		List<CompanyApprovalRoot> lstCom = this.repo.getAllComApprovalRoot(companyId);
		for (CompanyApprovalRoot companyApprovalRoot : lstCom) {
			List<ApprovalPhaseDto> lstApprovalPhase = new ArrayList<>();
			Optional<ApprovalBranch> branch = this.repo.getApprovalBranch(companyId, companyApprovalRoot.getBranchId(), 1);
			if(branch.isPresent()){
				List<Approver> lstApprover = new ArrayList<>();
				List<ApprovalPhase> lstAppPhase = this.repo.getAllApprovalPhasebyCode(companyId, companyApprovalRoot.getBranchId());
				for (ApprovalPhase approvalPhase : lstAppPhase) {
					lstApprover = this.repo.getAllApproverByCode(companyId, approvalPhase.getApprovalPhaseId());
					lstApprovalPhase.add(new ApprovalPhaseDto(lstApprover, approvalPhase.getBranchId(),approvalPhase.getApprovalPhaseId(),
							approvalPhase.getApprovalForm().value, approvalPhase.getBrowsingPhase(), approvalPhase.getOrderNumber()));
				}
			}
			lstAppRoot.add(new CommonApprovalRootDto(companyApprovalRoot,lstApprovalPhase));			
		}
		
		return lstAppRoot;
	}
//	public List<ApprovalPeriod> grouping(List<ApprovalPeriod> lstApprovalPeriod){
//		for (ApprovalPeriod approvalPeriod : lstApprovalPeriod) {
//			approvalPeriod.getStartDate()
//		}
//		return null;
//		
//	}
}
