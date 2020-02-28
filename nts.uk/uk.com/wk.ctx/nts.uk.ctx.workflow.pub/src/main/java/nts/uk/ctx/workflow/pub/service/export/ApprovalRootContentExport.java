package nts.uk.ctx.workflow.pub.service.export;

import lombok.AllArgsConstructor;
import lombok.Value;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@Value
@AllArgsConstructor
public class ApprovalRootContentExport {
	
	public ApprovalRootStateExport approvalRootState;
	
	private ErrorFlagExport errorFlag;
	
	public static ApprovalRootContentExport fixData() {
		ApprovalRootStateExport approvalRootState = ApprovalRootStateExport.fixData();
		ErrorFlagExport errorFlag = ErrorFlagExport.NO_ERROR;
		return new ApprovalRootContentExport(approvalRootState, errorFlag);
	}
}
