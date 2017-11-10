package nts.uk.ctx.at.record.dom.worktime.repository;

import nts.arc.time.GeneralDate;

public interface TemporaryTimeOfDailyPerformanceRepository {

	void delete(String employeeId, GeneralDate ymd);
}
