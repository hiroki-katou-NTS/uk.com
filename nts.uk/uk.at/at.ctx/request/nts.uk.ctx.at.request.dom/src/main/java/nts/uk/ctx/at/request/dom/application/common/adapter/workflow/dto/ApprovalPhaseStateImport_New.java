package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

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
public class ApprovalPhaseStateImport_New {
	
	private Integer phaseOrder;
	
	private String approvalAtr;
	
	private List<ApprovalFrameImport_New> listApprovalFrame;
}
