package nts.uk.ctx.at.request.dom.application.lateorleaveearly.service;

import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;

public interface FactoryLateOrLeaveEarly {

	LateOrLeaveEarly buildLateOrLeaveEarly(Application_New application, int early1, int earlyTime1, int late1, int lateTime1, int early2, int earlyTime2,
			int late2, int lateTime2);
}