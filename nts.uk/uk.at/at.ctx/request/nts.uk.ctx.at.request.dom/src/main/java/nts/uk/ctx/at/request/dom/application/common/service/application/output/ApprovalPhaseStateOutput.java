package nts.uk.ctx.at.request.dom.application.common.service.application.output;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalBehaviorAtrImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalPhaseStateImport_New;
/**
 * @author hiep.ld
 *
 */
@AllArgsConstructor
@Data
@Getter
public class ApprovalPhaseStateOutput {
	private Integer phaseOrder;
	
	private ApprovalBehaviorAtrImport_New approvalAtr;
	
	private List<ApprovalFrameOutput> listApprovalFrame;
	
	public static ApprovalPhaseStateOutput fromRootStateImportToOutput(ApprovalPhaseStateImport_New approvalPhase){
		return new ApprovalPhaseStateOutput(approvalPhase.getPhaseOrder(), approvalPhase.getApprovalAtr(), approvalPhase.getListApprovalFrame().stream().map(x ->{
			return ApprovalFrameOutput.fromApprovalFrameImportToOutput(x);
		}).collect(Collectors.toList()));
	}
}
