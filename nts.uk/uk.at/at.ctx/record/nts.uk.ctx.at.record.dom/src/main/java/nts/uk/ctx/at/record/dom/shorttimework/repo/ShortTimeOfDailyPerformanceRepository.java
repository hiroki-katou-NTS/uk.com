package nts.uk.ctx.at.record.dom.shorttimework.repo;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.shorttimework.ShortTimeOfDailyPerformance;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface ShortTimeOfDailyPerformanceRepository {
	
	Optional<ShortTimeOfDailyPerformance> find(String employeeId, GeneralDate ymd);
	
	List<ShortTimeOfDailyPerformance> finds(List<String> employeeId, DatePeriod ymd);
	
	List<ShortTimeOfDailyPerformance> finds(Map<String, GeneralDate> param);
	
	void updateByKey(ShortTimeOfDailyPerformance shortWork);
	
	void insert(ShortTimeOfDailyPerformance shortWork);
	
	void deleteByEmployeeIdAndDate(String employeeId, GeneralDate ymd);
}
