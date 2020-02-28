package nts.uk.ctx.hr.shared.dom.personalinfo.retirementinfo;

/**
 * @author anhdt
 * 退職区分
 */
public enum RetirementCategory {
	
	RETIREMENT(1, "退職"), // 退職

	TRANSFER(2, "退職"), // 転籍

	DISMISSAL(3, "解雇"), // 解雇

	RETIREMENT_AGE(4, "定年"); // 定年

	public final int value;
	public final String name;

	/** The Constant values. */
	private final static RetirementCategory[] values = RetirementCategory.values();

	private RetirementCategory(int value, String name) {
		this.value = value;
		this.name = name;
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
