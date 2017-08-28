package nts.uk.ctx.at.request.dom.application.common.detailedscreenprebootmode;

import lombok.Value;
import nts.uk.ctx.at.request.dom.application.common.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.approvalframe.ApprovalATR;

@Value
public class DetailedScreenPreBootModeOutput {
	
	String approverSID;
	
	ReflectPlanPerState reflectPlanState;
	
	Boolean authorizableFlags;
	
	ApprovalATR approvalATR;
	
	Boolean alternateExpiration;
	
}