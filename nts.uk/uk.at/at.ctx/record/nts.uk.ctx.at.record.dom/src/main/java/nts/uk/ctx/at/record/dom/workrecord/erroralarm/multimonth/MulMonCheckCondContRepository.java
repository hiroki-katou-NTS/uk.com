package nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth;

import java.util.Optional;

public interface MulMonCheckCondContRepository {

	Optional<MulMonthCheckCondContinue> getMulMonthCheckCondContById(String errorAlarmCheckID);
	
	void addMulMonthCheckCondCont(MulMonthCheckCondContinue mulMonthCheckCondContinue);
	
	void updateMulMonthCheckCondCont(MulMonthCheckCondContinue mulMonthCheckCondContinue);
	
	void deleteMulMonthCheckCondCont(String errorAlarmCheckID);
	
}
