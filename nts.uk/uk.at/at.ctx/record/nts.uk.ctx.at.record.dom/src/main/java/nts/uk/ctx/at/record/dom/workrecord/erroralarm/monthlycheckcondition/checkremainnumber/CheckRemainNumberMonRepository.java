package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.checkremainnumber;

import java.util.Optional;

public interface CheckRemainNumberMonRepository {
	Optional<CheckRemainNumberMon> getByEralCheckID(String errorAlarmID);
	
	void addCheckRemainNumberMon(CheckRemainNumberMon checkRemainNumberMon);
	
	void updateCheckRemainNumberMon(CheckRemainNumberMon checkRemainNumberMon);
	
	void deleteCheckRemainNumberMon(String errorAlarmID);
}
