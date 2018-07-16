package nts.uk.ctx.workflow.pub.service.export;

import java.util.List;

import lombok.Getter;
@Getter
public class ApprovalPhaseStateParam {
	
	private String rootStateID;
	
	private Integer phaseOrder;
	
	private int approvalAtr;
	
	private int approvalForm;
	
	private List<ApprovalFrameParam> listApprovalFrame;
}
