package nts.uk.ctx.at.record.dom.workrecord.erroralarm.multimonth;

import java.util.Optional;

public interface MulMonCheckCondCospRepository {

	Optional<MulMonthCheckCondCosp> getMulMonthCheckCondCospById(String errorAlarmCheckID);
	
	void addMulMonthCheckCondCosp(MulMonthCheckCondCosp mulMonthCheckCondCosp);
	
	void updateMulMonthCheckCondCosp(MulMonthCheckCondCosp mulMonthCheckCondCosp);
	
	void deleteMulMonthCheckCondCosp(String errorAlarmCheckID);
	
}
