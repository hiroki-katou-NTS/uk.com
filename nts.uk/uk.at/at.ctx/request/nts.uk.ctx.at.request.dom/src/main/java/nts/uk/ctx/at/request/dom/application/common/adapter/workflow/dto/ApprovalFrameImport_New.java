package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprovalFrameImport_New {
	
	private Integer frameOrder;
	
	private List<ApproverStateImport_New> listApprover;
	
	private int confirmAtr;
	
	private GeneralDate appDate;
	
	public void setListApprover(List<ApproverStateImport_New> listApprover) {
		this.listApprover = listApprover;
	}
}
