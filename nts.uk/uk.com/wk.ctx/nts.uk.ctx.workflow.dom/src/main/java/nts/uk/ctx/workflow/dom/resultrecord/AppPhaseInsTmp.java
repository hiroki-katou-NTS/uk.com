package nts.uk.ctx.workflow.dom.resultrecord;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AppPhaseInsTmp {
	
	private String rootID;
	
	private Integer phaseOrder;
	
	private Integer approvalForm;
	
	public boolean equalOther(AppPhaseInsTmp appPhaseInsTmp) {
		if(rootID.equals(appPhaseInsTmp.getRootID()) &&
			phaseOrder==appPhaseInsTmp.getPhaseOrder() &&
			approvalForm==appPhaseInsTmp.getApprovalForm()) {
			return true;
		}
		return false;
	}
}
