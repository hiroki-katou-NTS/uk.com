package nts.uk.ctx.workflow.pub.service.export;

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
public class ApproverApprovedExport {
	
	private List<ApproverWithFlagExport> listApproverWithFlagOutput;
	
	private List<String> listApprover;
}
