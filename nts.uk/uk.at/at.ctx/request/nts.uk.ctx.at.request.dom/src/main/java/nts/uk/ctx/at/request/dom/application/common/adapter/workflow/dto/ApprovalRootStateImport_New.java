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
public class ApprovalRootStateImport_New {
	
	private String rootStateID;
	
	private List<ApprovalPhaseStateImport_New> listApprovalPhaseState;
	
	private GeneralDate date;
	
	private int rootType;
	
	private String employeeID;
	
}
