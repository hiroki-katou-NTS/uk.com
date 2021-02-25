package nts.uk.ctx.at.function.dom.dailyworkschedule;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.就業機能.日別勤務表.文字の大きさ
 * @author LienPTK
 *
 */
public enum FontSizeEnum {
	/** 大 */
	BIG(1),
	/** 小 */
	SMALL(0);
	
	public final int value;

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
