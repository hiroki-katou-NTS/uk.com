package nts.uk.ctx.at.function.ac.workschedule;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.function.dom.adapter.workschedule.WorkScheduleBasicInforFunctionImport;
import nts.uk.ctx.at.function.dom.adapter.workschedule.WorkScheduleFunctionAdapter;
import nts.uk.ctx.at.schedule.pub.schedule.workschedule.WorkSchedulePub;
@Stateless
public class WorkScheduleFunctionAdapterImpl implements WorkScheduleFunctionAdapter {
	@Inject
	private WorkSchedulePub workSchedulePub;

	@Override
	public List<WorkScheduleBasicInforFunctionImport> getScheBySids(List<String> lstSid, DatePeriod dPeriod) {
		List<WorkScheduleBasicInforFunctionImport> lstSche = workSchedulePub.get(lstSid, dPeriod).stream()
				.map(x -> new WorkScheduleBasicInforFunctionImport(x.getEmployeeID(), x.getYmd(), x.getWorkTypeCd(), x.getWorkTimeCd()))
				.collect(Collectors.toList());
		return lstSche;
	}

	
}
