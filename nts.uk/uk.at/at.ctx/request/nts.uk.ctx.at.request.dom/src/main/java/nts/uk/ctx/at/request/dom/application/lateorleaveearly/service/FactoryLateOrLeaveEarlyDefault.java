package nts.uk.ctx.at.request.dom.application.lateorleaveearly.service;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.request.dom.application.common.appapprovalphase.AppApprovalPhase;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class FactoryLateOrLeaveEarlyDefault implements FactoryLateOrLeaveEarly {

	@Override
	public LateOrLeaveEarly buildLateOrLeaveEarly(String appID, GeneralDate applicationDate, int prePostAtr, String applicationReason,
			List<AppApprovalPhase> listAppApprovalPhase,
			int early1, int earlyTime1, int late1, int lateTime1, int early2, int earlyTime2, int late2,
			int lateTime2) {
		return new LateOrLeaveEarly(AppContexts.user().companyId(), appID, prePostAtr, GeneralDateTime.now(),
				AppContexts.user().personId(), "", applicationDate, applicationReason, 9,
				AppContexts.user().employeeId(), 0, null, 0, 0, 0, null, 0, 0, null, null,
				listAppApprovalPhase,
				early1, earlyTime1, late1, lateTime1, early2, earlyTime2, late2, lateTime2);
	}

}
