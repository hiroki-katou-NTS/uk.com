package nts.uk.ctx.at.record.dom.breakorgoout.repository;

import nts.arc.time.GeneralDate;

public interface BreakTimeOfDailyPerformanceRepository {

	void delete(String employeeId, GeneralDate ymd);
}
