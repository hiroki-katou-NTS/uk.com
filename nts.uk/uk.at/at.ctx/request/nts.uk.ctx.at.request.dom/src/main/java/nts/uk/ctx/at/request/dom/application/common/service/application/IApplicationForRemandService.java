package nts.uk.ctx.at.request.dom.application.common.service.application;

import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApplicationForRemandOutput;

public interface IApplicationForRemandService {
	ApplicationForRemandOutput getApplicationForRemand(String appID);
}
