package nts.uk.ctx.at.record.pub.monthly;

import java.util.Optional;

import nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition.checkremainnumber.CheckRemainNumberMon;

public interface GetCheckRemainNumberMonPub {
	Optional<CheckRemainNumberMon> getByEralCheckID(String errorAlarmID);
}
