package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApproverRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class PrivateApprovalRootFinder {
	@Inject
	private PersonApprovalRootRepository repo;
	@Inject
	private ApprovalPhaseRepository repoAppPhase;
	@Inject
	private ApproverRepository repoApprover;
	
	public List<PrivateApprovalRootDto> getAllPrivateApprovalRoot(String employeeId){
		//user contexts
		String companyId = AppContexts.user().companyId();
		List<PrivateApprovalRootDto> lstAppRoot = new ArrayList<>();
		//get data person by employee id
		List<PersonApprovalRoot> lstPri = this.repo.getAllPsApprovalRoot(companyId, employeeId);
		for (PersonApprovalRoot personalApprovalRoot : lstPri) {
			List<ApprovalPhaseDto> lstApprovalPhase = new ArrayList<>();
//			Optional<ApprovalBranch> branch = this.repo.getApprovalBranch(companyId, personalApprovalRoot.getBranchId(), 1);
//			if(branch.isPresent()){
				List<ApproverDto> lstApprover = new ArrayList<>();
				//get data approval phase by branch id
				List<ApprovalPhase> lstAppPhase = this.repoAppPhase.getAllApprovalPhasebyCode(companyId, personalApprovalRoot.getBranchId());
				for (ApprovalPhase approvalPhase : lstAppPhase) {
					//get data approver by approval phase id
					lstApprover = this.repoApprover.getAllApproverByCode(companyId, approvalPhase.getApprovalPhaseId())
							.stream()
							.map(c->ApproverDto.fromDomain(c))
							.collect(Collectors.toList());
					lstApprovalPhase.add(new ApprovalPhaseDto(lstApprover, approvalPhase.getBranchId(),approvalPhase.getApprovalPhaseId(),
							approvalPhase.getApprovalForm().value, approvalPhase.getBrowsingPhase(), approvalPhase.getOrderNumber()));
				}
//			}
			lstAppRoot.add(new PrivateApprovalRootDto(personalApprovalRoot,lstApprovalPhase));			
		}
		
		return lstAppRoot;
	}
}
