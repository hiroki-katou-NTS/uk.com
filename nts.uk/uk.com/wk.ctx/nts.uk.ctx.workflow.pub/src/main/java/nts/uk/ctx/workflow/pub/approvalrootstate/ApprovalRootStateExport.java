package nts.uk.ctx.workflow.pub.approvalrootstate;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalRootStateExport {
	
	private String rootStateID;
	
	private GeneralDate approvalRecordDate;
	
	private String employeeID;
	
	private List<ApprovalPhaseStateExport> listApprovalPhaseState;
	
}
