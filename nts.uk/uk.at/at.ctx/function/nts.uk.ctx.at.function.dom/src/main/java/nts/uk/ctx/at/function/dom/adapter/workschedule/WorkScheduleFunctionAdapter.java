package nts.uk.ctx.at.function.dom.adapter.workschedule;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;

public interface WorkScheduleFunctionAdapter {
	List<WorkScheduleBasicInforFunctionImport> getScheBySids(List<String> lstSid, DatePeriod dPeriod);
}
