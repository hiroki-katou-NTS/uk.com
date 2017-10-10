package nts.uk.shr.com.history.strategic;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.gul.util.range.ComparableRange;
import nts.uk.shr.com.history.History;
import nts.uk.shr.com.history.HistoryItem;

public interface ContinuousHistory<S extends ComparableRange<S, D>, D extends Comparable<D>>
		extends History<S, D> {

	@Override
	default void add(HistoryItem<S, D> itemToBeAdded) {
		
		this.constraints().forEach(c -> c.validateIfCanAdd(this, itemToBeAdded));

		this.latestStartItem().ifPresent(latest -> {
			if (itemToBeAdded.span().startIsBefore(latest.span())) {
				throw new BusinessException("Msg_102");
			}
			latest.shortenEndToAccept(itemToBeAdded);
		});
		
		this.items().add(itemToBeAdded);
	}

	@Override
	default void remove(HistoryItem<S, D> itemToBeRemoved) {

		this.constraints().forEach(c -> c.validateIfCanRemove(this, itemToBeRemoved));
		
		
		
		this.items().remove(itemToBeRemoved);
	}

	@Override
	default void changeSpan(HistoryItem<S, D> itemToBeChanged, S newSpan) {

		this.constraints().forEach(c -> c.validateIfCanChangeSpan(this, itemToBeChanged, newSpan));
		
		this.immediatelyBefore(itemToBeChanged).ifPresent(immediatelyBefore -> {

			if (newSpan.startIsBefore(immediatelyBefore.span())) {
				throw new BusinessException("Msg_127");
			}
			
			immediatelyBefore.shortenEndToAccept(newSpan);
		});

		itemToBeChanged.changeSpan(newSpan);
	}


}
