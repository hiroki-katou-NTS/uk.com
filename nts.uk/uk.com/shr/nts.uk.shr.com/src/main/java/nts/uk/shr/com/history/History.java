package nts.uk.shr.com.history;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.gul.util.value.DiscreteValue;
import nts.uk.shr.com.history.constraint.HistoryConstraint;
import nts.uk.shr.com.time.calendar.period.GeneralPeriod;

import static java.util.Comparator.*;

import java.util.Collections;

public interface History<S extends GeneralPeriod<S, D>, D extends Comparable<D> & DiscreteValue<D>> {
	
	List<HistoryItem<S, D>> items();
	
	default List<HistoryConstraint<S, D>> constraints() {
		return Collections.emptyList();
	}

	default void add(HistoryItem<S, D> itemToBeAdded) {
		this.constraints().forEach(c -> c.validateIfCanAdd(this, itemToBeAdded));
		this.items().add(itemToBeAdded);
	}
	
	default void remove(HistoryItem<S, D> itemToBeRemoved) {
		this.constraints().forEach(c -> c.validateIfCanRemove(this, itemToBeRemoved));
		this.items().remove(itemToBeRemoved);
	}
	
	default void changeSpan(HistoryItem<S, D> itemToBeChanged, S newSpan) {
		this.constraints().forEach(c -> c.validateIfCanChangeSpan(this, itemToBeChanged, newSpan));
		itemToBeChanged.changeSpan(newSpan);
	}
	
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
