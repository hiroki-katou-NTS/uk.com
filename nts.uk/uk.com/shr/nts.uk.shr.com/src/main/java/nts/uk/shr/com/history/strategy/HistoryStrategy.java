package nts.uk.shr.com.history.strategy;

import nts.gul.util.range.ComparableRange;
import nts.uk.shr.com.history.History;
import nts.uk.shr.com.history.HistoryItem;

public interface HistoryStrategy<S extends ComparableRange<S, D>, D extends Comparable<D>> extends History<S, D> {

	void add(HistoryItem<S, D> itemToBeAdded);
	
	void updateSpan(HistoryItem<S, D> itemToBeUpdated, S newSpan);
	
	void remove(HistoryItem<S, D> itemToBeRemoved);
}
