package nts.uk.ctx.at.record.dom.daily.dailyperformance.classification;

import lombok.AllArgsConstructor;

/**
 * @author thanhnx
 * するしない区分
 */
@AllArgsConstructor
public enum DoWork {
	// しない
	NOTUSE(0, "しない"),

	/** The pattern schedule. */
	// する
	USE(1, "する");

	public final int value;

	public final String description;

	private final static DoWork[] values = DoWork.values();

	public static DoWork valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (DoWork val : DoWork.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
