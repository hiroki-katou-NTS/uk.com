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
	private List<ApproverInfoExport> approvers;

	public ApprovalPhaseExport(String companyId, String branchId, String approvalPhaseId, int approvalForm,
			int browsingPhase, int orderNumber) {
		super();
		this.companyId = companyId;
		this.branchId = branchId;
		this.approvalPhaseId = approvalPhaseId;
		this.approvalForm = approvalForm;
		this.browsingPhase = browsingPhase;
		this.orderNumber = orderNumber;
	}

	public void addApproverList(List<ApproverInfoExport> approvers) {
		this.approvers = approvers;
	}
}
