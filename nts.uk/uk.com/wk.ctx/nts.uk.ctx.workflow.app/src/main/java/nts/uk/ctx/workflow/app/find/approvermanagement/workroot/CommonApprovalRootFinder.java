package nts.uk.ctx.workflow.app.find.approvermanagement.workroot;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApproverRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRootRepository;
import nts.uk.shr.com.context.AppContexts;
/**
 * 
 * @author hoatt
 *
 */
@Stateless
public class CommonApprovalRootFinder {
	@Inject
	private PersonApprovalRootRepository repo;
	@Inject
	private CompanyApprovalRootRepository repoCom;
	@Inject
	private WorkplaceApprovalRootRepository repoWorkplace;
	@Inject
	private ApprovalPhaseRepository repoAppPhase;
	@Inject
	private ApproverRepository repoApprover;
	
	public CommonApprovalRootDto getAllCommonApprovalRoot(ParamDto param){
		//user contexts
		String companyId = AppContexts.user().companyId();
		//TH: company - domain 会社別就業承認ルート
		if(param.getRootType() == 0){
			List<CompanyAppRootDto> lstComRoot = new ArrayList<>();
			//get all data from ComApprovalRoot (会社別就業承認ルート)
			List<ComApprovalRootDto> lstCom = this.repoCom.getAllComApprovalRoot(companyId)
								.stream()
								.map(c->ComApprovalRootDto.fromDomain(c))
								.collect(Collectors.toList());
			for (ComApprovalRootDto companyApprovalRoot : lstCom) {
				List<ApprovalPhaseDto> lstApprovalPhase = new ArrayList<>();
				List<ApproverDto> lstApprover = new ArrayList<ApproverDto>();
				//get All Approval Phase by BranchId
				List<ApprovalPhase> lstAppPhase = this.repoAppPhase.getAllApprovalPhasebyCode(companyId, companyApprovalRoot.getBranchId());
				for (ApprovalPhase approvalPhase : lstAppPhase) {
					//get All Approver By ApprovalPhaseId
					lstApprover = this.repoApprover.getAllApproverByCode(companyId, approvalPhase.getApprovalPhaseId())
								.stream()
								.map(c->ApproverDto.fromDomain(c))
								.collect(Collectors.toList());
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
		if(param.getRootType() == 1){
			List<WorkPlaceAppRootDto> lstWpRoot = new ArrayList<>();
//			String workplaceId = "";
			//get all data from WorkplaceApprovalRoot (職場別就業承認ルート)
			List<WpApprovalRootDto> lstWp = this.repoWorkplace.getAllWpApprovalRoot(companyId, param.getWorkplaceId())
					.stream()
					.map(c->WpApprovalRootDto.fromDomain(c))
					.collect(Collectors.toList());
			for (WpApprovalRootDto workplaceApprovalRoot : lstWp) {
				List<ApprovalPhaseDto> lstApprovalPhase = new ArrayList<>();
				List<ApproverDto> lstApprover = new ArrayList<>();
				//get All Approval Phase by BranchId
				List<ApprovalPhase> lstAppPhase = this.repoAppPhase.getAllApprovalPhasebyCode(companyId, workplaceApprovalRoot.getBranchId());
				for (ApprovalPhase approvalPhase : lstAppPhase) {
					//get All Approver By ApprovalPhaseId
					lstApprover = this.repoApprover.getAllApproverByCode(companyId, approvalPhase.getApprovalPhaseId())
							.stream()
							.map(c->ApproverDto.fromDomain(c))
							.collect(Collectors.toList());
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
			List<PsApprovalRootDto> lstPs = this.repo.getAllPsApprovalRoot(companyId, param.getEmployeeId())
					.stream()
					.map(c->PsApprovalRootDto.fromDomain(c))
					.collect(Collectors.toList());
			for (PsApprovalRootDto personApprovalRoot : lstPs) {
				List<ApprovalPhaseDto> lstApprovalPhase = new ArrayList<>();
				List<ApproverDto> lstApprover = new ArrayList<>();
				//get All Approval Phase by BranchId
				List<ApprovalPhase> lstAppPhase = this.repoAppPhase.getAllApprovalPhasebyCode(companyId, personApprovalRoot.getBranchId());
				for (ApprovalPhase approvalPhase : lstAppPhase) {
					//get All Approver By ApprovalPhaseId
					lstApprover = this.repoApprover.getAllApproverByCode(companyId, approvalPhase.getApprovalPhaseId())
							.stream()
							.map(c->ApproverDto.fromDomain(c))
							.collect(Collectors.toList());
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
