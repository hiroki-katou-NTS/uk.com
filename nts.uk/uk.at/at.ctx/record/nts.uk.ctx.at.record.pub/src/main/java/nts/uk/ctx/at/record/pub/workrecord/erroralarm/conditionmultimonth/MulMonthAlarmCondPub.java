package nts.uk.ctx.at.record.pub.workrecord.erroralarm.conditionmultimonth;

import java.util.List;

public interface MulMonthAlarmCondPub {
	List<MulMonthAlarmCondPubEx> getListMulMonAlarmByListEralID(List<String> listEralCheckID);
}
