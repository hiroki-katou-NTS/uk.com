package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalBranch;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.Approver;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkAppApprovalRootRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class PrivateApprovalRootFinder {
	@Inject
	private WorkAppApprovalRootRepository repo;
	//user contexts
	String companyId = AppContexts.user().companyId();
	
	public List<PrivateApprovalRootDto> getAllPrivateApprovalRoot(String employeeId){
		List<PrivateApprovalRootDto> lstAppRoot = new ArrayList<>();
		//get data person by employee id
		List<PersonApprovalRoot> lstPri = this.repo.getAllPsApprovalRoot(companyId, employeeId);
		for (PersonApprovalRoot personalApprovalRoot : lstPri) {
			List<ApprovalPhaseDto> lstApprovalPhase = new ArrayList<>();
//			Optional<ApprovalBranch> branch = this.repo.getApprovalBranch(companyId, personalApprovalRoot.getBranchId(), 1);
//			if(branch.isPresent()){
				List<Approver> lstApprover = new ArrayList<>();
				//get data approval phase by branch id
				List<ApprovalPhase> lstAppPhase = this.repo.getAllApprovalPhasebyCode(companyId, personalApprovalRoot.getBranchId());
				for (ApprovalPhase approvalPhase : lstAppPhase) {
					//get data approver by approval phase id
					lstApprover = this.repo.getAllApproverByCode(companyId, approvalPhase.getApprovalPhaseId());
					lstApprovalPhase.add(new ApprovalPhaseDto(lstApprover, approvalPhase.getBranchId(),approvalPhase.getApprovalPhaseId(),
							approvalPhase.getApprovalForm().value, approvalPhase.getBrowsingPhase(), approvalPhase.getOrderNumber()));
				}
//			}
			lstAppRoot.add(new PrivateApprovalRootDto(personalApprovalRoot,lstApprovalPhase));			
		}
		
		return lstAppRoot;
	}
}
