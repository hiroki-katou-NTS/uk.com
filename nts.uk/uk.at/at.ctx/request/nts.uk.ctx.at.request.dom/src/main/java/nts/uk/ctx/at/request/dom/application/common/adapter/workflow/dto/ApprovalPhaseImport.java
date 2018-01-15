package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 承認フェーズDto
 * 
 * @author vunv
 *
 */
@Getter
@AllArgsConstructor
public class ApprovalPhaseImport {
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
	/** 承認者 */
	private List<ApproverInfoImport> approvers;

	public ApprovalPhaseImport(String companyId, String branchId, String approvalPhaseId, int approvalForm,
			int browsingPhase, int orderNumber) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.approvalPhaseId = approvalPhaseId;
		this.approvalForm = approvalForm;
		this.browsingPhase = browsingPhase;
		this.orderNumber = orderNumber;
	}

	public void addApproverList(List<ApproverInfoImport> approvers) {
		this.approvers = approvers;
	}
}
