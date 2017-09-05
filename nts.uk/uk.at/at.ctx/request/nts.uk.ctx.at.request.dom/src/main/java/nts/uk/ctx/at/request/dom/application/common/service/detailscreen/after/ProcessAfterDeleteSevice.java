package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import nts.uk.ctx.at.request.dom.application.common.Application;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.ScreenAfterDelete;

public interface ProcessAfterDeleteSevice {

	public ScreenAfterDelete screenAfterDelete (Application applicationData , String appID);

}
