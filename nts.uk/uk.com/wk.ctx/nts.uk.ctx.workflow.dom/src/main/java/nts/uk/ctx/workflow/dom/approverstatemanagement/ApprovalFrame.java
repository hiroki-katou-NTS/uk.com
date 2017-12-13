package nts.uk.ctx.workflow.dom.approverstatemanagement;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
/**
 * 承認枠
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprovalFrame {
	
	private String companyID;
	
	private String rootStateID;
	
	private Integer rootType;
	
	private Integer phaseOrder;
	
	private Integer frameOrder;
	
	private ApprovalAtr approvalAtr;
	
	private Boolean confirmAtr;
	
	private List<ApproverState> listApproverState;
	
	private String approverID;
	
	private String representerID;
	
	private GeneralDate approvalDate;
	
	private String approvalReason;
	
}
