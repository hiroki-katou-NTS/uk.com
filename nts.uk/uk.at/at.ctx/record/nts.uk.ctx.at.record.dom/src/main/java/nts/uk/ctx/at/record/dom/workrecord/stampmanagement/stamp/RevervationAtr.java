package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

import lombok.AllArgsConstructor;

/**
 * @author ThanhNX
 *
 *         予約区分
 */
@AllArgsConstructor
public enum RevervationAtr {

	// 0:なし
	NONE(0, "なし"),

	// 1:予約
	RESERVATION(1, "予約"),

	// 2:予約取消
	CANCEL_RESERVATION(2, "予約取消");

	public final int value;

	public final String name;

	/** The Constant values. */
	private final static RevervationAtr[] values = RevervationAtr.values();

	public static RevervationAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (RevervationAtr val : RevervationAtr.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
