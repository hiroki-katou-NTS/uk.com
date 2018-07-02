package nts.uk.ctx.at.shared.dom.era.name;

/**
 * The Enum SystemType.
 */
public enum SystemType {
	
	/** The system. */
	SYSTEM(1, "Enum_SystemType_SYSTEM"),
	
	/** The not system. */
	NOT_SYSTEM(0, "Enum_SystemType_NOT_SYSTEM");
	
	/** The value. */
	public final Integer value;
	
	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static SystemType[] values = SystemType.values();
	
	/**
	 * Instantiates a new system type.
	 *
	 * @param value the value
	 * @param nameId the name id
	 */
	private SystemType(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
	/**
	 * Value of.
	 *
	 * @param value
	 * the value
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
