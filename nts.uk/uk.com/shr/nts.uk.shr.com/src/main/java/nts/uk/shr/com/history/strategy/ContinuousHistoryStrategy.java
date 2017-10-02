package nts.uk.shr.com.history.strategy;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.gul.util.range.ComparableRange;
import nts.uk.shr.com.history.History;
import nts.uk.shr.com.history.HistoryItem;

public interface ContinuousHistoryStrategy<S extends ComparableRange<S, D>, D extends Comparable<D>> extends HistoryStrategy<S, D> {

	@Override
	default void add(HistoryItem<S, D> itemToBeAdded) {

		this.latestStartItem().ifPresent(latest -> {
			
			if (itemToBeAdded.span().startIsBefore(latest.span())) {
				throw new BusinessException("Msg_102");
			}
			
			latest.shortenEndToAccept(itemToBeAdded);
		});
		
		this.items().add(itemToBeAdded);
	}

	@Override
	default void updateSpan(HistoryItem<S, D> itemToBeUpdated, S newSpan) {
		
		this.immediatelyBefore(itemToBeUpdated).ifPresent(immediatelyBefore -> {

			if (newSpan.startIsBefore(immediatelyBefore.span())) {
				throw new BusinessException("Msg_127");
			}
			
			immediatelyBefore.shortenEndToAccept(newSpan);
		});
	}

	@Override
	default void remove(HistoryItem<S, D> itemToBeRemoved) {
		// TODO Auto-generated method stub
		
	}


}
