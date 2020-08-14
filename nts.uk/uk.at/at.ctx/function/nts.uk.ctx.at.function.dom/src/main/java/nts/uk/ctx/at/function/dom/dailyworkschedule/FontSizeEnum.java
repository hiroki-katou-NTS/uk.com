package nts.uk.ctx.at.function.dom.dailyworkschedule;
/**
 * 文字の大きさ
 * @author LienPTK
 *
 */
public enum FontSizeEnum {
	/** 大 */
	BIG(0),
	/** 小 */
	SMALL(1);
	
	private final int value;

	private FontSizeEnum(int value) {
		this.value = value;
	}
	
	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the font size enum
	 */
	public static FontSizeEnum valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (FontSizeEnum val : FontSizeEnum.values()) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
