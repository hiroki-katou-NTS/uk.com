package nts.uk.shr.com.history.constraint;

import nts.gul.util.range.ComparableRange;
import nts.uk.shr.com.history.History;
import nts.uk.shr.com.history.HistoryItem;

public class MustBeResident<S extends ComparableRange<S, D>, D extends Comparable<D>> implements HistoryConstraint<S, D>{

	@Override
	public void validateIfCanAdd(History<S, D> history, HistoryItem<S, D> itemToBeAdded) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validateIfCanRemove(History<S, D> history, HistoryItem<S, D> itemToBeRemoved) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void validateIfCanChangeSpan(History<S, D> history, HistoryItem<S, D> itemToBeChanged, S newSpan) {
		// TODO Auto-generated method stub
		
	}


}
