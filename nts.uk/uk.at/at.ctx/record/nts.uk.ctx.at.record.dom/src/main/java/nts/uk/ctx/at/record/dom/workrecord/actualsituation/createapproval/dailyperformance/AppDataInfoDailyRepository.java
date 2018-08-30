package nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance;

import java.util.List;
import java.util.Optional;

public interface AppDataInfoDailyRepository {
	
	List<AppDataInfoDaily> getAppDataInfoDailyByExeID(String executionId);
	
	Optional<AppDataInfoDaily> getAppDataInfoDailyByID(String employeeId,String executionId);
	
	void addAppDataInfoDaily(AppDataInfoDaily appDataInfoDaily);
	
	void updateAppDataInfoDaily(AppDataInfoDaily appDataInfoDaily);
	
	void deleteAppDataInfoDaily(String employeeId,String executionId);
	
}
