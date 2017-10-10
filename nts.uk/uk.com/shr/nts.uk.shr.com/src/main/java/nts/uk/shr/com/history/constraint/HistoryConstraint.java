package nts.uk.shr.com.history.constraint;

import nts.gul.util.value.DiscreteValue;
import nts.uk.shr.com.history.History;
import nts.uk.shr.com.history.HistoryItem;
import nts.uk.shr.com.time.calendar.period.GeneralPeriod;

public interface HistoryConstraint<S extends GeneralPeriod<S, D>, D extends Comparable<D> & DiscreteValue<D>> {

	void validateIfCanAdd(History<S, D> history, HistoryItem<S, D> itemToBeAdded);
	void validateIfCanRemove(History<S, D> history, HistoryItem<S, D> itemToBeRemoved);
	void validateIfCanChangeSpan(History<S, D> history, HistoryItem<S, D> itemToBeChanged, S newSpan);
	
}
