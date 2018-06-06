package nts.uk.ctx.sys.assist.dom.deletedata;

public enum DelType {
	// システム管理者
	MANUAL(0),

	// 会社管理者
	AUTOMATIC(1);
	
	/** The value. */
	public final int value;

	
	/** The Constant values. */
	private final static DelType[] values = DelType.values();

	/**
	 * Instantiates a new role type.
	 *
	 * @param value the value
	 */
	private DelType(int value) {
		this.value = value;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the role type
	 */
	public static DelType valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (DelType val : DelType.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
