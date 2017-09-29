package nts.uk.ctx.at.request.dom.application.lateorleaveearly.service;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class FactoryLateOrLeaveEarlyDefault implements FactoryLateOrLeaveEarly {

	@Override
	public LateOrLeaveEarly buildLateOrLeaveEarly(GeneralDate applicationDate, String applicationReason,
			//int actualCancelAtr,
			int early1, int earlyTime1, int late1, int lateTime1, int early2, int earlyTime2,
			int late2, int lateTime2) {
		String appID = IdentifierUtil.randomUniqueId();
		return new LateOrLeaveEarly(AppContexts.user().companyId(), appID, 0, GeneralDate.today(),
				AppContexts.user().personId(), "", applicationDate, applicationReason, 9,
				AppContexts.user().employeeId(), 0, null, 0, 0, 0, null, 0, 0,null,null,null,
				// actualCancelAtr,
				early1,
				earlyTime1, late1, lateTime1, early2, earlyTime2, late2, lateTime2);
	}

	
}
