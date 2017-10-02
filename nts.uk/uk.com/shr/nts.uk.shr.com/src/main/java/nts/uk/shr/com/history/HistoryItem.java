package nts.uk.shr.com.history;

import lombok.val;
import nts.gul.util.range.ComparableRange;

/**
 * Item of history that has an own span.
 *
 * @param <S> Span
 */
public interface HistoryItem<S extends ComparableRange<S, D>, D extends Comparable<D>> {

	/**
	 * Returns the own span.
	 * @return span
	 */
	S span();
	
	/**
	 * Returns string to identify: ID, code, ...
	 * @return identifier
	 */
	String identifier();
	
	/**
	 * Change span of this item.
	 * @param newSpan new span
	 */
	void changeSpan(S newSpan);

	/**
	 * Shorten start of this span, to accept a given span.
	 * @param spanToBeAccepted
	 */
	default void shortenStartToAccept(S spanToBeAccepted) {
		val newSpan = this.span().cutOffWithNewStart(spanToBeAccepted.end());
		this.changeSpan(newSpan);
	}
	
	/**
	 * Shorten start of this span, to accept a given span.
	 * @param spanToBeAccepted
	 */
	default void shortenEndToAccept(S spanToBeAccepted) {
		val newSpan = this.span().cutOffWithNewEnd(spanToBeAccepted.start());
		this.changeSpan(newSpan);
	}

	/**
	 * Shorten start of this span, to accept a given item.
	 * @param itemToBeAccepted item to be accepted
	 */
	default void shortenStartToAccept(HistoryItem<S, D> itemToBeAccepted) {
		this.shortenStartToAccept(itemToBeAccepted.span());
	}
	
	/**
	 * Shorten end of this span, to accept a given item.
	 * @param itemToBeAccepted item to be accepted
	 */
	default void shortenEndToAccept(HistoryItem<S, D> itemToBeAccepted) {
		this.shortenEndToAccept(itemToBeAccepted.span());
	}
	
	/**
	 * Returns true if same identifier.
	 * @param other item to be compared
	 * @return true if same identifier
	 */
	default boolean equals(HistoryItem<S, D> other) {
		return this.identifier().equals(other.identifier());
	}
}
