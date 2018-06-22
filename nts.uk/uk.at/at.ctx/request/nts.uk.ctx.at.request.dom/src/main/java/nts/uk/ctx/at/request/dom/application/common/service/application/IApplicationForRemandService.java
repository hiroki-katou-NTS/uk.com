package nts.uk.ctx.at.request.dom.application.common.service.application;

import java.util.List;

import nts.uk.ctx.at.request.dom.application.common.service.application.output.ApplicationForRemandOutput;

public interface IApplicationForRemandService {
	/**
	 * get Application For Remand
	 * @param lstAppID
	 * @return
	 */
	ApplicationForRemandOutput getApplicationForRemand(List<String> lstAppID);
}
