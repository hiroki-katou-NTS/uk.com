package nts.uk.ctx.workflow.dom.approverstatemanagement;

import lombok.AllArgsConstructor;
import lombok.Getter;
/**
 * 承認枠 : 承認者
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApproverState {
	
	private String companyID;
	
	private String rootStateID;
	
	private Integer rootType;
	
	private Integer phaseOrder;
	
	private Integer frameOrder;
	
	private String approverID;
}
