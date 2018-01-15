package nts.uk.ctx.at.record.dom.workinformation.repository;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workinformation.WorkInfoOfDailyPerformance;
import nts.uk.ctx.at.record.dom.workinformation.WorkInformation;

public interface WorkInformationRepository {
	
	/** Find an Employee WorkInformation of Daily Performance */
	Optional<WorkInfoOfDailyPerformance> find(String employeeId, GeneralDate ymd);
	
	List<WorkInfoOfDailyPerformance> findByListEmployeeId(List<String> employeeIds, List<GeneralDate> ymds);
	
	void delete(String employeeId, GeneralDate ymd);
	
	void deleteByListEmployeeId(List<String> employeeIds, List<GeneralDate> ymds);
	
	void updateByKey(WorkInfoOfDailyPerformance workInfoOfDailyPerformance);
	
	void insert(WorkInfoOfDailyPerformance workInfoOfDailyPerformance);
}