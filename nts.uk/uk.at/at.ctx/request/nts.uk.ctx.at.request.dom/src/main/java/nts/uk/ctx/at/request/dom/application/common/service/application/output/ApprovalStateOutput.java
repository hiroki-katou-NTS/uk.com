package nts.uk.ctx.at.request.dom.application.common.service.application.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalBehaviorAtrImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverStateImport_New;

/**
 * @author hiep.ld
 *
 */
@AllArgsConstructor
@Getter
public class ApprovalStateOutput {
	private String approverID;
	
	private ApprovalBehaviorAtrImport_New approvalAtr;
	
	private String agentID;
	
	private String approverName;
	
	private String representerID;
	
	private String representerName;
	
	private GeneralDateTime approvalDate;
	
	private String approvalReason;
	
	@Setter
	private String sMail;
	@Setter
	private String sMailAgent;
	
	private Integer approverInListOrder;
	
	public static ApprovalStateOutput fromApprovalStateImportToOutput(ApproverStateImport_New approvalState){
		return new ApprovalStateOutput(
				approvalState.getApproverID(),
				approvalState.getApprovalAtr(),
				approvalState.getAgentID(),
				approvalState.getApproverName(), 
				approvalState.getRepresenterID(), 
				approvalState.getRepresenterName(),
				approvalState.getApprovalDate(),
				approvalState.getApprovalReason(),
				approvalState.getApproverEmail(),
				approvalState.getRepresenterEmail(),
				approvalState.getApproverInListOrder());
	}
}
