package nts.uk.ctx.at.request.dom.application.common.detailedscreenprebootmode;

import lombok.Value;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalATR;

@Value
public class CanBeApprovedOutput {
	
	Boolean authorizableFlags;
	
	ApprovalATR approvalATR;
	
	Boolean alternateExpiration;
	
}