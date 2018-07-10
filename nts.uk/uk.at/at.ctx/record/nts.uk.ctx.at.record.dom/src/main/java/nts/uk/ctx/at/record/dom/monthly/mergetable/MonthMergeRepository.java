package nts.uk.ctx.at.record.dom.monthly.mergetable;

import java.util.List;

public interface MonthMergeRepository {
	List<ObjectMergeAll> find(MonthMergeKey key);
	void insert(ObjectMergeAll objMergeAll);
	void update(ObjectMergeAll objMergeAll);
	void delete(MonthMergeKey key);
}
