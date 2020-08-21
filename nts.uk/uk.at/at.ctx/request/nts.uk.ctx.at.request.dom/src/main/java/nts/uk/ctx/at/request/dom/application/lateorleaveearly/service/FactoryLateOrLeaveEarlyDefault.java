package nts.uk.ctx.at.request.dom.application.lateorleaveearly.service;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.LateOrLeaveEarly;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.Select;
import nts.uk.ctx.at.request.dom.application.lateorleaveearly.TimeDay;

@Stateless
public class FactoryLateOrLeaveEarlyDefault implements FactoryLateOrLeaveEarly {

	@Override
	public LateOrLeaveEarly buildLateOrLeaveEarly(Application application,int actualCancel, int early1, Integer earlyTime1, int late1, Integer lateTime1, 
			int early2, Integer earlyTime2, int late2, Integer lateTime2) {
//		return new LateOrLeaveEarly(application, actualCancel, 
//									EnumAdaptor.valueOf(early1, Select.class),
//									getTimeDay(earlyTime1), 
//									EnumAdaptor.valueOf(late1, Select.class),
//									getTimeDay(lateTime1),  
//									EnumAdaptor.valueOf(early2, Select.class),
//									getTimeDay(earlyTime2), 
//									EnumAdaptor.valueOf(late2, Select.class),
//									getTimeDay(lateTime2));
		return null;
	}

	private TimeDay getTimeDay(Integer timeDay) {
		return timeDay != null ? new TimeDay(timeDay) : null;
	}

}
