package nts.uk.ctx.at.function.dom.attendancerecord.export.setting;

/**
 * The Enum NameUseAtr.
 */
// 名称使用区分
public enum NameUseAtr {

	/** The formal name. */
	// 正式名称
	FORMAL_NAME(1, "正式名称"),

	/** The short name. */
	// 略称
	SHORT_NAME(2, "略称");

	/** The value. */
	public final int value;

	/** The name. */
	public final String name;

	/** The Constant values. */
	private final static NameUseAtr[] values = NameUseAtr.values();

	/**
	 * Instantiates a new name use atr.
	 *
	 * @param value
	 *            the value
	 * @param name
	 *            the name
	 */
	private NameUseAtr(int value, String name) {
		this.value = value;
		this.name = name;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the name use atr
	 */
	public static NameUseAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (NameUseAtr val : NameUseAtr.values) {
			if (val.value == value) {

				return val;
			}
		}

		// Not found.
		return null;
	}

}
