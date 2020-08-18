package nts.uk.ctx.at.function.dom.attendancerecord.export.setting;


//	文字の大きさ
/**
 * @author nws-ducnt
 *
 */
public enum ExportFontSize {

	// 	小
	/**
	 * ENUM_CHARS_SIZE_SMALL
	 */
	CHARS_SIZE_SMALL(0, "小"),

	// 	中
	/**
	 * ENUM_CHAR_SIZE_MEDIUM
	 */
	CHAR_SIZE_MEDIUM(1, "中"),
	
	// 	大
	/**
	 * ENUM_CHAR_SIZE_LARGE
	 */
	CHAR_SIZE_LARGE(2, "大");

	/** The value. */
	public final int value;

	/** The name. */
	public final String name;

	/** The Constant values. */
	private final static ExportFontSize[] values = ExportFontSize.values();

	/**
	 * Instantiates a new ExportFontSize.
	 *
	 * @param value
	 *            the value
	 * @param name
	 *            the name
	 */
	private ExportFontSize(int value, String name) {
		this.value = value;
		this.name = name;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return ExportFontSize
	 */
	public static ExportFontSize valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ExportFontSize val : ExportFontSize.values) {
			if (val.value == value) {

				return val;
			}
		}

		// Not found.
		return null;
	}
}
