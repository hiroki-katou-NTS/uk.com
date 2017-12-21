package nts.uk.ctx.at.function.dom.alarm.extractionrange;

/**
 * @author thanhpv
 * 抽出する範囲
 */
public enum ExtractionRange {
	/**
	 * 単年
	 */
	YEAR(0, "Enum_ExtractionRange_YEAR", "単年"),
	/**
	 * 単月
	 */
	MONTH(1, "Enum_ExtractionRange_MONTH", "単月"),
	/**
	 * 周期
	 */
	WEEK(2, "Enum_ExtractionRange_WEEK", "周期"),
	/**
	 * 期間
	 */
	PERIOD(3, "Enum_ExtractionRange_PERIOD", "期間");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static ExtractionRange[] values = ExtractionRange.values();
	
	private ExtractionRange(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}
	public static ExtractionRange valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ExtractionRange val : ExtractionRange.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
