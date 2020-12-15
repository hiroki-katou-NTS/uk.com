package nts.uk.ctx.at.schedule.pub.schedule.workschedule;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
/**
 * 
 * @author tutk
 *
 */
public interface WorkSchedulePub {
	public Optional<WorkScheduleExport> get(String employeeID , GeneralDate ymd);
	
	public List<WorkScheduleBasicInforExport> get(List<String> lstSid , DatePeriod ymdPeriod);
}
