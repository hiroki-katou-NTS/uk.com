package nts.uk.ctx.at.shared.dom.dailyattdcal.converter;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

public interface DailyRecordShareFinder {
	
	public IntegrationOfDaily find(String employeeId, GeneralDate date);
	
	public List<IntegrationOfDaily> findByListEmployeeId(List<String> employeeId, DatePeriod baseDate);

}
