package nts.uk.ctx.workflow.pub.service.export;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.uk.ctx.workflow.pub.approvalroot.export.ApprovalPhaseExport;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Value
public class ApprovalRootStateExport {
	
	private List<ApprovalPhaseStateExport> listApprovalPhaseState;
	
}
