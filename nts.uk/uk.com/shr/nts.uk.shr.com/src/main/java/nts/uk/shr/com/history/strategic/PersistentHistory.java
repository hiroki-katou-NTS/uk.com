package nts.uk.shr.com.history.strategic;

import nts.gul.util.value.DiscreteValue;
import nts.uk.shr.com.history.HistoryItem;
import nts.uk.shr.com.time.calendar.period.GeneralPeriod;

/**
 * PersistentHistory
 *
 * @param <S> self
 * @param <D> endpoint
 */
public interface PersistentHistory<S extends GeneralPeriod<S, D>, D extends Comparable<D> & DiscreteValue<D>>
		extends ContinuousHistory<S, D> {

	@Override
	default void exValidateIfCanAdd(HistoryItem<S, D> itemToBeAdded) {
		
		// this should be restricted by UI
		if (!itemToBeAdded.span().isEndMax()) {
			throw new RuntimeException("end of item to be added must be max.");
		}
	}
	
	@Override
	default void exCorrectToRemove(HistoryItem<S, D> itemToBeRemoved) {
		
		this.latestStartItem().ifPresent(latest -> {
			latest.changeSpan(latest.span().newSpanWithMaxEnd());
		});
	}
	
	@Override
	default void exValidateIfCanChangeSpan(HistoryItem<S, D> itemToBeChanged, S newSpan) {
		
		// this should be restricted by UI
		if (!itemToBeChanged.end().equals(newSpan.end())) {
			throw new RuntimeException("end can not be changed.");
		}
	}
}
