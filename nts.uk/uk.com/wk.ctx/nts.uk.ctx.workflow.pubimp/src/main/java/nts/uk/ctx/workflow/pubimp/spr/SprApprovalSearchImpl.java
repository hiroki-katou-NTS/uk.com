package nts.uk.ctx.workflow.pubimp.spr;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootStateRepository;
import nts.uk.ctx.workflow.dom.service.JudgmentApprovalStatusService;
import nts.uk.pub.spr.approvalroot.SprApprovalSearch;
import nts.uk.pub.spr.approvalroot.output.ApprovalComSpr;
import nts.uk.pub.spr.approvalroot.output.ApprovalPersonSpr;
import nts.uk.pub.spr.approvalroot.output.ApprovalPhaseSpr;
import nts.uk.pub.spr.approvalroot.output.ApprovalRootStateSpr;
import nts.uk.pub.spr.approvalroot.output.ApprovalWorkplaceSpr;
import nts.uk.pub.spr.approvalroot.output.ApproverSpr;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Stateless
public class SprApprovalSearchImpl implements SprApprovalSearch {
	
	@Inject
	private CompanyApprovalRootRepository companyApprovalRootRepository;
	
	@Inject
	private WorkplaceApprovalRootRepository workplaceApprovalRootRepository; 
	
	@Inject
	private PersonApprovalRootRepository personApprovalRootRepository; 
	
	@Inject
	private ApprovalPhaseRepository approvalPhaseRepository;
	
	@Inject
	private ApprovalRootStateRepository approvalRootStateRepository;
	
	@Inject
	private JudgmentApprovalStatusService judgmentApprovalStatusService;

	@Override
	public List<ApprovalComSpr> getApprovalRootCom(String companyID, GeneralDate date, Integer employmentRootAtr,
			Integer confirmRootAtr) {
		return companyApprovalRootRepository.getComAppRoot(companyID, date, employmentRootAtr, confirmRootAtr)
				.stream().map(x -> new ApprovalComSpr(
						companyID, 
						x.getApprovalId(), 
						x.getApplicationType().value, 
						x.getBranchId(), 
						x.getAnyItemApplicationId(), 
						x.getConfirmationRootType().value, 
						employmentRootAtr))
				.collect(Collectors.toList());
	}

	@Override
	public List<ApprovalPhaseSpr> getAllIncludeApprovers(String companyId, String branchId) {
		return approvalPhaseRepository.getAllIncludeApprovers(companyId, branchId)
			.stream().map(x -> ApprovalPhaseSpr.createFromJavaType(
					x.getCompanyId(), 
					x.getBranchId(), 
					x.getApprovalPhaseId(), 
					x.getApprovalForm().value, 
					x.getBrowsingPhase(), 
					x.getOrderNumber(), 
					x.getApprovers().stream().map(y -> ApproverSpr.createFromJavaType(
							y.getCompanyId(), 
							y.getBranchId(), 
							y.getApprovalPhaseId(), 
							y.getApproverId(), 
							y.getJobTitleId(), 
							y.getEmployeeId(), 
							y.getOrderNumber(), 
							y.getApprovalAtr().value, 
							y.getConfirmPerson().value)).collect(Collectors.toList())))
			.collect(Collectors.toList());
	}

	@Override
	public List<ApprovalWorkplaceSpr> getApprovalRootWp(String companyID, GeneralDate date, Integer employmentRootAtr,
			Integer confirmRootAtr) {
		return workplaceApprovalRootRepository.getWpAppRoot(companyID, date, employmentRootAtr, confirmRootAtr)
				.stream().map(x -> new ApprovalWorkplaceSpr(
						x.getCompanyId(), 
						x.getApprovalId(), 
						x.getWorkplaceId(), 
						x.getBranchId(), 
						x.getAnyItemApplicationId(), 
						x.getConfirmationRootType().value, 
						employmentRootAtr, 
						x.getApplicationType().value))
				.collect(Collectors.toList());
	}

	@Override
	public List<ApprovalPersonSpr> getApprovalRootPs(String companyID, GeneralDate date, Integer employmentRootAtr,
			Integer confirmRootAtr) {
		return personApprovalRootRepository.getPsAppRoot(companyID, date, employmentRootAtr, confirmRootAtr)
				.stream().map(x -> new ApprovalPersonSpr(
						x.getCompanyId(), 
						x.getApprovalId(), 
						x.getEmployeeId(), 
						x.getApplicationType().value, 
						x.getBranchId(), 
						x.getAnyItemApplicationId(), 
						x.getConfirmationRootType().value, 
						employmentRootAtr))
				.collect(Collectors.toList());
	}
	
	@Override
	public List<ApprovalRootStateSpr> getRootStateByDateAndType(GeneralDate date, Integer rootType) {
		return approvalRootStateRepository.getRootStateByDateAndType(date, rootType)
			.stream().map(x -> new ApprovalRootStateSpr(
					x.getRootStateID(), 
					x.getRootType().value, 
					x.getHistoryID(), 
					x.getApprovalRecordDate(), 
					x.getEmployeeID()))
			.collect(Collectors.toList());
	}

	@Override
	public boolean judgmentTargetPersonCanApprove(String companyID, String rootStateID, String employeeID) {
		return judgmentApprovalStatusService.judgmentTargetPersonCanApprove(companyID, rootStateID, employeeID).getAuthorFlag();
	}
	
}
