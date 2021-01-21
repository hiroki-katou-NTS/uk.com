package nts.uk.ctx.workflow.pub.service.export;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Value
public class ApprovalRootStateExport {
	
	private String rootStateID;
	
	private int rootType;
	
	private GeneralDate approvalRecordDate;
	
	private String employeeID;
	
	private List<ApprovalPhaseStateExport> listApprovalPhaseState;
	
	public static ApprovalRootStateExport fixData() {
		List<ApprovalPhaseStateExport> lstPhase = new ArrayList<>();
		lstPhase.add(ApprovalPhaseStateExport.fixData());
		return new ApprovalRootStateExport("appID", 0, GeneralDate.today(), "employeeID", lstPhase);
	}
}
