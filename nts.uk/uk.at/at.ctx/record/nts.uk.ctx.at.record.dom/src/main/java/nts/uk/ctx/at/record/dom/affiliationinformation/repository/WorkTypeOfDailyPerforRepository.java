package nts.uk.ctx.at.record.dom.affiliationinformation.repository;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.affiliationinformation.WorkTypeOfDailyPerformance;

public interface WorkTypeOfDailyPerforRepository {
	
	void delete(String employeeId, GeneralDate processingDate);
	
	void add(WorkTypeOfDailyPerformance workTypeOfDailyPerformance);
	
	void update(WorkTypeOfDailyPerformance workTypeOfDailyPerformance);
	
	Optional<WorkTypeOfDailyPerformance> findByKey(String employeeId, GeneralDate processingDate);

}
