package nts.uk.ctx.hr.shared.dom.personalinfo.retirementinfo;

/**
 * @author anhdt
 * 退職区分
 */
public enum RetirementCategory {
	// 退職
	RETIREMENT(1),

	// 転籍
	TRANSFER(2),

	// 解雇
	DISMISSAL(3),

	// 定年
	RETIREMENT_AGE(4);

	public final int value;

	/** The Constant values. */
	private final static RetirementCategory[] values = RetirementCategory.values();

	private RetirementCategory(int value) {
		this.value = value;
	}

	public static RetirementCategory valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (RetirementCategory val : RetirementCategory.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
