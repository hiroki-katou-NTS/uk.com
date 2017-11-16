package nts.uk.ctx.at.record.dom.editstate.repository;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface EditStateOfDailyPerformanceRepository {

	void delete(String employeeId, GeneralDate ymd);
	
	void deleteByListEmployeeId(List<String> employeeIds, List<GeneralDate> processingYmds);
}
