package nts.uk.ctx.sys.assist.dom.mastercopy;

/**
 * The Enum SystemType.
 */
// システム区分
public enum SystemType {
	
	/** The common. */
	COMMON(0, "Enum_SystemType_COMMON"),

	/** The employment. */
	EMPLOYMENT(1, "Enum_SystemType_EMPLOYMENT"),
	
	/** The salary. */
	SALARY(2, "Enum_SystemType_SALARY"),
	
	/** The human resource. */
	HUMAN_RESOURCE(3, "Enum_SystemType_HUMAN_RESOURCE");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static SystemType[] values = SystemType.values();

	/**
	 * Instantiates a new rounding.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 */
	private SystemType(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the rounding
	 */
	public static SystemType valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (SystemType val : SystemType.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
