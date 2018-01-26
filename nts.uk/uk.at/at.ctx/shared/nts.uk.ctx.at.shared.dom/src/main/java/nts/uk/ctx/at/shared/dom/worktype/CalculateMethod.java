package nts.uk.ctx.at.shared.dom.worktype;

/**
 * The Enum CalulateMethod
 * 出勤率の計算方法
 * @author sonnh
 *
 */
public enum CalculateMethod {

	/**
	 * 出勤としない（分子に加算しない）
	 */
	DO_NOT_GO_TO_WORK(0, "Enum_CalculateMethod_DO_NOT_GO_TO_WORK","出勤としない（分子に加算しない）"),
	
	/**
	 * 出勤とみなす（分子に加算する）
	 */
	MAKE_ATTENDANCE_DAY(1, "Enum_CalculateMethod_MAKE_ATTENDANCE_DAY","出勤とみなす（分子に加算する）"),
	

	/**
	 * 労働日から除外する（分母から減算）
	 */
	EXCLUDE_FROM_WORK_DAY(2, "Enum_CalculateMethod_EXCLUDE_FROM_WORK_DAY","労働日から除外する（分母から減算）"),
	

	/**
	 * 時間年休時のみ出勤扱い
	 */
	TIME_DIGEST_VACATION(3, "Enum_CalculateMethod_TIME_DIGEST_VACATION","時間年休時のみ出勤扱い");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static CalculateMethod[] values = CalculateMethod.values();

	/**
	 * Instantiates a new role type.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private CalculateMethod(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the role type
	 */
	public static CalculateMethod valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (CalculateMethod val : CalculateMethod.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
