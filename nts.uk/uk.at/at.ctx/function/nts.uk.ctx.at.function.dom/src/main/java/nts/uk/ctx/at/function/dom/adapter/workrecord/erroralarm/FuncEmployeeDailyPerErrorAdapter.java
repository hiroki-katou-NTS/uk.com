package nts.uk.ctx.at.function.dom.adapter.workrecord.erroralarm;

import java.util.List;

import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface FuncEmployeeDailyPerErrorAdapter {
	List<FuncEmployeeDailyPerErrorImport> getByErrorAlarm(String employeeId, DatePeriod datePeriod, List<String> errorCodes);
}
