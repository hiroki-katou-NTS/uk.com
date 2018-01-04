package nts.uk.ctx.workflow.pub.service.export;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@Value
@AllArgsConstructor
public class ApproverWithFlagExport {
	
	private String employeeID;
	
	private Boolean agentFlag;
	
}
