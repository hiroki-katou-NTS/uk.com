package nts.uk.ctx.at.record.dom.affiliationinformation.repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.affiliationinformation.WorkTypeOfDailyPerformance;

public interface WorkTypeOfDailyPerforRepository {
	
	void delete(String employeeId, GeneralDate processingDate);
	
	void add(WorkTypeOfDailyPerformance workTypeOfDailyPerformance);
	
	void update(WorkTypeOfDailyPerformance workTypeOfDailyPerformance);
	
	Optional<WorkTypeOfDailyPerformance> findByKey(String employeeId, GeneralDate processingDate);
	
	List<WorkTypeOfDailyPerformance> finds(List<String> employeeId, DatePeriod baseDate);
	
	List<WorkTypeOfDailyPerformance> finds(Map<String, List<GeneralDate>> param);

}
