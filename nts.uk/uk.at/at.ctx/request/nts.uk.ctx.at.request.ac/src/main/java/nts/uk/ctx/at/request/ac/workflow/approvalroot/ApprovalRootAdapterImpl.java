package nts.uk.ctx.at.request.ac.workflow.approvalroot;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.CompanyAppRootImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.PersonAppRootImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.WkpAppRootImport;
import nts.uk.ctx.workflow.pub.approvalroot.ApprovalRootPub;

@Stateless
public class ApprovalRootAdapterImpl implements ApprovalRootAdapter
{

	@Inject
	private ApprovalRootPub approvalRootPub;
	
	@Override
	public List<PersonAppRootImport> findByBaseDate(String cid, String sid, Date standardDate, int appType) {
		return this.approvalRootPub.findByBaseDate(cid, sid, standardDate, appType).stream()
				.map(x -> new PersonAppRootImport(
						x.getCompanyId(),
						x.getApprovalId(),
						x.getEmployeeId(),
						x.getHistoryId(),
						x.getApplicationType(),
						x.getStartDate(),
						x.getEndDate(),
						x.getBranchId(),
						x.getAnyItemApplicationId(),
						x.getConfirmationRootType(),
						x.getEmploymentRootAtr()
			    )).collect(Collectors.toList());
	}
	
	@Override
	public List<PersonAppRootImport> findByBaseDateOfCommon(String cid, String sid, Date standardDate) {
		return this.approvalRootPub.findByBaseDateOfCommon(cid, sid, standardDate).stream()
				.map(x -> new PersonAppRootImport(
						x.getCompanyId(),
						x.getApprovalId(),
						x.getEmployeeId(),
						x.getHistoryId(),
						x.getApplicationType(),
						x.getStartDate(),
						x.getEndDate(),
						x.getBranchId(),
						x.getAnyItemApplicationId(),
						x.getConfirmationRootType(),
						x.getEmploymentRootAtr()
			    )).collect(Collectors.toList());
	}

	@Override
	public List<WkpAppRootImport> findWkpByBaseDate(String cid, String workPlaceId, Date baseDate, int appType) {
		return this.approvalRootPub.findWkpByBaseDate(cid, workPlaceId, baseDate, appType).stream()
				.map(x -> new WkpAppRootImport(
						x.getCompanyId(),
						x.getApprovalId(),
						x.getWorkplaceId(),
						x.getHistoryId(),
						x.getApplicationType(),
						x.getStartDate(),
						x.getEndDate(),
						x.getBranchId(),
						x.getAnyItemApplicationId(),
						x.getConfirmationRootType(),
						x.getEmploymentRootAtr()
			    )).collect(Collectors.toList());
	}

	@Override
	public List<WkpAppRootImport> findWkpByBaseDateOfCommon(String cid, String workPlaceId, Date baseDate) {
		return this.approvalRootPub.findWkpByBaseDateOfCommon(cid, workPlaceId, baseDate).stream()
				.map(x -> new WkpAppRootImport(
						x.getCompanyId(),
						x.getApprovalId(),
						x.getWorkplaceId(),
						x.getHistoryId(),
						x.getApplicationType(),
						x.getStartDate(),
						x.getEndDate(),
						x.getBranchId(),
						x.getAnyItemApplicationId(),
						x.getConfirmationRootType(),
						x.getEmploymentRootAtr()
			    )).collect(Collectors.toList());
	}

	@Override
	public List<CompanyAppRootImport> findCompanyByBaseDate(String cid, Date baseDate, int appType) {
		return this.approvalRootPub.findCompanyByBaseDate(cid, baseDate, appType).stream()
				.map(x -> new CompanyAppRootImport(
						x.getCompanyId(),
						x.getApprovalId(),
						x.getHistoryId(),
						x.getApplicationType(),
						x.getStartDate(),
						x.getEndDate(),
						x.getBranchId(),
						x.getAnyItemApplicationId(),
						x.getConfirmationRootType(),
						x.getEmploymentRootAtr()
			    )).collect(Collectors.toList());
	}

	@Override
	public List<CompanyAppRootImport> findCompanyByBaseDateOfCommon(String cid, Date baseDate) {
		return this.approvalRootPub.findCompanyByBaseDateOfCommon(cid, baseDate).stream()
				.map(x -> new CompanyAppRootImport(
						x.getCompanyId(),
						x.getApprovalId(),
						x.getHistoryId(),
						x.getApplicationType(),
						x.getStartDate(),
						x.getEndDate(),
						x.getBranchId(),
						x.getAnyItemApplicationId(),
						x.getConfirmationRootType(),
						x.getEmploymentRootAtr()
			    )).collect(Collectors.toList());
	}

	@Override
	public List<ApprovalPhaseImport> findApprovalPhaseByBranchId(String cid, String branchId) {
		return this.approvalRootPub.findApprovalPhaseByBranchId(cid, branchId).stream()
				.map(x -> new ApprovalPhaseImport(
						x.getCompanyId(),
						x.getBranchId(),
						x.getApprovalPhaseId(),
						x.getApprovalForm(),
						x.getBrowsingPhase(),
						x.getOrderNumber(),
						x.getApproverDtos().stream().map(a -> new ApproverImport(
								a.getCompanyId(), 
								a.getApprovalPhaseId(), 
								a.getApproverId(), 
								a.getJobTitleId(), 
								a.getEmployeeId(), 
								a.getOrderNumber(), 
								a.getApprovalAtr(), 
								a.getConfirmPerson()))
						.collect(Collectors.toList())
			    )).collect(Collectors.toList());
	}
}

