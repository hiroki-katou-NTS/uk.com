package nts.uk.ctx.at.request.dom.application.lateorleaveearly.service;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.Application_New;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.Select;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.TimeDay;

@Stateless
public class FactoryLateOrLeaveEarlyDefault implements FactoryLateOrLeaveEarly {

	@Override
	public LateOrLeaveEarly buildLateOrLeaveEarly(Application_New application, int early1, int earlyTime1, int late1, int lateTime1, 
			int early2, int earlyTime2, int late2, int lateTime2) {
		return new LateOrLeaveEarly(application, 0, 
				EnumAdaptor.valueOf(early1, Select.class), new TimeDay(earlyTime1), 
				EnumAdaptor.valueOf(late1, Select.class), new TimeDay(lateTime1),  
				EnumAdaptor.valueOf(early2, Select.class), new TimeDay(earlyTime2), 
				EnumAdaptor.valueOf(late2, Select.class), new TimeDay(lateTime2));
	}
	

}
