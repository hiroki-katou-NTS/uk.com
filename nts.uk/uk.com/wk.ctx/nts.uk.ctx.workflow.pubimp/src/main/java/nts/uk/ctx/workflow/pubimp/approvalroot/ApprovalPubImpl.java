package nts.uk.ctx.workflow.pubimp.approvalroot;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhaseRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.CompanyApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.PersonApprovalRootRepository;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.WorkplaceApprovalRootRepository;
import nts.uk.ctx.workflow.pub.approvalroot.ApprovalRootPub;
import nts.uk.ctx.workflow.pub.approvalroot.export.ApprovalPhaseExport;
import nts.uk.ctx.workflow.pub.approvalroot.export.ApproverExport;
import nts.uk.ctx.workflow.pub.approvalroot.export.CompanyApprovalRootExport;
import nts.uk.ctx.workflow.pub.approvalroot.export.PersonApprovalRootExport;
import nts.uk.ctx.workflow.pub.approvalroot.export.WkpApprovalRootExport;

@Stateless
public class ApprovalPubImpl implements ApprovalRootPub{
	
	@Inject
	private PersonApprovalRootRepository personAppRootRepository;
	
	@Inject 
	private WorkplaceApprovalRootRepository wkpAppRootRepository;
	
	@Inject 
	private CompanyApprovalRootRepository companyAppRootRepository;
	
	@Inject
	private ApprovalPhaseRepository appPhaseRepository;
	
	@Override
	public List<PersonApprovalRootExport> findByBaseDate(String cid, String sid, Date standardDate, int appType) {
		return this.personAppRootRepository.findByBaseDate(cid, sid, standardDate, appType).stream()
				.map(x -> new PersonApprovalRootExport(
						x.getCompanyId(),
						x.getApprovalId(),
						x.getEmployeeId(),
						x.getHistoryId(),
						x.getApplicationType().value,
						x.getPeriod().getStartDate(),
						x.getPeriod().getEndDate(),
						x.getBranchId(),
						x.getAnyItemApplicationId(),
						x.getConfirmationRootType().value,
						x.getEmploymentRootAtr().value
			    )).collect(Collectors.toList());
	}

	@Override
	public List<PersonApprovalRootExport> findByBaseDateOfCommon(String cid, String sid, Date standardDate) {
		return this.personAppRootRepository.findByBaseDateOfCommon(cid, sid, standardDate).stream()
				.map(x -> new PersonApprovalRootExport(
						x.getCompanyId(),
						x.getApprovalId(),
						x.getEmployeeId(),
						x.getHistoryId(),
						x.getApplicationType().value,
						x.getPeriod().getStartDate(),
						x.getPeriod().getEndDate(),
						x.getBranchId(),
						x.getAnyItemApplicationId(),
						x.getConfirmationRootType().value,
						x.getEmploymentRootAtr().value
			    )).collect(Collectors.toList());
	}
	
	@Override
	public List<WkpApprovalRootExport> findWkpByBaseDate(String cid, String workplaceId, Date baseDate, int appType) {
		return this.wkpAppRootRepository.findByBaseDate(cid, workplaceId, baseDate, appType).stream()
				.map(x -> new WkpApprovalRootExport(
						x.getCompanyId(),
						x.getApprovalId(),
						x.getWorkplaceId(),
						x.getHistoryId(),
						x.getApplicationType().value,
						x.getPeriod().getStartDate(),
						x.getPeriod().getEndDate(),
						x.getBranchId(),
						x.getAnyItemApplicationId(),
						x.getConfirmationRootType().value,
						x.getEmploymentRootAtr().value
			    )).collect(Collectors.toList());
	}
	
	@Override
	public List<WkpApprovalRootExport> findWkpByBaseDateOfCommon(String cid, String workplaceId, Date baseDate) {
		return this.wkpAppRootRepository.findByBaseDateOfCommon(cid, workplaceId, baseDate).stream()
				.map(x -> new WkpApprovalRootExport(
						x.getCompanyId(),
						x.getApprovalId(),
						x.getWorkplaceId(),
						x.getHistoryId(),
						x.getApplicationType().value,
						x.getPeriod().getStartDate(),
						x.getPeriod().getEndDate(),
						x.getBranchId(),
						x.getAnyItemApplicationId(),
						x.getConfirmationRootType().value,
						x.getEmploymentRootAtr().value
			    )).collect(Collectors.toList());
	}
	
	@Override
	public List<CompanyApprovalRootExport> findCompanyByBaseDate(String cid, Date baseDate, int appType) {
		return this.companyAppRootRepository.findByBaseDate(cid, baseDate, appType).stream()
				.map(x -> new CompanyApprovalRootExport(
						x.getCompanyId(),
						x.getApprovalId(),
						x.getHistoryId(),
						x.getApplicationType().value,
						x.getPeriod().getStartDate(),
						x.getPeriod().getEndDate(),
						x.getBranchId(),
						x.getAnyItemApplicationId(),
						x.getConfirmationRootType().value,
						x.getEmploymentRootAtr().value
			    )).collect(Collectors.toList());
	}
	
	@Override
	public List<CompanyApprovalRootExport> findCompanyByBaseDateOfCommon(String cid, Date baseDate) {
		return this.companyAppRootRepository.findByBaseDateOfCommon(cid, baseDate).stream()
				.map(x -> new CompanyApprovalRootExport(
						x.getCompanyId(),
						x.getApprovalId(),
						x.getHistoryId(),
						x.getApplicationType().value,
						x.getPeriod().getStartDate(),
						x.getPeriod().getEndDate(),
						x.getBranchId(),
						x.getAnyItemApplicationId(),
						x.getConfirmationRootType().value,
						x.getEmploymentRootAtr().value
			    )).collect(Collectors.toList());
	}
	
	@Override
	public List<ApprovalPhaseExport> findApprovalPhaseByBranchId(String cid, String branchId) {
		return this.appPhaseRepository.getAllApprovalPhasebyCode(cid, branchId).stream()
				.map(x -> new ApprovalPhaseExport(
						x.getCompanyId(),
						x.getBranchId(),
						x.getApprovalPhaseId(),
						x.getApprovalForm().value,
						x.getBrowsingPhase(),
						x.getOrderNumber(),
						x.getApprovers().stream().map(a -> new ApproverExport(
								a.getCompanyId(), 
								a.getApprovalPhaseId(), 
								a.getApproverId(), 
								a.getJobTitleId(), 
								a.getEmployeeId(), 
								a.getOrderNumber(), 
								a.getApprovalAtr().value, 
								a.getConfirmPerson().value))
						.collect(Collectors.toList())
			    )).collect(Collectors.toList());
	}
}
