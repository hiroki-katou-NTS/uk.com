package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApproverResult {
	
	private List<ApproverWhoApproved> approverWhoApproveds;
	
	private List<String> approvers;
	
}
