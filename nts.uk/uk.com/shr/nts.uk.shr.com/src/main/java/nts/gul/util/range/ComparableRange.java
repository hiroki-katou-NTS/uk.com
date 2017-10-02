package nts.gul.util.range;

public interface ComparableRange<S extends ComparableRange<S, T>, T extends Comparable<T>> {

	/**
	 * Returns start value of range.
	 * @return start
	 */
	T start();
	
	/**
	 * Returns end value of range.
	 * @return end
	 */
	T end();
	
	/**
	 * Returns next value of start.
	 * @param isIncrement true if incremented value needed
	 * @return next value of start
	 */
	T startNext(boolean isIncrement);
	
	/**
	 * Returns next value of end.
	 * @param isIncrement true if incremented value needed
	 * @return next value of end
	 */
	T endNext(boolean isIncrement);
	
	/**
	 * Compare ranges.
	 * @param other other range
	 * @return RangeDuplication state of duplication
	 */
	@SuppressWarnings("unchecked")
	default RangeDuplication compare(S other) {
		
		if (this.sameStart(other) && this.sameEnd(other)) {
			return RangeDuplication.SAME_COMPLETE;
		}
		
		if (this.contains(other)) {
			if (this.sameStart(other)) {
				return RangeDuplication.BASE_CONTAINS_SAME_START;
			} else if (this.sameEnd(other)) {
				return RangeDuplication.BASE_CONTAINS_SAME_END;
			} else {
				return RangeDuplication.BASE_CONTAINS_COMPLETE;
			}
		}
		
		if (other.contains((S)this)) {
			if (this.sameStart(other)) {
				return RangeDuplication.BASE_CONTAINED_SAME_START;
			} else if (this.sameEnd(other)) {
				return RangeDuplication.BASE_CONTAINED_SAME_END;
			} else {
				return RangeDuplication.BASE_CONTAINED_COMPLETE;
			}
		}
		
		if (other.end().equals(this.start())) {
			return RangeDuplication.CONTINUOUS_BEFORE_BASE;
		}
		
		if (other.isAdjacentBefore((S)this)) {
			return RangeDuplication.ADJACENT_BEFORE_BASE;
		}
		
		if (this.end().equals(other.start())) {
			return RangeDuplication.CONTINUOUS_AFTER_BASE;
		}
		
		if (other.isAdjacentAfter((S)this)) {
			return RangeDuplication.ADJACENT_AFTER_BASE;
		}
		
		if (this.isBefore(other)) {
			return RangeDuplication.BASE_BEFORE_COMPARED;
		}
		
		if (this.isAfter(other)) {
			return RangeDuplication.BASE_AFTER_COMPARED;
		}
		
		if (this.contains(other.start())) {
			return RangeDuplication.START_SANDWITCHED_BETWEEN_BASE;
		}
		
		if (this.contains(other.end())) {
			return RangeDuplication.END_SANDWITCHED_BETWEEN_BASE;
		}
		
		throw new RuntimeException("unknown State Start:End " + this.start().toString() + ":" + this.end().toString() + "，Start:End" + other.start().toString() + ":" + other.end().toString());
	}
	
	/**
	 * Returns true if this range contains a given point.
	 * @param point point
	 * @return result
	 */
	default boolean contains(T point) {
		return this.start().compareTo(point) <= 0 && this.end().compareTo(point) >= 0;
	}
	
	/**
	 * Returns true if this range contains a given range.
	 * @param other range to be compared
	 * @return　result
	 */
	default boolean contains(S other) {
		return this.start().compareTo(other.start()) <= 0 && this.end().compareTo(other.end()) >= 0;
	}

	/**
	 * Returns true if start of this range is equal to start of a given range.
	 * @param other range to be compared
	 * @return　result
	 */
	default boolean sameStart(S other) {
		return this.start().equals(other.start());
	}
	
	/**
	 * Returns true if end of this range is equal to end of a given range.
	 * @param other range to be compared
	 * @return result
	 */
	default boolean sameEnd(S other) {
		return this.end().equals(other.end());
	}
	
	
	/**
	 * Returns true if this range is before given range.
	 * @param other range to be compared
	 * @return result
	 */
	default boolean isBefore(S other) {
		return this.end().compareTo(other.start()) < 0;
	}
	
	/**
	 * Returns true if this range is after given range.
	 * @param other range to be compared
	 * @return result
	 */
	default boolean isAfter(S other) {
		return this.start().compareTo(other.end()) > 0;
	}
	
	/**
	 * Returns true if this range is adjacent before given range.
	 * @param other range to be compared
	 * @return result
	 */
	default boolean isAdjacentBefore(S other) {
		return this.endNext(true).equals(other.start());
	}
	
	/**
	 * Returns true if this range is adjacent after given range.
	 * @param other range to be compared
	 * @return result
	 */
	default boolean isAdjacentAfter(S other) {
		return other.endNext(true).equals(this.start());
	}
	
	/**
	 * Returns true if start of this rannge is before given range.
	 * @param other range to be compared
	 * @return result
	 */
	default boolean startIsBefore(S other) {
		return this.start().compareTo(other.start()) < 0;
	}
	
	/**
	 * Returns true if end
	 * @param other
	 * @return
	 */
	default boolean endIsAfter(S other) {
		return this.end().compareTo(other.end()) > 0;
	}
	
	S newSpan(T newStart, T newEnd);

	default S cutOffWithNewStart(T newStart) {
		return this.newSpan(newStart, this.end());
	}
	
	default S cutOffWithNewEnd(T newEnd) {
		return this.newSpan(this.start(), newEnd);
	}
}
