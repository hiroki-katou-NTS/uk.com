package nts.uk.ctx.at.shared.dom.common;

public interface ComparableRange<T extends Comparable<T>> {

	/**
	 * Returns start value of range.
	 * @return start
	 */
	T startValue();
	
	/**
	 * Returns end value of range.
	 * @return end
	 */
	T endValue();
	
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
	 * 期間の重複状態を判定する
	 * @param other 
	 * @return RangeDuplication
	 */
	default RangeDuplication compare(ComparableRange<T> other) {
		
		if (this.sameStart(other) && this.sameEnd(other)) {
			return RangeDuplication.SAME_SPAN;
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
		
		if (other.contains(this)) {
			if (this.sameStart(other)) {
				return RangeDuplication.BASE_CONTAINED_SAME_START;
			} else if (this.sameEnd(other)) {
				return RangeDuplication.BASE_CONTAINED_SAME_END;
			} else {
				return RangeDuplication.BASE_CONTAINED_COMPLETE;
			}
		}

		if (this.startValue().equals(other.endValue())) {
			return RangeDuplication.CONTINUOUS_BEFORE_BASE;
		}
		
		if (this.endValue().equals(other.startValue())) {
			return RangeDuplication.CONTINUOUS_AFTER_BASE;
		}
		
		if (this.isBefore(other)) {
			return RangeDuplication.BASE_BEFORE_COMPARED;
		}
		
		if (this.isAfter(other)) {
			return RangeDuplication.BASE_AFTER_COMPARED;
		}
		
		if (this.contains(other.startValue())) {
			return RangeDuplication.COMPARED_START_BETWEEN_BASE;
		}
		
		if (this.contains(other.endValue())) {
			return RangeDuplication.COMPARED_END_BETWEEN_BASE;
		}
		
		throw new RuntimeException("unknown State Start:End " + this.startValue().toString() + ":" + this.endValue().toString() + "，Start:End" + other.startValue().toString() + ":" + other.endValue().toString());
	}
	
	/**
	 * 基準期間が完全に内包する
	 * @param point
	 * @return 基準期間が完全に内包する
	 */
	default boolean contains(T point) {
		return this.startValue().compareTo(point) <= 0 && this.endValue().compareTo(point) >= 0;
	}
	
	/**
	 * 基準時間が完全に内包する
	 * @param other
	 * @return　基準期間が完全に内包する
	 */
	default boolean contains(ComparableRange<T> other) {
		return this.startValue().compareTo(other.startValue()) <= 0 && this.endValue().compareTo(other.endValue()) >= 0;
	}

	/**
	 * 開始時刻が同じか判定
	 * @param other
	 * @return　同じである
	 */
	default boolean sameStart(ComparableRange<T> other) {
		return this.startValue().equals(other.startValue());
	}
	
	/**
	 * 終了時刻が同じか判定
	 * @param other
	 * @return 同じである
	 */
	default boolean sameEnd(ComparableRange<T> other) {
		return this.endValue().equals(other.endValue());
	}
	
	/**
	 * 基準期間の開始時刻と比較期間の開始時刻の比較
	 * @param other
	 * @return　基準期間の開始時刻の方が遅い
	 */
	default boolean greaterStart(ComparableRange<T> other) {
		return this.startValue().compareTo(other.startValue()) > 0;
	}
	
	/**
	 * 基準期間の開始時刻と比較期間の開始時刻の比較
	 * @param other
	 * @return 基準期間の開始時刻の方が遅い
	 */
	default boolean greaterOrEqualStart(ComparableRange<T> other) {
		return this.startValue().compareTo(other.startValue()) >= 0;
	}
	
	/**
	 * 基準期間の終了時刻と比較期間の終了時刻の比較
	 * @param other
	 * @return 基準期間の終了時刻の方が早い
	 */
	default boolean greaterEnd(ComparableRange<T> other) {
		return this.startValue().compareTo(other.startValue()) < 0;
	}
	
	/**
	 * 基準期間の終了時刻と比較期間の終了時刻の比較
	 * @param other
	 * @return 基準期間の終了時刻の方が早い
	 */
	default boolean greaterOrEqualEnd(ComparableRange<T> other) {
		return this.startValue().compareTo(other.startValue()) <= 0;
	}
	
	/**
	 * 基準期間の終了時刻と比較期間の開始時刻の比較
	 * @param other
	 * @return 基準期間の終了時刻の方が早い
	 */
	default boolean isBefore(ComparableRange<T> other) {
		return this.endValue().compareTo(other.startValue()) < 0;
	}
	
	/**
	 * 基準期間の終了時刻と比較期間の開始時刻の比較
	 * @param other
	 * @return 基準期間の開始時刻の方が早い
	 */
	default boolean isAfter(ComparableRange<T> other) {
		return this.startValue().compareTo(other.endValue()) > 0;
	}
}
