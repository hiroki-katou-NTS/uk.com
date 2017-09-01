package nts.uk.ctx.at.request.dom.application.common.service.approvalroot.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseAdaptorDto;

/**
 * 承認フェーズDto
 * 
 * @author vunv
 *
 */
@Data
@AllArgsConstructor
public class ApprovalPhaseAdaptorResult {
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
	
	public static ApprovalPhaseAdaptorResult convertDtoToResult(ApprovalPhaseAdaptorDto phase) {
		return new ApprovalPhaseAdaptorResult(
				phase.getCompanyId(), 
				phase.getBranchId(), 
				phase.getApprovalPhaseId(), 
				phase.getApprovalForm(), 
				phase.getBrowsingPhase(), 
				phase.getOrderNumber(), null);
	}
}
