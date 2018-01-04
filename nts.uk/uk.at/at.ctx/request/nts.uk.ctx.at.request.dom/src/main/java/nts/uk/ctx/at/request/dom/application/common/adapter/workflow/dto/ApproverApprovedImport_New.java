package nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto;

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
public class ApproverApprovedImport_New {
	
	private List<ApproverWithFlagImport_New> listApproverWithFlagOutput;
	
	private List<String> listApprover;
}
