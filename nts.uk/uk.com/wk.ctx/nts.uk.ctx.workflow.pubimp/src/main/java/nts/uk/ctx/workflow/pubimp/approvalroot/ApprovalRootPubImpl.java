package nts.uk.ctx.workflow.pubimp.approvalroot;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.workflow.dom.approvermanagement.approvalroot.ApprovalRootService;
import nts.uk.ctx.workflow.dom.approvermanagement.approvalroot.JobtitleToApproverService;
import nts.uk.ctx.workflow.dom.approvermanagement.approvalroot.output.ApprovalPhaseOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.approvalroot.output.ApprovalRootOutput;
import nts.uk.ctx.workflow.dom.approvermanagement.approvalroot.output.ApproverInfo;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ConfirmPerson;
import nts.uk.ctx.workflow.pub.approvalroot.ApprovalRootPub;
import nts.uk.ctx.workflow.pub.approvalroot.export.ApprovalPhaseExport;
import nts.uk.ctx.workflow.pub.approvalroot.export.ApprovalRootExport;
import nts.uk.ctx.workflow.pub.approvalroot.export.ApproverInfoExport;
import nts.uk.ctx.workflow.pub.approvalroot.export.ErrorFlag;

@Stateless
public class ApprovalRootPubImpl implements ApprovalRootPub {

	@Inject
	private ApprovalRootService approvalRootService;
	
	@Inject
	private JobtitleToApproverService jobtitleToApproverService;

	@Override
	public List<ApprovalRootExport> getApprovalRootOfSubjectRequest(String cid, String sid, int employmentRootAtr,
			int appType, GeneralDate standardDate) {
		List<ApprovalRootOutput> approvalData = this.approvalRootService.getApprovalRootOfSubjectRequest(cid, sid, employmentRootAtr, appType, standardDate);
		if (CollectionUtil.isEmpty(approvalData)) {
			return Collections.emptyList();
		}
		
		return approvalData.stream().map(x -> { 
			ApprovalRootExport export =  new ApprovalRootExport(
					x.getCompanyId(), 
					x.getWorkplaceId(), 
					x.getApprovalId(), 
					x.getEmployeeId(), 
					x.getHistoryId(), 
					x.getStartDate(), 
					x.getEndDate(), 
					x.getBranchId(), 
					x.getAnyItemApplicationId()); 
					
			export.addDataType(x.getApplicationType(), x.getConfirmationRootType(), x.getEmploymentRootAtr());
			export.addBeforeApprovers(this.convertApprovalPhaseListBefore(x.getBeforeApprovers()));
			export.addAfterApprovers(this.convertApprovalPhaseListAfter(x.getAfterApprovers()));
			return export;
			}).collect(Collectors.toList());
	}

	@Override
	public List<ApprovalRootExport> adjustmentData(String cid, String sid, GeneralDate baseDate,
			List<ApprovalRootExport> appDatas) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ErrorFlag checkError(List<ApprovalPhaseExport> beforeDatas, List<ApprovalPhaseExport> afterDatas) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Convert before adjustment data
	 * 
	 * @param beforeApprovers
	 * @return
	 */
	private List<ApprovalPhaseExport> convertApprovalPhaseListBefore(List<ApprovalPhase> beforeApprovers) {
		return beforeApprovers.stream()
				.map(m -> {
					ApprovalPhaseExport phase = new ApprovalPhaseExport(
							m.getCompanyId(), 
							m.getBranchId(), 
							m.getApprovalPhaseId(), 
							m.getApprovalForm().value, 
							m.getBrowsingPhase(), 
							m.getOrderNumber());
					
					phase.addApproverList(m.getApprovers().stream().map(a -> 
								new ApproverInfoExport(
										a.getEmployeeId(), 
										a.getApprovalPhaseId(), 
										a.getConfirmPerson() == ConfirmPerson.CONFIRM? true: false,
									    a.getOrderNumber())).collect(Collectors.toList()));
					return phase;
			}).collect(Collectors.toList());
	}

	/**
	 * convert after adjustment data
	 * @param beforeApprovers
	 * @return
	 */
	private List<ApprovalPhaseExport> convertApprovalPhaseListAfter(List<ApprovalPhaseOutput> beforeApprovers) {
		return beforeApprovers.stream()
				.map(m -> {
					ApprovalPhaseExport phase = new ApprovalPhaseExport(
							m.getCompanyId(), 
							m.getBranchId(), 
							m.getApprovalPhaseId(), 
							m.getApprovalForm(), 
							m.getBrowsingPhase(), 
							m.getOrderNumber());
					
					phase.addApproverList(m.getApprovers().stream().map(a -> {
							ApproverInfoExport export = new ApproverInfoExport(a.getSid(), a.getApprovalPhaseId(), a.getIsConfirmPerson(), a.getOrderNumber());
							export.addEmployeeName(a.getName());
							return export;
						}).collect(Collectors.toList()));
					return phase;
			}).collect(Collectors.toList());
	}

	@Override
	public List<ApproverInfoExport> convertToApprover(String cid, String sid, GeneralDate baseDate, String jobTitleId) {
		List<ApproverInfo> approvers = this.jobtitleToApproverService.convertToApprover(cid, sid, baseDate, jobTitleId);
		if (!CollectionUtil.isEmpty(approvers)) {
			return approvers.stream().map(x -> {
				ApproverInfoExport export = new ApproverInfoExport(x.getSid(), x.getApprovalPhaseId(), x.getIsConfirmPerson(), x.getOrderNumber());
				export.addEmployeeName(x.getName());
				return export;
			}).collect(Collectors.toList());
		}
		
		return null;
	}
	
}
