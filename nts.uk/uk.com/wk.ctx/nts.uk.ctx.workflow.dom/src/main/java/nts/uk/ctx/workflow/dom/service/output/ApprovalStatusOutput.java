package nts.uk.ctx.workflow.dom.service.output;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.workflow.dom.approverstatemanagement.ApprovalBehaviorAtr;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprovalStatusOutput {
	
	private Boolean approvalFlag;
	
	private ApprovalBehaviorAtr approvalAtr;
	
	private Boolean approvableFlag;
	
	private Boolean subExpFlag;
	
}
