package nts.uk.ctx.at.request.dom.application.common.service.application;

import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApplicationForSendOutput;

public interface IApplicationForSendService {
	ApplicationForSendOutput getApplicationForSend(String appID);
}
