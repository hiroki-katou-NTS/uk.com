package nts.uk.ctx.at.record.dom.workrecord.actualsituation.createapproval.monthlyperformance;

import java.util.Optional;

public interface AppInterrupMonRepository {
	
	Optional<AppInterrupMon> getAppInterrupMonByID(String executionId);
	
	void addAppInterrupMon(AppInterrupMon appInterrupMon);
	
	void updateAppInterrupMon(AppInterrupMon appInterrupMon);
	
	void deleteAppInterrupMon(String executionId);
}
