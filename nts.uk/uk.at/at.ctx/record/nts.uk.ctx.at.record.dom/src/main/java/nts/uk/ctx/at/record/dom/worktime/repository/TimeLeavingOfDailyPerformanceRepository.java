package nts.uk.ctx.at.record.dom.worktime.repository;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface TimeLeavingOfDailyPerformanceRepository {
	
	void delete(String employeeId, GeneralDate ymd);
	
	void deleteByListEmployeeId(List<String> employeeIds, List<GeneralDate> ymds);
	
	List<TimeLeavingOfDailyPerformance> findByListEmployeeId(List<String> employeeIds, List<GeneralDate> ymds);
	
	Optional<TimeLeavingOfDailyPerformance> findByKey(String employeeId, GeneralDate ymd);

	List<TimeLeavingOfDailyPerformance> finds(List<String> employeeIds, DatePeriod ymd);

	void add(TimeLeavingOfDailyPerformance timeLeaving);
	
	void update(TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance);

	void insert(TimeLeavingOfDailyPerformance timeLeavingOfDailyPerformance);


}
