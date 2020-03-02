package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
import nts.arc.time.GeneralDate;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Value
public class ApprovalFrameImport_New {
	
	private Integer frameOrder;
	
	private List<ApproverStateImport_New> listApprover;
	
	private int confirmAtr;
	
	private GeneralDate appDate;
}
