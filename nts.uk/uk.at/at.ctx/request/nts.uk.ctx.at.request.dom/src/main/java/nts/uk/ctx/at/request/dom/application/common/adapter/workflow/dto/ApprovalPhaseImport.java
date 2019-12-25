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
	/** 承認ID */
	private String approvalId;
	/** 承認フェーズ順序 */
	private int phaseOrder;
	/** 承認形態 */
	private int approvalForm;
	/** 閲覧フェーズ */
	private int browsingPhase;
	
	/** 承認者 */
	private List<ApproverInfoImport> approvers;

	public ApprovalPhaseImport(String companyId, String approvalId,int phaseOrder, int approvalForm,
			int browsingPhase) {
		super();
		this.companyId = companyId;
		this.approvalId = approvalId;
		this.phaseOrder = phaseOrder;
		this.approvalForm = approvalForm;
		this.browsingPhase = browsingPhase;
	}

	public void addApproverList(List<ApproverInfoImport> approvers) {
		this.approvers = approvers;
	}
}
