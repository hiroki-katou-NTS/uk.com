package nts.uk.ctx.at.record.dom.workinformation.repository;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;

public interface WorkInformationRepository {
	
	/** Find an Employee WorkInformation of Daily Performance */
	Optional<WorkInfoOfDailyPerformance> find(String employeeId, GeneralDate ymd);
	
	void delete(String employeeId, GeneralDate ymd);
	
	void updateByKey(WorkInfoOfDailyPerformance workInfoOfDailyPerformance);
	
	void insert(WorkInfoOfDailyPerformance workInfoOfDailyPerformance);
}