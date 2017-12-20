package nts.uk.ctx.workflow.dom.service.output;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Value
@AllArgsConstructor
public class ApprovalRepresenterInforOutput {
	
	String approver;
	RepresenterInforOutput representer;
	
	public boolean isPass() {
		return this.representer.getValue().equals(RepresenterInforOutput.Path_Information);
	}
	
}
