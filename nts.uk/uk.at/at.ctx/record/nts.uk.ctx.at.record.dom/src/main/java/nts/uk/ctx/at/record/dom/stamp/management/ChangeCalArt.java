package nts.uk.ctx.at.record.dom.stamp.management;

/**
 * 計算区分変更対象
 * @author phongtq
 *
 */
public enum ChangeCalArt {
	
	/** なし */
	NONE(0),

	/** 早出 */
	EARLY_APPEARANCE(1),

	/** 残業 */
	OVER_TIME(2),

	/** 休出 */
	BRARK(3),

	/** ﾌﾚｯｸｽ */
	FIX(4);

	/** The value. */
	public int value;

	/** The Constant values. */
	private final static ChangeCalArt[] values = ChangeCalArt.values();

	/**
	 * Instantiates a new closure id.
	 *
	 * @param value
	 *            the value
	 * @param description
	 *            the description
	 */
	private ChangeCalArt(int value) {
		this.value = value;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the use division
	 */
	public static ChangeCalArt valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ChangeCalArt val : ChangeCalArt.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}