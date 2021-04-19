package nts.uk.ctx.at.request.dom.application.common.service.application;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApplicationForSendOutput;

public interface IApplicationForSendService {
	ApplicationForSendOutput getApplicationForSend(List<String> appIDLst);
}
