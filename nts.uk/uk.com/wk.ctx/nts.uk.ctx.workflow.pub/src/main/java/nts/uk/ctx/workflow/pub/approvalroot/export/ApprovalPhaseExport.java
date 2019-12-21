package nts.uk.ctx.workflow.pub.approvalroot.export;

import java.util.List;

import lombok.Getter;

/**
 * 承認フェーズDto
 * 
 * @author vunv
 *
 */
@Getter
public class ApprovalPhaseExport {
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
	private List<ApproverInfoExport> approvers;

	public ApprovalPhaseExport(String companyId, String approvalId, int phaseOrder, int approvalForm,
			int browsingPhase) {
		super();
		this.companyId = companyId;
		this.approvalId = approvalId;
		this.phaseOrder = phaseOrder;
		this.approvalForm = approvalForm;
		this.browsingPhase = browsingPhase;
	}

	public void addApproverList(List<ApproverInfoExport> approvers) {
		this.approvers = approvers;
	}
}
