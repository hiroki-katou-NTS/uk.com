package nts.uk.ctx.at.record.dom.workinformation;

import java.util.Optional;

public interface WorkInformationRepository {
	
	/** Find an Employee WorkInformation of Daily Performance */
	Optional<WorkInfoOfDailyPerformance> find(String employeeId);
}