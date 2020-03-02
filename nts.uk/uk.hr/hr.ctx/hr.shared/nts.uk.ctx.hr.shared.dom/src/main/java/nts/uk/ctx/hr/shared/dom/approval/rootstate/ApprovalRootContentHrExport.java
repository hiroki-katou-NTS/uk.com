package nts.uk.ctx.hr.shared.dom.approval.rootstate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 
 * @author Laitv
 *	
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalRootContentHrExport {
	
	public ApprovalRootStateHrExport approvalRootState;
	
	private ErrorFlagHrExport errorFlag;
	
}
