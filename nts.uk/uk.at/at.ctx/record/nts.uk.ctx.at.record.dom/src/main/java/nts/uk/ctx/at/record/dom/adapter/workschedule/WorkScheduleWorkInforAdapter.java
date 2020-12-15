package nts.uk.ctx.at.record.dom.adapter.workschedule;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
/**
 * 
 * @author tutk
 *
 */
public interface WorkScheduleWorkInforAdapter {
	public Optional<WorkScheduleWorkInforImport> get(String employeeID , GeneralDate ymd);
	
	public List<WorkScheduleBasicInforRecordImport> getList(List<String> sid, DatePeriod dPeriod);
}
