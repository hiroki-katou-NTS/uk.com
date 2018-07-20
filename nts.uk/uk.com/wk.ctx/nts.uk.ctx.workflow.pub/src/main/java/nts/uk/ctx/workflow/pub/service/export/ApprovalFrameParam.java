package nts.uk.ctx.workflow.pub.service.export;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
@Getter
public class ApprovalFrameParam {
	private String rootStateID;
	
	private Integer phaseOrder;
	
	private Integer frameOrder;
	
	private int approvalAtr;
	
	private int confirmAtr;
	
	private List<ApproverStateParam> listApproverState;
	
	@Setter
	private String approverID;
	
	@Setter
	private String representerID;
	
	@Setter
	private GeneralDate approvalDate;
	
	@Setter
	private String approvalReason;
}
