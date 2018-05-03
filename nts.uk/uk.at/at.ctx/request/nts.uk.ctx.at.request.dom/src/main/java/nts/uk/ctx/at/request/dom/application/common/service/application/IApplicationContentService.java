package nts.uk.ctx.at.request.dom.application.common.service.application;

import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApplicationForSendOutput;

public interface IApplicationContentService {
	/**
	 * 申請内容
	 * @param app
	 * @return
	 */
	String getApplicationContent(Application_New app); 
}
