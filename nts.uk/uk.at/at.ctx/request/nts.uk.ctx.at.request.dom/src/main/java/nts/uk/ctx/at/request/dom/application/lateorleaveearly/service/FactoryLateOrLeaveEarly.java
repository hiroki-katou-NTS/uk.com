package nts.uk.ctx.at.request.dom.application.lateorleaveearly.service;

import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;

public interface FactoryLateOrLeaveEarly {

	LateOrLeaveEarly buildLateOrLeaveEarly(Application_New application,int actualCancel, int early1, Integer earlyTime1, int late1, Integer lateTime1, int early2, Integer earlyTime2,
			int late2, Integer lateTime2);
}