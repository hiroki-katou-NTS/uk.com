package nts.uk.ctx.at.record.dom.breakorgoout.repository;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.breakorgoout.BreakTimeOfDailyPerformance;

public interface BreakTimeOfDailyPerformanceRepository {

	void delete(String employeeId, GeneralDate ymd);
	
	void deleteByListEmployeeId(List<String> employeeIds, List<GeneralDate> ymds);
	
	List<BreakTimeOfDailyPerformance> findByKey(String employeeId, GeneralDate ymd);
}
