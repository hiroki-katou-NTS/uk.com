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
import nts.uk.ctx.workflow.pub.approvalroot.ApprovalPhaseDto;
import nts.uk.ctx.workflow.pub.approvalroot.ApprovalRootPub;
import nts.uk.ctx.workflow.pub.approvalroot.ApproverDto;
import nts.uk.ctx.workflow.pub.approvalroot.CompanyApprovalRootDto;
import nts.uk.ctx.workflow.pub.approvalroot.PersonApprovalRootDto;
import nts.uk.ctx.workflow.pub.approvalroot.WkpApprovalRootDto;

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
	public List<PersonApprovalRootDto> findByBaseDate(String cid, String sid, Date standardDate, int appType) {
		return this.personAppRootRepository.findByBaseDate(cid, sid, standardDate, appType).stream()
				.map(x -> new PersonApprovalRootDto(
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
	public List<PersonApprovalRootDto> findByBaseDateOfCommon(String cid, String sid, Date standardDate) {
		return this.personAppRootRepository.findByBaseDateOfCommon(cid, sid, standardDate).stream()
				.map(x -> new PersonApprovalRootDto(
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
	public List<WkpApprovalRootDto> findWkpByBaseDate(String cid, String workplaceId, Date baseDate, int appType) {
		return this.wkpAppRootRepository.findByBaseDate(cid, workplaceId, baseDate, appType).stream()
				.map(x -> new WkpApprovalRootDto(
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
	public List<WkpApprovalRootDto> findWkpByBaseDateOfCommon(String cid, String workplaceId, Date baseDate) {
		return this.wkpAppRootRepository.findByBaseDateOfCommon(cid, workplaceId, baseDate).stream()
				.map(x -> new WkpApprovalRootDto(
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
	public List<CompanyApprovalRootDto> findCompanyByBaseDate(String cid, Date baseDate, int appType) {
		return this.companyAppRootRepository.findByBaseDate(cid, baseDate, appType).stream()
				.map(x -> new CompanyApprovalRootDto(
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
	public List<CompanyApprovalRootDto> findCompanyByBaseDateOfCommon(String cid, Date baseDate) {
		return this.companyAppRootRepository.findByBaseDateOfCommon(cid, baseDate).stream()
				.map(x -> new CompanyApprovalRootDto(
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
	public List<ApprovalPhaseDto> findApprovalPhaseByBranchId(String cid, String branchId) {
		return this.appPhaseRepository.getAllApprovalPhasebyCode(cid, branchId).stream()
				.map(x -> new ApprovalPhaseDto(
						x.getCompanyId(),
						x.getBranchId(),
						x.getApprovalPhaseId(),
						x.getApprovalForm().value,
						x.getBrowsingPhase(),
						x.getOrderNumber(),
						x.getApprovers().stream().map(a -> new ApproverDto(
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
