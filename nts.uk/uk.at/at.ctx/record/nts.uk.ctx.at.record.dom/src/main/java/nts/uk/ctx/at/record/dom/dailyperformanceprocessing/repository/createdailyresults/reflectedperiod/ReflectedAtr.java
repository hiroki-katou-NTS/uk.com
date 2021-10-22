package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.repository.createdailyresults.reflectedperiod;

import lombok.AllArgsConstructor;

/**
 * ・終了状態（正常｜中断）
 * @author tutk
 *
 */
@AllArgsConstructor
public enum ReflectedAtr {

	// 正常
	NORMAL(0, "正常"),

	// 中断
	SUSPENDED(1, "中断");

	public final int value;

	public final String name;

	/** The Constant values. */
	private final static ReflectedAtr[] values = ReflectedAtr.values();

	public static ReflectedAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ReflectedAtr val : ReflectedAtr.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
