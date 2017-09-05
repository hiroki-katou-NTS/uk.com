package nts.uk.ctx.at.request.dom.application.common.valueobject;

import java.util.List;

import lombok.Data;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApproverImport;

@Data
public class ApproverResult {
	
	private List<ApproverWhoApproved> approverWhoApproveds;
	
	private List<ApproverImport> approvers;
	
}
