package nts.uk.ctx.at.record.dom.stamp.management;

/**
 * ボタン配置タイプ
 * @author phongtq
 *
 */

public enum ButtonLayoutType {
	
	/** 大2小4 */
	LARGE_2_SMALL_4(0),

	/** 小8 */
	SMALL_8(1);

	/** The value. */
	public int value;

	/** The Constant values. */
	private final static ButtonLayoutType[] values = ButtonLayoutType.values();

	/**
	 * Instantiates a new closure id.
	 *
	 * @param value
	 *            the value
	 * @param description
	 *            the description
	 */
	private ButtonLayoutType(int value) {
		this.value = value;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the use division
	 */
	public static ButtonLayoutType valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ButtonLayoutType val : ButtonLayoutType.values) {
			if (val.value == value) {
				return val;
			}
		}
		// Not found.
		return null;
	}
}
