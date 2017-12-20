package nts.uk.ctx.workflow.dom.approvermanagement.approvalroot.output;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalForm;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.Approver;
import nts.uk.ctx.workflow.dom.service.output.ApproverInfo;
import nts.uk.ctx.workflow.dom.service.output.ErrorFlag;

/**
 * 承認フェーズDto
 * 
 * @author vunv
 *
 */
@Getter
@AllArgsConstructor
public class ApprovalPhaseOutput {
	/** 会社ID */
	private String companyId;
	/** 分岐ID */
	private String branchId;
	/** 承認フェーズID */
	private String approvalPhaseId;
	/** 承認形態 */
	private int approvalForm;
	/** 閲覧フェーズ */
	private int browsingPhase;
	/** 順序 */
	private int orderNumber;
	/** 承認者*/
	private List<ApproverInfo> approvers;
	
	public static ApprovalPhaseOutput convertToOutputData(ApprovalPhase phase) {
		return new ApprovalPhaseOutput(
				phase.getCompanyId(), 
				phase.getBranchId(), 
				phase.getApprovalPhaseId(), 
				phase.getApprovalForm().value, 
				phase.getBrowsingPhase(), 
				phase.getOrderNumber(), null);
	}
	
	public void addApproverList(List<ApproverInfo> approvers) {
		this.approvers = approvers;
	}
	
	public ErrorFlag checkError() {
		if (this.approvers.size() > 10) {
			return ErrorFlag.APPROVER_UP_10;
		}
		
		if (this.approvers.isEmpty()) {
			return ErrorFlag.NO_APPROVER;
		}
		
		return ErrorFlag.NO_ERROR;
	}
	
	public boolean isSingleApproved() {
		return this.approvalForm == ApprovalForm.SINGLE_APPROVED.value;
	}
	
	public boolean containsConfirmer() {
		return this.approvers.stream().anyMatch(a -> a.getIsConfirmPerson());
	}
	
	public boolean containsAny(List<Approver> masterApprovers) {
		Set<String> masterApproverIds = masterApprovers.stream().map(a -> a.getApproverId()).collect(Collectors.toSet());
		return this.approvers.stream().anyMatch(a -> masterApproverIds.contains(a.getSid()));
	}
}
