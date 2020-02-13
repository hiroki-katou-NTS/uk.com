package nts.uk.ctx.workflow.pub.service.export;

import java.util.ArrayList;
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
	
	public static ApprovalPhaseStateExport fixData() {
		
		Integer phaseOrder = 1;
		ApprovalBehaviorAtrExport approvalAtr = ApprovalBehaviorAtrExport.UNAPPROVED;
		ApprovalFormExport approvalForm = ApprovalFormExport.EVERYONE_APPROVED;
		List<ApprovalFrameExport> lstFrame = new ArrayList<>();
		lstFrame.add(ApprovalFrameExport.fixData(1));
		lstFrame.add(ApprovalFrameExport.fixData(2));
		lstFrame.add(ApprovalFrameExport.fixData(3));
		lstFrame.add(ApprovalFrameExport.fixData(4));
		lstFrame.add(ApprovalFrameExport.fixData(5));
		return new ApprovalPhaseStateExport(phaseOrder, approvalAtr, approvalForm, lstFrame);
	}
}
