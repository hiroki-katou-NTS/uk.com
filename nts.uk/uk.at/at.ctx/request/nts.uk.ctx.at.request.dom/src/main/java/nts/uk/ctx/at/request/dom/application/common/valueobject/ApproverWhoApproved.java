package nts.uk.ctx.at.request.dom.application.common.valueobject;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApproverWhoApproved {
	
	private String approverAdaptorDto;
	
	private boolean agentFlag;
	
}
