package nts.uk.ctx.at.record.dom.editstate.repository;

import nts.arc.time.GeneralDate;

public interface EditStateOfDailyPerformanceRepository {

	void delete(String employeeId, GeneralDate ymd);
}
