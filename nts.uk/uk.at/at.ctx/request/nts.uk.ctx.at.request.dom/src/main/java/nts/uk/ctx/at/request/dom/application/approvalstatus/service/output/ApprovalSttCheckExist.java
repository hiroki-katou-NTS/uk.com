package nts.uk.ctx.at.request.dom.application.approvalstatus.service.output;

import lombok.Value;

@Value
public class ApprovalSttCheckExist {
	
	private String workplaceId;
	
	private boolean isChecked;
}
