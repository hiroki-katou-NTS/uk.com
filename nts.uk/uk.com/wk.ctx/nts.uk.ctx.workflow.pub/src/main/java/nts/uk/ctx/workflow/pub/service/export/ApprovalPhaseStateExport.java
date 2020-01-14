package nts.uk.ctx.workflow.pub.service.export;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Value
public class ApprovalPhaseStateExport {
	
	private Integer phaseOrder;
	
	private ApprovalBehaviorAtrExport approvalAtr;
	
	private ApprovalFormExport approvalForm;
	
	private List<ApprovalFrameExport> listApprovalFrame;
	
}
