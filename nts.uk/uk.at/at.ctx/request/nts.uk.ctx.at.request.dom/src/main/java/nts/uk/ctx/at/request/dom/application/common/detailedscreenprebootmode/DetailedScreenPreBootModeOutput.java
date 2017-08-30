package nts.uk.ctx.at.request.dom.application.common.detailedscreenprebootmode;

import lombok.Value;
import nts.uk.ctx.at.request.dom.application.common.ReflectPlanPerState;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.ApprovalAtr;

@Value
public class DetailedScreenPreBootModeOutput {
	
	String approverSID;
	
	ReflectPlanPerState reflectPlanState;
	
	Boolean authorizableFlags;
	
	ApprovalAtr approvalATR;
	
	Boolean alternateExpiration;
	
}