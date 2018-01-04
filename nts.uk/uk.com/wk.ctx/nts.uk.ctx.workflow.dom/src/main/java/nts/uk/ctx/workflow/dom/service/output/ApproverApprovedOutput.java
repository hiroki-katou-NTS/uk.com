package nts.uk.ctx.workflow.dom.service.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Value;
/**
 * 承認を行った承認者一覧
 * @author Doan Duy Hung
 *
 */
@Value
@AllArgsConstructor
public class ApproverApprovedOutput {
	
	private List<ApproverWithFlagOutput> listApproverWithFlagOutput;
	
	private List<String> listApprover;
	
}
