package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.approvalstatus.ApprovalStatusMailTemp;

/**
 * refactor 5
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class ApprSttSendMailInfoOutput {
	private ApprovalStatusMailTemp approvalStatusMailTemp;
	
	private List<ApprSttExecutionOutput> apprSttExecutionOutputLst;
}
