package nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone;

import lombok.AllArgsConstructor;
/**
 * 重複状態区分
 * @author trungtran
 *
 */
@AllArgsConstructor
public enum DuplicateStateAtr {
	/**0: 同じ期間*/
	SAME_PERIOD(0),
	/**1: 基準期間が完全に内包する */
	REFERENCE_PERIOD_COMPLETELY_EMBRACES(1),
	/**2: 基準期間が内包し、開始が同じ */
	REFERENCE_PERIOD_INCLUDE_START_SAME(2),
	/**3: 基準期間が内包し、終了が同じ */
	REFERENCE_PERIOD_INCLUDED_END_SAME(3),
	/** 4: 基準期間が完全に内包される*/
	REFERENCE_PERIOD_FULLY_INCLUDED(4),
	/**5: 基準期間が内包され、開始が同じ */
	REFERENCE_PERIOD_WAS_INCLUDED_START_SAME(5),
	/**6: 基準期間が内包され、終了が同じ */
	REFERENCE_PERIOD_WAS_INCLUDED_END_SAME(6),
	/** 7: 基準期間の後ろに連続する*/
	CONTINUOUS_AFTER_REFERENCE_PERIOD(7),
	/**8: 基準期間の前に連続する */
	CONTINUOUS_BEFORE_REFERENCE_PERIOD(8),
	/**9: 基準期間が比較期間より前 */
	REFERENCE_PERIOD_BEFORE_COMPARISON_PERIOD(9),
	/**10: 基準期間が比較期間より後 */
	REFERENCE_PERIOD_AFTER_COMPARISON_PERIOD(10),
	/**11: 比較期間の開始が基準期間の間にある */
	START_OF_COMPARISON_PERIOD_BETWEEN_REFERENCE_PERIOD(11),
	/** 12: 比較期間の終了が基準期間の間にある*/
	END_OF_COMPARISON_PERIOD_WITHIN_REFERENCE_PERIOD(12);
	/** The value. */
	public final int value;
	
	private final static DuplicateStateAtr[] values = DuplicateStateAtr.values();
	
	public static DuplicateStateAtr valueOf(Integer value){
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (DuplicateStateAtr val : DuplicateStateAtr.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
