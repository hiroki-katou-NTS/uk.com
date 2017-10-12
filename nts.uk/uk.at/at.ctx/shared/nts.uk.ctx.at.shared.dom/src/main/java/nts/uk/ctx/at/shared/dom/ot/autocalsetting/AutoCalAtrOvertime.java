/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.ot.autocalsetting;

/**
 * The Enum AutoCalAtrOvertime.
 */
// 時間外の自動計算区分
public enum AutoCalAtrOvertime {

	/** The timerecorder. */
	TIMERECORDER(0, "タイムレコーダーで選択", "Enum_AutoCalAtrOvertime_TimeRecorder"),

	/** The calculatemboss. */
	CALCULATEMBOSS(1, "打刻から計算する", "Enum_AutoCalAtrOvertime_CalculateEmbossing"),

	/** The applymanuallyenter. */
	APPLYMANUALLYENTER(2, "申請または手入力", "Enum_AutoCalAtrOvertime_ApplyOrManuallyEnter");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static AutoCalAtrOvertime[] values = AutoCalAtrOvertime.values();

	/**
	 * Instantiates a new auto cal atr overtime.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 * @param description
	 *            the description
	 */
	private AutoCalAtrOvertime(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the auto cal atr overtime
	 */
	public static AutoCalAtrOvertime valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (AutoCalAtrOvertime val : AutoCalAtrOvertime.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
