package nts.uk.ctx.at.request.dom.application.lateleaveearly;

import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.ArrivedLateLeaveEarlyInfoOutput;

/**
 * @author anhnm
 *
 */
public interface LateLeaveEarlyRepository {

	void registerLateLeaveEarly(String cID, Application application, ArrivedLateLeaveEarlyInfoOutput infoOutput);
}
