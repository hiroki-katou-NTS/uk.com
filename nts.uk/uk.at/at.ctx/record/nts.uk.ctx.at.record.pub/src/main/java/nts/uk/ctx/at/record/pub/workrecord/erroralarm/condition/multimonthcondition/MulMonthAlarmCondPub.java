package nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.multimonthcondition;

import java.util.List;

public interface MulMonthAlarmCondPub {
	List<MulMonthAlarmCondPubEx> getListMulMonAlarmByListEralID(List<String> listEralCheckID);
}
