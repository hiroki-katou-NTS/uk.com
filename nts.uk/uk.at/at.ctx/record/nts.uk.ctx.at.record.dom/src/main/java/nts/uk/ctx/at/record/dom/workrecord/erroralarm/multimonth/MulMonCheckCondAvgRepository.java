package nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth;

import java.util.Optional;

public interface MulMonCheckCondAvgRepository {

	Optional<MulMonthCheckCondAverage> getMulMonthCheckCondAvgById(String errorAlarmCheckID);
	
	void addMulMonthCheckCondAvg(MulMonthCheckCondAverage mulMonthCheckCondAverage);
	
	void updateMulMonthCheckCondAvg(MulMonthCheckCondAverage mulMonthCheckCondAverage);
	
	void deleteMulMonthCheckCondAvg(String errorAlarmCheckID);
	
}
