package nts.uk.ctx.at.record.dom.stamp.management;

/**
 * 履歴表示方法
 * @author phongtq
 *
 */

public enum HistoryDisplayMethod {
	
	/** 表示しない */
	HIDE(0),

	/** 打刻一覧を表示 */
	DISPLAY(1),

	/** タイムカードを表示 */
	SHOW_TIME_CARD(2);

	/** The value. */
	public int value;

	/** The Constant values. */
	private final static HistoryDisplayMethod[] values = HistoryDisplayMethod.values();

	/**
	 * Instantiates a new closure id.
	 *
	 * @param value
	 *            the value
	 * @param description
	 *            the description
	 */
	private HistoryDisplayMethod(int value) {
		this.value = value;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the use division
	 */
	public static HistoryDisplayMethod valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (HistoryDisplayMethod val : HistoryDisplayMethod.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}