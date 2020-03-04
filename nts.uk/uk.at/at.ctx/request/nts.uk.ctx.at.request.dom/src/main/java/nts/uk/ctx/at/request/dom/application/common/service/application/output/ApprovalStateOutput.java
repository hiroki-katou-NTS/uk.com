package nts.uk.ctx.at.request.dom.application.common.service.application.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalBehaviorAtrImport_New;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverStateImport_New;

/**
 * @author hiep.ld
 *
 */
@AllArgsConstructor
@Data
@Getter
public class ApprovalStateOutput {
	private String approverID;
	
	private ApprovalBehaviorAtrImport_New approvalAtr;
	
	private String agentID;
	
	private String approverName;
	
	private String representerID;
	
	private String representerName;
	
	private GeneralDate approvalDate;
	
	private String approvalReason;
	
	@Setter
	private String sMail;
	@Setter
	private String sMailAgent;
	
	public static ApprovalStateOutput fromApprovalStateImportToOutput(ApproverStateImport_New approvalState){
		return new ApprovalStateOutput(
				approvalState.getApproverID(),
				approvalState.getApprovalAtr(),
				approvalState.getAgentID(),
				approvalState.getApproverName(), 
				approvalState.getRepresenterID(), 
				approvalState.getRepresenterName(),
				approvalState.getApprovalDate(),
				approvalState.getApprovalReason());
	}

	public ApprovalStateOutput(String approverID, ApprovalBehaviorAtrImport_New approvalAtr, String agentID, String approverName, 
			String representerID, String representerName, GeneralDate approvalDate, String approvalReason) {
		super();
		this.approverID = approverID;
		this.approvalAtr = approvalAtr;
		this.agentID = agentID;
		this.approverName = approverName;
		this.representerID = representerID;
		this.representerName = representerName;
		this.approvalDate = approvalDate;
		this.approvalReason = approvalReason;
	}
}
