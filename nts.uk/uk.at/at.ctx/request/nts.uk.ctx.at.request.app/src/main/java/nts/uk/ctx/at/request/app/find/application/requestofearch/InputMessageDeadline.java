package nts.uk.ctx.at.request.app.find.application.requestofearch;

import lombok.Value;
import nts.arc.time.GeneralDate;

@Value
public class InputMessageDeadline {
	String sid;
	int appType;
	String appDate;
}
