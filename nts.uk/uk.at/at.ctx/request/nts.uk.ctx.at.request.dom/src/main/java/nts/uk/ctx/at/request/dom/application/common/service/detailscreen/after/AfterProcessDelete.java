package nts.uk.ctx.at.request.dom.application.common.service.detailscreen.after;

import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;


public interface AfterProcessDelete {

	public ProcessResult screenAfterDelete (String companyID,String appID, Long version );

}
