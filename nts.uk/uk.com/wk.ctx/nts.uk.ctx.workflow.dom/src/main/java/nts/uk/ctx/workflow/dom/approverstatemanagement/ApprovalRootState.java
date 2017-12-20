package nts.uk.ctx.workflow.dom.approverstatemanagement;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
/**
 * 承認ルートインスタンス
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprovalRootState {
	
	private String rootStateID;
	
	private RootType rootType;
	
	private String historyID;
	
	private GeneralDate approvalRecordDate;
	
	private String employeeID;
	
	private List<ApprovalPhaseState> listApprovalPhaseState;
}
