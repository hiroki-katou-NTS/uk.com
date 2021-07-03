/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Enum Superiority.
 */
public enum Superiority {
	
	/** The attendance. */
	// 出勤
	ATTENDANCE(0, "Enum_Superiority_specifiedTimeSubHol", "出勤"),
	
	/** The office work. */
	// 退勤
	OFFICE_WORK(1, "Enum_Superiority_specifiedTimeSubHol", "退勤"),
	
	/** The go out. */
	// 外出
	GO_OUT(2, "Enum_Superiority_specifiedTimeSubHol", "外出"),
	
	/** The turn back. */
	// 戻り
	TURN_BACK(3, "Enum_Superiority_certainTimeExcSubHol", "戻り");

	/** The value. */
	public int value;

	/** The name id. */
	public String nameId;

	/** The description. */
	public String description;

	/** The Constant values. */
	private final static Superiority[] values = Superiority.values();

	/**
	 * Instantiates a new superiority.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private Superiority(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the superiority
	 */
	public static Superiority valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (Superiority val : Superiority.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
