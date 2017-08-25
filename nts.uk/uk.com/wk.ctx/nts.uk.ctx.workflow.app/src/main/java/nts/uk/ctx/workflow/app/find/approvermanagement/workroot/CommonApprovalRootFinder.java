package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.Approver;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRoot;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkAppApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRoot;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author hoatt
 *
 */
@Stateless
public class CommonApprovalRootFinder {
	@Inject
	private WorkAppApprovalRootRepository repo;
	
	public CommonApprovalRootDto getAllCommonApprovalRoot(int rootType, String employeeId){
		//user contexts
		String companyId = AppContexts.user().companyId();
		//TH: company - domain 会社別就業承認ルート
		if(rootType == 0){
			List<CompanyAppRootDto> lstComRoot = new ArrayList<>();
			//get all data from ComApprovalRoot (会社別就業承認ルート)
			List<CompanyApprovalRoot> lstCom = this.repo.getAllComApprovalRoot(companyId);
			for (CompanyApprovalRoot companyApprovalRoot : lstCom) {
				List<ApprovalPhaseDto> lstApprovalPhase = new ArrayList<>();
				List<Approver> lstApprover = new ArrayList<>();
				//get All Approval Phase by BranchId
				List<ApprovalPhase> lstAppPhase = this.repo.getAllApprovalPhasebyCode(companyId, companyApprovalRoot.getBranchId());
				for (ApprovalPhase approvalPhase : lstAppPhase) {
					//get All Approver By ApprovalPhaseId
					lstApprover = this.repo.getAllApproverByCode(companyId, approvalPhase.getApprovalPhaseId());
					//lst (ApprovalPhase + lst Approver)
					lstApprovalPhase.add(new ApprovalPhaseDto(lstApprover, approvalPhase.getBranchId(),approvalPhase.getApprovalPhaseId(),
							approvalPhase.getApprovalForm().value, approvalPhase.getBrowsingPhase(), approvalPhase.getOrderNumber()));
				}
				//add in lstAppRoot
				lstComRoot.add(new CompanyAppRootDto(companyApprovalRoot,lstApprovalPhase));
			}
			return new CommonApprovalRootDto(lstComRoot, null, null);
		}
		//TH: workplace - domain 職場別就業承認ルート
		if(rootType == 1){
			List<WorkPlaceAppRootDto> lstWpRoot = new ArrayList<>();
			String workplaceId = "";
			//get all data from WorkplaceApprovalRoot (職場別就業承認ルート)
			List<WorkplaceApprovalRoot> lstWp = this.repo.getAllWpApprovalRoot(companyId, workplaceId);
			for (WorkplaceApprovalRoot workplaceApprovalRoot : lstWp) {
				List<ApprovalPhaseDto> lstApprovalPhase = new ArrayList<>();
				List<Approver> lstApprover = new ArrayList<>();
				//get All Approval Phase by BranchId
				List<ApprovalPhase> lstAppPhase = this.repo.getAllApprovalPhasebyCode(companyId, workplaceApprovalRoot.getBranchId());
				for (ApprovalPhase approvalPhase : lstAppPhase) {
					//get All Approver By ApprovalPhaseId
					lstApprover = this.repo.getAllApproverByCode(companyId, approvalPhase.getApprovalPhaseId());
					//lst (ApprovalPhase + lst Approver)
					lstApprovalPhase.add(new ApprovalPhaseDto(lstApprover, approvalPhase.getBranchId(),approvalPhase.getApprovalPhaseId(),
							approvalPhase.getApprovalForm().value, approvalPhase.getBrowsingPhase(), approvalPhase.getOrderNumber()));
				}
				//add in lstAppRoot
				lstWpRoot.add(new WorkPlaceAppRootDto(workplaceApprovalRoot,lstApprovalPhase));
			}
			return new CommonApprovalRootDto(null, lstWpRoot, null);
		}
		//TH: person - domain 個人別就業承認ルート
		else{
			List<PersonAppRootDto> lstPsRoot = new ArrayList<>();
			//get all data from PersonApprovalRoot (個人別就業承認ルート)
			List<PersonApprovalRoot> lstPs = this.repo.getAllPsApprovalRoot(companyId, employeeId);
			for (PersonApprovalRoot personApprovalRoot : lstPs) {
				List<ApprovalPhaseDto> lstApprovalPhase = new ArrayList<>();
				List<Approver> lstApprover = new ArrayList<>();
				//get All Approval Phase by BranchId
				List<ApprovalPhase> lstAppPhase = this.repo.getAllApprovalPhasebyCode(companyId, personApprovalRoot.getBranchId());
				for (ApprovalPhase approvalPhase : lstAppPhase) {
					//get All Approver By ApprovalPhaseId
					lstApprover = this.repo.getAllApproverByCode(companyId, approvalPhase.getApprovalPhaseId());
					//lst (ApprovalPhase + lst Approver)
					lstApprovalPhase.add(new ApprovalPhaseDto(lstApprover, approvalPhase.getBranchId(),approvalPhase.getApprovalPhaseId(),
							approvalPhase.getApprovalForm().value, approvalPhase.getBrowsingPhase(), approvalPhase.getOrderNumber()));
				}
				//add in lstAppRoot
				lstPsRoot.add(new PersonAppRootDto(personApprovalRoot,lstApprovalPhase));
			}
			return new CommonApprovalRootDto(null, null, lstPsRoot);
		}
	}
	
	
//	public List<ApprovalPeriod> grouping(List<ApprovalPeriod> lstApprovalPeriod){
//		for (ApprovalPeriod approvalPeriod : lstApprovalPeriod) {
//			approvalPeriod.getStartDate()
//		}
//		return null;
//		
//	}
}
