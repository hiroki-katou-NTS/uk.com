package nts.uk.ctx.at.record.dom.shorttimework.repo;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.shorttimework.ShortTimeOfDailyPerformance;

public interface ShortTimeOfDailyPerformanceRepository {
	
	Optional<ShortTimeOfDailyPerformance> find(String employeeId, GeneralDate ymd);
	
	List<ShortTimeOfDailyPerformance> findByListEmployeeId(List<String> employeeIds, List<GeneralDate> ymds);
	
	void updateByKey(ShortTimeOfDailyPerformance shortWork);
	
	void insert(ShortTimeOfDailyPerformance shortWork);
}
