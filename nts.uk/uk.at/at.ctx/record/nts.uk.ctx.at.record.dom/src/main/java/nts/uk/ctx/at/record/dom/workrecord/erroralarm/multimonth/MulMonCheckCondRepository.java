package nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth;

import java.util.Optional;

public interface MulMonCheckCondRepository {

	Optional<MulMonthCheckCond> getMulMonthCheckCondById(String errorAlarmCheckID);
	
	void addMulMonthCheckCond(MulMonthCheckCond mulMonthCheckCond);
	
	void updateMulMonthCheckCond(MulMonthCheckCond mulMonthCheckCond);
	
	void deleteMulMonthCheckCond(String errorAlarmCheckID);
	
}
