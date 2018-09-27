package nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.monthlyperformance;

import java.util.List;
import java.util.Optional;

public interface AppDataInfoMonthlyRepository {
	
	List<AppDataInfoMonthly> getAppDataInfoMonthlyByExeID(String executionId);
	
	Optional<AppDataInfoMonthly> getAppDataInfoMonthlyByID(String employeeId,String executionId);
	
	void addAppDataInfoMonthly(AppDataInfoMonthly appDataInfoMonthly);
	
	void updateAppDataInfoMonthly(AppDataInfoMonthly appDataInfoMonthly);
	
	void deleteAppDataInfoMonthly(String employeeId,String executionId);
	
}
