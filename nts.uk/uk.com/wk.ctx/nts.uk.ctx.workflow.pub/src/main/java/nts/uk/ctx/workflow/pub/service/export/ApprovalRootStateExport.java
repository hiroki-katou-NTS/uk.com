package nts.uk.ctx.workflow.pub.service.export;

import java.util.ArrayList;
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
public class ApprovalRootStateExport {
	
	private List<ApprovalPhaseStateExport> listApprovalPhaseState;
	
	public static ApprovalRootStateExport fixData() {
		List<ApprovalPhaseStateExport> lstPhase = new ArrayList<>();
		lstPhase.add(ApprovalPhaseStateExport.fixData());
		return new ApprovalRootStateExport(lstPhase);
	}
}
