package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;

import java.util.List;

public interface FixedExtraMonRepository {

	List<FixedExtraMon> getByEralCheckID(String monAlarmCheckID);
	
	void addFixedExtraMon(FixedExtraMon fixedExtraMon);
	
	void updateFixedExtraMon(FixedExtraMon fixedExtraMon);
	
	void deleteFixedExtraMon(String monAlarmCheckID);
	
}
