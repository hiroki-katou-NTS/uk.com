package nts.uk.ctx.at.request.dom.application.common.service.application.output;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
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
	
	private String approverName;
	
	private String representerID;
	
	private String representerName;
	
	@Setter
	private String sMail;
	
	public static ApprovalStateOutput fromApprovalStateImportToOutput(ApproverStateImport_New approvalState){
		return new ApprovalStateOutput(approvalState.getApproverID(), approvalState.getApproverName(), approvalState.getRepresenterID(), approvalState.getRepresenterName());
	}

	public ApprovalStateOutput(String approverID, String approverName, String representerID, String representerName) {
		super();
		this.approverID = approverID;
		this.approverName = approverName;
		this.representerID = representerID;
		this.representerName = representerName;
	}
}
