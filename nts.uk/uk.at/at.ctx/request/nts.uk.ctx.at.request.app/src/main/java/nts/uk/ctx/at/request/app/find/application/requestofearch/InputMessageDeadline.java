package nts.uk.ctx.at.request.app.find.application.requestofearch;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class InputMessageDeadline {
	String companyID;
	String workplaceID;
	int appType;
	GeneralDate appDate;
}
