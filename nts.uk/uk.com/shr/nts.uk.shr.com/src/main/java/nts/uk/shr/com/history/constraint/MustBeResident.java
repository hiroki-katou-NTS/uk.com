package nts.uk.shr.com.history.constraint;

import nts.arc.error.BusinessException;
import nts.gul.util.value.DiscreteValue;
import nts.uk.shr.com.history.History;
import nts.uk.shr.com.history.HistoryItem;
import nts.uk.shr.com.time.calendar.period.GeneralPeriod;

/**
 * MustBeResident
 *
 * @param <S>
 * @param <D>
 */
public class MustBeResident<S extends GeneralPeriod<S, D>, D extends Comparable<D> & DiscreteValue<D>>
		implements HistoryConstraint<S, D>{

	@Override
	public void validateIfCanAdd(History<S, D> history, HistoryItem<S, D> itemToBeAdded) {
	}

	@Override
	public void validateIfCanRemove(History<S, D> history, HistoryItem<S, D> itemToBeRemoved) {
		
		if (history.items().isEmpty()) {
			throw new BusinessException("");
		}
	}

	@Override
	public void validateIfCanChangeSpan(History<S, D> history, HistoryItem<S, D> itemToBeChanged, S newSpan) {
	}

}
