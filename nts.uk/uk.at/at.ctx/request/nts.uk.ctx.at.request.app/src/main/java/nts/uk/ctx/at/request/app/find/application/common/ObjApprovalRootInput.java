package nts.uk.ctx.at.request.app.find.application.common;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class ObjApprovalRootInput {
	String cid; 
	String sid; 
	int employmentRootAtr; 
	int appType;
	GeneralDate standardDate;
}
