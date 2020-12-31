package nts.uk.ctx.at.record.dom.adapter.approvalrootstate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ApprovalRootStateImport {
	
	private String rootStateID;
	
	private GeneralDate approvalRecordDate;
	
	private String employeeID;
	
	private List<ApprovalPhaseStateImport> listApprovalPhaseState;
	
}
