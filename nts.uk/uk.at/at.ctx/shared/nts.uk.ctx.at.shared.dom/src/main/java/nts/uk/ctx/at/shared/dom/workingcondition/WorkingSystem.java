package nts.uk.ctx.at.shared.dom.workingcondition;


/**
 * 労働制
 */
public enum WorkingSystem {
	
	/** 通常勤務 */
	REGULAR_WORK(0, "Enum_WorkingSystem_REGULAR_WORK"),

	/** フレックス時間勤務 */
	FLEX_TIME_WORK(1, "Enum_WorkingSystem_FLEX_TIME_WORK"),

	/** 変形労働時間勤務 */
	VARIABLE_WORKING_TIME_WORK(2, "Enum_WorkingSystem_VARIABLE_WORKING_TIME_WORK"),
	
	/** 就業計算対象外 */
	EXCLUDED_WORKING_CALCULATE(3, "Enum_WorkingSystem_EXCLUDED_WORKING_CALCULATE");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static WorkingSystem[] values = WorkingSystem.values();

	/**
	 * Instantiates a new rounding.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 */
	private WorkingSystem(int value, String nameId) {
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
	public static WorkingSystem valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (WorkingSystem val : WorkingSystem.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
	
	/**
	 * 固定勤務であるか判定する
	 * @return　固定勤務である
	 */
	public boolean isRegularWork() {
		return this.equals(REGULAR_WORK);
	}
	
	
	/**
	 * 就業計算対象外であるか判定する
	 * @return　就業計算対象外である
	 */
	public boolean isExcludedWorkingCalculate() {
		return this.equals(EXCLUDED_WORKING_CALCULATE);
	}
	
	/**
	 * 変形労働であるか判定する
	 * @return　変形労働である
	 */
	public boolean isVariableWorkingTimeWork() {
		return this.equals(VARIABLE_WORKING_TIME_WORK);
	}
	
	/**
	 * フレックス時間勤務であるか判定する
	 * @return　フレックス勤務である
	 */
	public boolean isFlexTimeWork() {
		return this.equals(FLEX_TIME_WORK);
	}
}
