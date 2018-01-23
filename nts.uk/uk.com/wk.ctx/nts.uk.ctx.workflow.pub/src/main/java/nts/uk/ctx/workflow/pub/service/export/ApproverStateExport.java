package nts.uk.ctx.workflow.pub.service.export;

import lombok.AllArgsConstructor;
import lombok.Value;

/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Value
public class ApproverStateExport {
	
	private String approverID;
	
	private String representerID;
}
