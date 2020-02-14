package nts.uk.ctx.hr.shared.dom.approval.rootstate;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
/**
 * 
 * @author Laitv
 *
 */
@AllArgsConstructor
@Value
public class ApprovalRootStateHrExport {
	
	private List<ApprovalPhaseStateHrExport> listApprovalPhaseState;
	
	public static ApprovalRootStateHrExport fixData() {
		List<ApprovalPhaseStateHrExport> lstPhase = new ArrayList<>();
		lstPhase.add(ApprovalPhaseStateHrExport.fixData());
		return new ApprovalRootStateHrExport(lstPhase);
	}
}
