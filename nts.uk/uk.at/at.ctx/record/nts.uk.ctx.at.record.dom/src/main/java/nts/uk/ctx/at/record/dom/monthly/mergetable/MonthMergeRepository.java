package nts.uk.ctx.at.record.dom.monthly.mergetable;

public interface MonthMergeRepository {
	ObjectMergeAll find(MonthMergeKey key);
	void insert(ObjectMergeAll objMergeAll);
	void update(ObjectMergeAll objMergeAll);
	void delete(MonthMergeKey key);
}
