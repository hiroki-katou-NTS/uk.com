package nts.uk.ctx.at.request.dom.application.applist.service;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;

@Value
public class CheckExitSync {

	private boolean checkExit;
	private GeneralDate appDateSub;
	private GeneralDateTime inputDateSub;
}
