package nts.uk.ctx.at.record.pub.workrecord.erroralarm.condition.monthlycheckcondition;

import java.util.List;

public interface FixedExtraMonPub {
	List<FixedExtraMonPubEx> getByEralCheckID(String monAlarmCheckID);
	
	void addFixedExtraMon(FixedExtraMonPubEx fixedExtraMon);
	
	void updateFixedExtraMon(FixedExtraMonPubEx fixedExtraMon);
	
	void deleteFixedExtraMon(String monAlarmCheckID);
}
