package nts.uk.ctx.at.request.app.find.application.common.dto.approvesendmail;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ApproverStateSendDto {
	private String approverID;
	
	private String approverName;
	
	private String representerID;
	
	private String representerName;
	
	private String approverMail;
	
	private String agentMail;
}
