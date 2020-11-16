package nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave;

import lombok.AllArgsConstructor;

@AllArgsConstructor
/** 期限日の管理方法 **/
public enum TermManagement {
	// 締めで管理する 0
	MANAGE_BY_TIGHTENING(0, "MANAGE_BY_TIGHTENING"),
	// 発生日を基準にして管理する 1
	MANAGE_BASED_ON_THE_DATE(1, "MANAGE_BASED_ON_THE_DATE");

	/** The value. */
	public final Integer value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static TermManagement[] values = TermManagement.values();

	private TermManagement(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	public static TermManagement valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (TermManagement val : TermManagement.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
