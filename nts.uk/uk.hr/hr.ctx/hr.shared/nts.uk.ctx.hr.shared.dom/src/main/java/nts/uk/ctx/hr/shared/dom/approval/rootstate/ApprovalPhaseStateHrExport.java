package nts.uk.ctx.hr.shared.dom.approval.rootstate;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 
 * @author Laitv
 *
 */
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class ApprovalPhaseStateHrExport {
	
	private Integer phaseOrder;
	
	private ApprovalBehaviorAtrHrExport approvalAtr;
	
	private ApprovalFormHrExport approvalForm;
	
	private List<ApprovalFrameHrExport> listApprovalFrame;
	
	public static ApprovalPhaseStateHrExport fixData() {
		
		Integer phaseOrder = 1;
		ApprovalBehaviorAtrHrExport approvalAtr = ApprovalBehaviorAtrHrExport.UNAPPROVED;
		ApprovalFormHrExport approvalForm = ApprovalFormHrExport.EVERYONE_APPROVED;
		List<ApprovalFrameHrExport> lstFrame = new ArrayList<>();
		lstFrame.add(ApprovalFrameHrExport.fixData(1));
		lstFrame.add(ApprovalFrameHrExport.fixData(2));
		lstFrame.add(ApprovalFrameHrExport.fixData(3));
		lstFrame.add(ApprovalFrameHrExport.fixData(4));
		lstFrame.add(ApprovalFrameHrExport.fixData(5));
		return new ApprovalPhaseStateHrExport(phaseOrder, approvalAtr, approvalForm, lstFrame);
	}
}
