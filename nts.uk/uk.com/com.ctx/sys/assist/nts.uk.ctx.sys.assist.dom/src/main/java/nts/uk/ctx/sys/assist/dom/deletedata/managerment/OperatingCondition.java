package nts.uk.ctx.sys.assist.dom.deletedata.managerment;

public enum OperatingCondition {
	// 準備中
	INPREPARATION(0),

	// 保存中
	SAVING(1),
	
	// 保存中
	INPROGRESS(2),

	// 保存中
	DELETING(3),

	// 保存中
	DONE(4),

	// 保存中
	INTERRUPTION_END(5),

	// 保存中
	ABNORMAL_TERMINATION(6);
	
	/** The value. */
	public final int value;

	
	/** The Constant values. */
	private final static OperatingCondition[] values = OperatingCondition.values();

	/**
	 * Instantiates a new role type.
	 *
	 * @param value the value
	 */
	private OperatingCondition(int value) {
		this.value = value;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the role type
	 */
	public static OperatingCondition valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (OperatingCondition val : OperatingCondition.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
