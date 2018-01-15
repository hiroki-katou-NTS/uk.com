package nts.uk.ctx.at.shared.dom.worktime.algorithm.rangeofdaytimezone;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DuplicationStatusOfTimeZone {
	/** 同じ時間帯 */
	SAME_WORK_TIME(0),
	/** 開始を跨いでいる */
	BEYOND_THE_START(1),
	/** 終了を跨いでいる */
	BEYOND_THE_END(2),
	/** 包含(自分が内側) */
	INCLUSION_OUTSIDE(3),
	/** 包含(自分が外側) */
	INCLUSION_INSIDE(4),
	/** 非重複 */
	NON_OVERLAPPING(5);
	/** The value. */
	public final int value;

	private final static DuplicationStatusOfTimeZone[] values = DuplicationStatusOfTimeZone.values();

	public static DuplicationStatusOfTimeZone valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}
		// Find value.
		for (DuplicationStatusOfTimeZone val : DuplicationStatusOfTimeZone.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}

}
