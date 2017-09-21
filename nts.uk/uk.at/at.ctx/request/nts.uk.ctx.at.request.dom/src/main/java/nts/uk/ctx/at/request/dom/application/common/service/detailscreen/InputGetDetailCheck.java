package nts.uk.ctx.at.request.dom.application.common.service.detailscreen;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.Application;

@Value
public class InputGetDetailCheck {
	Application application;
	GeneralDate baseDate;
	
}
