package nts.uk.ctx.at.record.dom.worktime.repository;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface TemporaryTimeOfDailyPerformanceRepository {

	void delete(String employeeId, GeneralDate ymd);
	
	void deleteByListEmployeeId(List<String> employeeIds, List<GeneralDate> ymds);
}
