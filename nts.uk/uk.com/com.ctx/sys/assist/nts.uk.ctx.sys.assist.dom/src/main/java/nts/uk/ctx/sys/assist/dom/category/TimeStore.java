package nts.uk.ctx.sys.assist.dom.category;

/**
 * 
 * @author thanh.tq
 * 保存期間区分
 */

public enum TimeStore {
	
	FULL_TIME(0,"Enum_TimeStore_FULL_TIME"),
	DAILY(1,"Enum_TimeStore_DAILY"),
	MONTHLY(2,"Enum_TimeStore_MONTHLY"),
	ANNUAL(3,"Enum_TimeStore_ANNUAL");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private TimeStore(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	/** The Constant values. */
	private final static TimeStore[] values = TimeStore.values();
	
	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the role type
	 */
	public static TimeStore valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (TimeStore val : TimeStore.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
	
}