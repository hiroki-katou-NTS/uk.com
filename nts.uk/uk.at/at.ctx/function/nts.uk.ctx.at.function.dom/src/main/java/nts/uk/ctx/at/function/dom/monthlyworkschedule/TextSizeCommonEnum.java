package nts.uk.ctx.at.function.dom.monthlyworkschedule;
/**
 * The Enum TextSizeCommonEnum.
 */
//@author ChienDM
//帳票共通の文字の大きさ

public enum TextSizeCommonEnum {
	/** Small. */
	// 小
	SMALL(1, "SMALL"),
	/** During */
	// 中 
	DURING(2, "DURING"),
	/** Big*/
	// 大
	BIG(3, "BIG");
	
	/** The value. */
	public final Integer value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static TextSizeCommonEnum[] values = TextSizeCommonEnum.values();

	/**
	 * Instantiates a new Setting classification common To forms.
	 *
	 * @param value  the value
	 * @param nameId the name id
	 */
	private TextSizeCommonEnum(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the prints the setting remarks column
	 */
	public static TextSizeCommonEnum valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (TextSizeCommonEnum val : TextSizeCommonEnum.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
