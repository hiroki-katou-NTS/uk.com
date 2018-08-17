package nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth;

import java.util.List;
import java.util.Optional;

public interface MulMonAlarmCheckCondRepository {
	List<MulMonthAlarmCheckCond> getMulMonAlarmsByListID(List<String> listErrorAlarmCheckID);
	
	Optional<MulMonthAlarmCheckCond> getMulMonAlarmByID(String errorAlarmCheckID);
	
	void addMulMonAlarm(MulMonthAlarmCheckCond mulMonthAlarmCheckCond);
	
	void updateMulMonAlarm(MulMonthAlarmCheckCond mulMonthAlarmCheckCond);
	
	void deleteMulMonAlarm(String errorAlarmCheckID);
}

