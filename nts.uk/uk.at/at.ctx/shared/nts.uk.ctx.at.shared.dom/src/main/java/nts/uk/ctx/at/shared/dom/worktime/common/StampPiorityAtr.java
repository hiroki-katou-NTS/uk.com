/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Enum StampPiorityAtr.
 */
// 打優先刻区分
public enum StampPiorityAtr {

	/** The going work. */
	// 出勤
	GOING_WORK(0, "Enum_StampPiorityAtr_allEmployee", "出勤"),

	/** The leave work. */
	// 退勤
	LEAVE_WORK(1, "Enum_StampPiorityAtr_allEmployee", "退勤"),

	/** The entering. */
	// 入門
	ENTERING(2, "Enum_StampPiorityAtr_allEmployee", "入門"),

	/** The exit. */
	// 退門
	EXIT(3, "Enum_StampPiorityAtr_allEmployee", "退門"),

	/** The pclogin. */
	// PCログイン
	PCLOGIN(4, "Enum_StampPiorityAtr_pcLogin", "PCログイン"),

	/** The pc logout. */
	// PCログオフ
	PC_LOGOUT(5, "Enum_StampPiorityAtr_pcLogout", "PCログオフ");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static StampPiorityAtr[] values = StampPiorityAtr.values();

	/**
	 * Late early atr.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 * @param description
	 *            the description
	 */
	private StampPiorityAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the late early atr
	 */
	public static StampPiorityAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (StampPiorityAtr val : StampPiorityAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
