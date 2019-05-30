package nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.dailyperformance;

import java.util.Optional;

public interface AppInterrupDailyRepository {
	
	Optional<AppInterrupDaily> getAppInterrupDailyByID(String executionId);
	
	void addAppInterrupDaily(AppInterrupDaily appInterrupDaily);
	
	void updateAppInterrupDaily(AppInterrupDaily appInterrupDaily);
	
	void deleteAppInterrupDaily(String executionId);
}
