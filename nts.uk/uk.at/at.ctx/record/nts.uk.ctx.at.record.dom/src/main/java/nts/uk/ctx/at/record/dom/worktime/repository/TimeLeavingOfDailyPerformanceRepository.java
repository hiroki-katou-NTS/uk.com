package nts.uk.ctx.at.record.dom.worktime.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailyattendance.attendancetime.TimeLeavingOfDailyAttd;

public interface TimeLeavingOfDailyPerformanceRepository {
	
	void delete(String employeeId, GeneralDate ymd);
	
	Optional<TimeLeavingOfDailyPerformance> findByKey(String employeeId, GeneralDate ymd);

	List<TimeLeavingOfDailyPerformance> findbyPeriodOrderByYmd(String employeeId, DatePeriod datePeriod);
	
	List<TimeLeavingOfDailyPerformance> finds(List<String> employeeIds, DatePeriod ymd);
	
	List<TimeLeavingOfDailyPerformance> finds(Map<String, List<GeneralDate>> param);
	
	void add(TimeLeavingOfDailyPerformance timeLeaving);
	
	void update(TimeLeavingOfDailyAttd timeLeavingOfDailyPerformance, String employeeId, GeneralDate day);

	void insert(TimeLeavingOfDailyAttd timeLeavingOfDailyPerformance, String employeeId, GeneralDate day);

	void updateFlush(TimeLeavingOfDailyAttd timeLeavingOfDailyPerformance, String employeeId, GeneralDate day);
}
