package nts.gul.util.range;

public enum RangeDuplication {
	/**
	 * BASE *-------*
	 * COMP *-------*
	 */
	SAME_COMPLETE,
	
	/**
	 * BASE *-------*
	 * COMP   *---*
	 */
	BASE_CONTAINS_COMPLETE,
	
	/**
	 * BASE *-------*
	 * COMP *----*
	 */
	BASE_CONTAINS_SAME_START,
	
	/**
	 * BASE *-------*
	 * COMP    *----*
	 */
	BASE_CONTAINS_SAME_END,
	
	/**
	 * BASE   *---*
	 * COMP *-------*
	 */
	BASE_CONTAINED_COMPLETE,
	
	/**
	 * BASE *----*
	 * COMP *-------*
	 */
	BASE_CONTAINED_SAME_START,
	
	/**
	 * BASE    *----*
	 * COMP *-------*
	 */
	BASE_CONTAINED_SAME_END,
	
	/**
	 * BASE *---*
	 * COMP     *---*
	 */
	CONTINUOUS_AFTER_BASE,
	
	/**
	 * BASE     *---*
	 * COMP *---*
	 */
	CONTINUOUS_BEFORE_BASE,
	
	/**
	 * BASE *---*
	 * COMP      *---*
	 */
	ADJACENT_AFTER_BASE,
	
	/**
	 * BASE      *---*
	 * COMP *---*
	 */
	ADJACENT_BEFORE_BASE,
	
	/**
	 * BASE *---*
	 * COMP        *---*
	 */
	BASE_BEFORE_COMPARED,
	
	/**
	 * BASE        *---*
	 * COMP *---*
	 */
	BASE_AFTER_COMPARED,
	
	/**
	 * BASE *----*
	 * COMP    *-----*
	 */
	START_SANDWITCHED_BETWEEN_BASE,
	
	/**
	 * BASE    *-----*
	 * COMP *----*
	 */
	END_SANDWITCHED_BETWEEN_BASE,
}
