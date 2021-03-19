package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.calculationsettings.shorttimework;

/**
 * 短時間勤務の計算方法
 * @author shuichi_ishida
 */
public enum CalcMethodOfShortTimeWork {

	/** The without number. */
	WITHIOUT(0, "Enum_CalcMethodOfShortTimeWork_WITHOUT", "育児時間を就業時間の外数として扱う"),
	/** The within number. */
	WITHIN(1, "Enum_CalcMethodOfShortTimeWork_WITHIN", "育児時間を就業時間の内数として扱う");

	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static CalcMethodOfShortTimeWork[] values = CalcMethodOfShortTimeWork.values();

	/**
	 * Instantiates a new calculation method of short time work.
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private CalcMethodOfShortTimeWork(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 * @param value the value
	 * @return the calculation method of short time work
	 */
	public static CalcMethodOfShortTimeWork valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (CalcMethodOfShortTimeWork val : CalcMethodOfShortTimeWork.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
