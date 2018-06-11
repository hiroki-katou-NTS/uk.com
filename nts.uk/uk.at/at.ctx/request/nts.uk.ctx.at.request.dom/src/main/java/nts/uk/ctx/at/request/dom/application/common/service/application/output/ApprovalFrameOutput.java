package nts.uk.ctx.at.request.dom.application.common.service.application.output;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalBehaviorAtrImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalFrameImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverStateImport_New;

/**
 * @author hiep.ld
 *
 */
@AllArgsConstructor
@Data
@Getter
public class ApprovalFrameOutput {
	private Integer phaseOrder;

	private Integer frameOrder;

	private ApprovalBehaviorAtrImport_New approvalAtr;

	private List<ApprovalStateOutput> listApprover;

	private String approverID;

	private String approverName;

	private String representerID;

	private String representerName;

	private String approvalReason;

	public static ApprovalFrameOutput fromApprovalFrameImportToOutput(ApprovalFrameImport_New approvalFrame) {
		return new ApprovalFrameOutput(approvalFrame.getPhaseOrder(), approvalFrame.getFrameOrder(),
				approvalFrame.getApprovalAtr(), approvalFrame.getListApprover().stream().map(x -> {
					return ApprovalStateOutput.fromApprovalStateImportToOutput(x);
				}).collect(Collectors.toList()), approvalFrame.getApproverID(), approvalFrame.getApproverName(),
				approvalFrame.getRepresenterID(), approvalFrame.getRepresenterName(),
				approvalFrame.getApprovalReason());
	}
}
