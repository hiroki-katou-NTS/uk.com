package nts.uk.ctx.at.shared.dom.ot.frame;

/**
 * 残業枠の役割
 * @author shuichi_ishida
 */
public enum RoleOvertimeWork {

	/** 法定外残業 */
	OUT_OVERTIME_STATUTORY(0, "Enum_RoleOvertimeWork_OUT_STAT"),

	/** 法定内残業 */
	IN_OVERTIME_STATUTORY(1, "Enum_RoleOvertimeWork_IN_STAT"),

	/** 法定内・外混在 */
	MIX_IN_OUT_STATUTORY(2, "Enum_RoleOvertimeWork_MIX_IN_OUT_STAT");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static RoleOvertimeWork[] values = RoleOvertimeWork.values();

	/**
	 * Instantiates a new RoleOvertimeWork.
	 *
	 * @param value the value
	 * @param nameId the name id
	 */
	private RoleOvertimeWork(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the RoleOvertimeWork
	 */
	public static RoleOvertimeWork valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (RoleOvertimeWork val : RoleOvertimeWork.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
