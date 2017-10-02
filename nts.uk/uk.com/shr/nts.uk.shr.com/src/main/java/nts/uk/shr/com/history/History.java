package nts.uk.shr.com.history;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.gul.util.range.ComparableRange;

import static java.util.Comparator.*;

public interface History<S extends ComparableRange<S, D>, D extends Comparable<D>> {
	

	List<HistoryItem<S, D>> items();
	
	default List<HistoryItem<S, D>> itemsStartAscending() {
		return this.items().stream()
				.sorted(comparing(item -> item.span().start()))
				.collect(Collectors.toList());
	}
	
	default List<HistoryItem<S, D>> itemsStartDescending() {
		return this.items().stream()
				.sorted(comparing(item -> item.span().start(), reverseOrder()))
				.collect(Collectors.toList());
	}
	
	default Optional<HistoryItem<S, D>> latestStartItem() {
		return this.itemsStartDescending().stream().findFirst();
	}
	
	default Optional<HistoryItem<S, D>> immediatelyBefore(HistoryItem<S, D> baseItem) {
		HistoryItem<S, D> immediatelyBefore = null;
		for (val currentItem : this.itemsStartAscending()) {
			if (currentItem.equals(baseItem)) {
				return Optional.ofNullable(immediatelyBefore);
			}
			
			immediatelyBefore = currentItem;
		}
		
		return Optional.empty();
	}
	
	default Optional<HistoryItem<S, D>> immediatelyAfter(HistoryItem<S, D> baseItem) {
		HistoryItem<S, D> immediatelyAfter = null;
		for (val currentItem : this.itemsStartDescending()) {
			if (currentItem.equals(baseItem)) {
				return Optional.ofNullable(immediatelyAfter);
			}
			
			immediatelyAfter = currentItem;
		}
		
		return Optional.empty();
	}
}
