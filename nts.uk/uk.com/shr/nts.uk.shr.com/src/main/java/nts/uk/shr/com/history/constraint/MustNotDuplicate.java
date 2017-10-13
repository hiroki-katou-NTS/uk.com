package nts.uk.shr.com.history.constraint;

import nts.arc.error.BusinessException;
import nts.gul.util.range.RangeDuplication;
import nts.gul.util.value.DiscreteValue;
import nts.uk.shr.com.history.History;
import nts.uk.shr.com.history.HistoryItem;
import nts.uk.shr.com.time.calendar.period.GeneralPeriod;

/**
 * MustNotDuplicate
 *
 * @param <S>
 * @param <D>
 */
public class MustNotDuplicate<S extends GeneralPeriod<S, D>, D extends Comparable<D> & DiscreteValue<D>>
		implements HistoryConstraint<S, D>{

	@Override
	public void validateIfCanAdd(History<S, D> history, HistoryItem<S, D> itemToBeAdded) {
		
		boolean isDuplicated = history.items().stream()
				.anyMatch(e -> isDuplicated(e.span().compare(itemToBeAdded.span())));
		
		if (isDuplicated) {
			throw new BusinessException("");
		}
	}

	@Override
	public void validateIfCanRemove(History<S, D> history, HistoryItem<S, D> itemToBeRemoved) {
	}

	@Override
	public void validateIfCanChangeSpan(History<S, D> history, HistoryItem<S, D> itemToBeChanged, S newSpan) {
		
		boolean isDuplicated = history.items().stream()
				.anyMatch(e -> isDuplicated(e.span().compare(newSpan)));
		
		if (isDuplicated) {
			throw new BusinessException("");
		}
	}


	private static boolean isDuplicated(RangeDuplication duplication) {
		switch (duplication) {
		case ADJACENT_AFTER_BASE:
		case ADJACENT_BEFORE_BASE:
		case BASE_AFTER_COMPARED:
		case BASE_BEFORE_COMPARED:
			return false;
		default:
			return true;
		}
	}
}
