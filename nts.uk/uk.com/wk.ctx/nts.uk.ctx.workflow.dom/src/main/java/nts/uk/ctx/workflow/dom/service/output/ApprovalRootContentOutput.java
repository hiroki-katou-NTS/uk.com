package nts.uk.ctx.workflow.dom.service.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.workflow.dom.approvermanagement.workroot.ApprovalPhase;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Value
@AllArgsConstructor
public class ApprovalRootContentOutput {
	
	public List<ApprovalPhase> listApprovalPhase;
	
	public ErrorFlag errorFlag;
	
}
