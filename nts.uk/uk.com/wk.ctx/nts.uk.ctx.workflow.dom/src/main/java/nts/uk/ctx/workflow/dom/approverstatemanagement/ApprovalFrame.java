package nts.uk.ctx.workflow.dom.approverstatemanagement;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
/**
 * 承認枠
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprovalFrame {
	
	private String rootStateID;
	
	private Integer phaseOrder;
	
	private Integer frameOrder;
	
	@Setter
	private ApprovalBehaviorAtr approvalAtr;
	
	private Boolean confirmAtr;
	
	private List<ApproverState> listApproverState;
	
	@Setter
	private String approverID;
	
	@Setter
	private String representerID;
	
	private GeneralDate approvalDate;
	
	private String approvalReason;
	
}
