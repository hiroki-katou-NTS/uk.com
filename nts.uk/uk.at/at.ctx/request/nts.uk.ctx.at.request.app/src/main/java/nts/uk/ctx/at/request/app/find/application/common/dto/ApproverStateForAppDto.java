package nts.uk.ctx.at.request.app.find.application.common.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class ApproverStateForAppDto {
	private String approverID;
	
	private Integer approvalAtrValue;
	
	private String approvalAtrName;
	
	private String agentID;
	
	private String approverName;
	
	private String representerID;
	
	private String representerName;
	
	private String approvalDate;
	
	private String approvalReason;
	
	private String approverMail;
	
	private String representerMail;
}
