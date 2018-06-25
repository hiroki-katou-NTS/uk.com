package nts.uk.ctx.at.function.dom.attendancerecord.item;

/**
 * The Enum SingleItemAttributes.
 */
//単一項目の属性
public enum SingleItemAttributes {

	//勤務種類
	DUTY_TYPE(13,"勤務種類"),
	
	//就業時間帯
	WORKING_HOUR(14,"就業時間帯"),
	
	//時刻
	DAY_TIME(15,"時刻");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String name;

	/** The Constant values. */
	private final static SingleItemAttributes[] values = SingleItemAttributes.values();

	/**
	 * Instantiates a new rounding.
	 *
	 * @param value            the value
	 * @param name the name
	 */
	private SingleItemAttributes(int value, String name) {
		this.value = value;
		this.name = name;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the rounding
	 */
	public static SingleItemAttributes valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (SingleItemAttributes val : SingleItemAttributes.values) {
			if (val.value == value) {

				return val;
			}
		}

		// Not found.
		return null;
	}
}
