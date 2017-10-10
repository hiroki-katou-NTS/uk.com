package nts.uk.shr.com.history.constraint;

import nts.gul.util.range.ComparableRange;
import nts.uk.shr.com.history.History;
import nts.uk.shr.com.history.HistoryItem;

public interface HistoryConstraint<S extends ComparableRange<S, D>, D extends Comparable<D>> {

	void validateIfCanAdd(History<S, D> history, HistoryItem<S, D> itemToBeAdded);
	void validateIfCanRemove(History<S, D> history, HistoryItem<S, D> itemToBeRemoved);
	void validateIfCanChangeSpan(History<S, D> history, HistoryItem<S, D> itemToBeChanged, S newSpan);
	
}
