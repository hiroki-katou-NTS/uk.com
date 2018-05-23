package nts.uk.ctx.at.record.dom.affiliationinformation.repository;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.affiliationinformation.WorkTypeOfDailyPerformance;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

public interface WorkTypeOfDailyPerforRepository {
	
	void delete(String employeeId, GeneralDate processingDate);
	
	void add(WorkTypeOfDailyPerformance workTypeOfDailyPerformance);
	
	void update(WorkTypeOfDailyPerformance workTypeOfDailyPerformance);
	
	Optional<WorkTypeOfDailyPerformance> findByKey(String employeeId, GeneralDate processingDate);
	
	List<WorkTypeOfDailyPerformance> finds(List<String> employeeId, DatePeriod baseDate);

}
