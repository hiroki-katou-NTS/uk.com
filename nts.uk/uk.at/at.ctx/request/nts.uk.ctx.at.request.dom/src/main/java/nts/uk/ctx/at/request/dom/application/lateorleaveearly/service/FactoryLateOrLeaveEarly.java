package nts.uk.ctx.at.request.dom.application.lateorleaveearly.service;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;

public interface FactoryLateOrLeaveEarly {

	LateOrLeaveEarly buildLateOrLeaveEarly(String appID, GeneralDate applicationDate, int prePostAtr,
			String applicationReason, int early1, int earlyTime1,
			int late1, int lateTime1, int early2, int earlyTime2, int late2, int lateTime2);
}