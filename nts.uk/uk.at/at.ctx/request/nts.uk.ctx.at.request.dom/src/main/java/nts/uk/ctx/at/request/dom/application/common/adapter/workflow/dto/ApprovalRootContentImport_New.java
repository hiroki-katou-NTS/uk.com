package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

import lombok.AllArgsConstructor;
import lombok.Value;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Value
@AllArgsConstructor
public class ApprovalRootContentImport_New {
	
	public ApprovalRootStateImport_New approvalRootState;
	
	private ErrorFlagImport errorFlag;
	
}
