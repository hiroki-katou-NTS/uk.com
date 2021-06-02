package nts.uk.ctx.at.record.dom.workrecord.erroralarm.monthlycheckcondition;

import java.util.List;
import java.util.Optional;

public interface FixedExtraMonRepository {

	List<FixedExtraMon> getByEralCheckID(String monAlarmCheckID);
	
	void addFixedExtraMon(FixedExtraMon fixedExtraMon);
	
	void updateFixedExtraMon(FixedExtraMon fixedExtraMon);
	
	void deleteFixedExtraMon(String monAlarmCheckID);
	
	List<FixedExtraMon> getFixedItem(String anyId, boolean useAtr);
	
	Optional<FixedExtraMon> getForKey(String id, int no);
	
	void persistFixedExtraMon(FixedExtraMon fixedExtraMon);
}
