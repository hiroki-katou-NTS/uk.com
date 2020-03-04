package nts.uk.ctx.hr.shared.dom.approval.rootstate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author laitv
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ApproverStateHrExport {
	
	private String approverID;
	
	private ApprovalBehaviorAtrHrExport approvalAtr;
	
	private String agentID;
	
	private String approverName;
	
	private String representerID;
	
	private String representerName;
	
	private GeneralDate approvalDate;
	
	private String approvalReason;

}
