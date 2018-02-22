package nts.uk.ctx.workflow.pub.service.export;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Value
public class ApprovalFrameExport {
	
	private Integer phaseOrder;
	
	private Integer frameOrder;
	
	private Integer approvalAtr_Enum;
	
	private String approvalAtr;
	
	private List<ApproverStateExport> listApprover;
	
	private String approverID;
	
	private String representerID;
	
	private String approvalReason;
}
