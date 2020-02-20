package nts.uk.ctx.hr.shared.dom.approval.rootstate;

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
	
}
