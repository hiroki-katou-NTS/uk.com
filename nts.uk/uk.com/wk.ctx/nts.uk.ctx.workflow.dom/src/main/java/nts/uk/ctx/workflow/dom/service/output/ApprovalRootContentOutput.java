package nts.uk.ctx.workflow.dom.service.output;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalRootState;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Value
@AllArgsConstructor
public class ApprovalRootContentOutput {
	
	public ApprovalRootState approvalRootState;
	
	public ErrorFlag errorFlag;
	
}
