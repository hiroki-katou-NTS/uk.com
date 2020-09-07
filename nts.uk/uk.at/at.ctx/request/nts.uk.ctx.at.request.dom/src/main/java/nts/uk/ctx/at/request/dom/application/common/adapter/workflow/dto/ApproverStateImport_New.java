package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Value
public class ApproverStateImport_New {
	
	private String approverID;
	
	private ApprovalBehaviorAtrImport_New approvalAtr;
	
	private String agentID;
	
	private String approverName;
	
	private String representerID;
	
	private String representerName;
	
	private GeneralDate approvalDate;
	
	private String approvalReason;
	
	private String approverEmail;
	
	private String representerEmail;
	
	private Integer approverInListOrder;
}
