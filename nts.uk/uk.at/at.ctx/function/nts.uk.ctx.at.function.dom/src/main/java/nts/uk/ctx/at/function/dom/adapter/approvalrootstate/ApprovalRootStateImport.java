package nts.uk.ctx.at.function.dom.adapter.approvalrootstate;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalRootStateImport {
	
	private String rootStateID;
	
	private GeneralDate approvalRecordDate;
	
	private String employeeID;
	
	private List<ApprovalPhaseStateImport> listApprovalPhaseState;
	
}
