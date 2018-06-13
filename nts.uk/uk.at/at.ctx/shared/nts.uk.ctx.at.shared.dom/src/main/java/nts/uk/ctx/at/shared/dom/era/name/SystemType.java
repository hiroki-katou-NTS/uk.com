package nts.uk.ctx.at.shared.dom.era.name;

public enum SystemType {
	
	SYSTEM(1, "Enum_SystemType_SYSTEM"),
	NOT_SYSTEM(0, "Enum_SystemType_NOT_SYSTEM");
	
	public final int value;
	
	public final String nameId;

	/** The Constant values. */
	private final static SystemType[] values = SystemType.values();
	
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
