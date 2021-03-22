package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyoneday;

import java.util.List;

import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.dailyattendancework.IntegrationOfDaily;

public interface IntegrationOfDailyGetter {

	List<IntegrationOfDaily> getIntegrationOfDaily(String employeeId, DatePeriod datePeriod);
}
