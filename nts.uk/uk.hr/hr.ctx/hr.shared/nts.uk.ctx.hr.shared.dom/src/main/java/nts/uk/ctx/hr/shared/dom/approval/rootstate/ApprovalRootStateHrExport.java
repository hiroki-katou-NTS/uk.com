package nts.uk.ctx.hr.shared.dom.approval.rootstate;

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
	
}
