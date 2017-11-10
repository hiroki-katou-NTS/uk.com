package nts.uk.ctx.at.record.dom.workinformation;

import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface WorkInformationRepository {
	
	/** Find an Employee WorkInformation of Daily Performance */
	Optional<WorkInfoOfDailyPerformance> find(String employeeId, GeneralDate ymd);
	
	void delete(String employeeId, GeneralDate ymd);
}