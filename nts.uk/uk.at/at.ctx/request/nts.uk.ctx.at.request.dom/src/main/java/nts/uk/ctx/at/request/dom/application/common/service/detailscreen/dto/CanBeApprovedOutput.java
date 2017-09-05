package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.dto;

import lombok.Value;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;

@Value
public class CanBeApprovedOutput {
	
	Boolean authorizableFlags;
	
	ApprovalAtr approvalATR;
	
	Boolean alternateExpiration;
	
}