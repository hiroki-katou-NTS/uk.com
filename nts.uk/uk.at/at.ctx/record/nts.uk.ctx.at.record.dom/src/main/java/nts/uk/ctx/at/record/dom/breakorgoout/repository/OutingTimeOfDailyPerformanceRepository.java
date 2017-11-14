package nts.uk.ctx.at.record.dom.breakorgoout.repository;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface OutingTimeOfDailyPerformanceRepository {

	void delete(String employeeId, GeneralDate ymd);
	
	void deleteByListEmployeeId(List<String> employeeIds, List<GeneralDate> ymds);
}
