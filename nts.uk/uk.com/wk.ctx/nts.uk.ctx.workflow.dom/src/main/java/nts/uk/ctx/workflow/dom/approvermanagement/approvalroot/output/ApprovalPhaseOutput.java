package nts.uk.ctx.workflow.dom.approvermanagement.approvalroot.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;

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
}
