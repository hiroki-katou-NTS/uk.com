package nts.uk.ctx.at.record.dom.editstate.repository;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.editstate.EditStateOfDailyPerformance;

public interface EditStateOfDailyPerformanceRepository {

	void delete(String employeeId, GeneralDate ymd);
	
	void deleteByListEmployeeId(List<String> employeeIds, List<GeneralDate> processingYmds);
	
	void add(List<EditStateOfDailyPerformance> editStates);
	
	List<EditStateOfDailyPerformance> findByKey(String employeeId, GeneralDate ymd);
	
	void updateByKey(List<EditStateOfDailyPerformance> editStates);
}
