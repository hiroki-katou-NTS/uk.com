package nts.uk.ctx.at.function.dom.adapter.monthlyremain;

import java.util.Optional;


public interface MonthlyCheckRemainNumberMonAdapter {
	
	Optional<CheckRemainNumberMonImport> getByEralCheckID(String errorAlarmID);
}
