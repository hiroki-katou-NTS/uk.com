package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.output.ProcessDeleteResult;


public interface AfterProcessDelete {

	public ProcessDeleteResult screenAfterDelete (String companyID,String appID, Long version );

}
