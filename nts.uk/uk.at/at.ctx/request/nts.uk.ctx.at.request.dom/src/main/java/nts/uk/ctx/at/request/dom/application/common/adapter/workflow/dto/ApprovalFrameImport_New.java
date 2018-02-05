package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

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
public class ApprovalFrameImport_New {
	
	private Integer phaseOrder;
	
	private Integer frameOrder;
	
	private String approvalAtr;
	
	private ApprovalBehaviorAtrImport_New approvalAtr_Enum;
	
	private List<ApproverStateImport_New> listApprover;
	
	private String approverID;
	
	private String representerID;
	
	private String approvalReason;
}
