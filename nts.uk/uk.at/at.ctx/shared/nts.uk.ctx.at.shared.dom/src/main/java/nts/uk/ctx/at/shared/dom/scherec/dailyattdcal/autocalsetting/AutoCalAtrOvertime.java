/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting;

/**
 * The Enum AutoCalAtrOvertime.
 */
// 時間外の自動計算区分
public enum AutoCalAtrOvertime {
	
	/** The applymanuallyenter. */
	// 申請または手入力
	APPLYMANUALLYENTER(0, "Enum_AutoCalAtrOvertime_ApplyOrManuallyEnter", "申請または手入力"),
	
	/** The calculatemboss. */
	// 打刻から計算する
	CALCULATEMBOSS(2, "Enum_AutoCalAtrOvertime_CalculateEmbossing", "打刻から計算する"),

	/** The timerecorder. */
	// タイムレコーダーで選択
	TIMERECORDER(1, "Enum_AutoCalAtrOvertime_TimeRecorder", "タイムレコーダーで選択");

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
	
	/**
	 * 打刻から計算するであるか判定する
	 * @return　打刻から計算する
	 */
	public boolean isCalculateEmbossing() {
		return CALCULATEMBOSS.equals(this);
	}
	
	/**
	 * 申請または手入力であるか判定する
	 * @return　申請または手入力である
	 */
	public boolean isApplyOrManuallyEnter() {
		return APPLYMANUALLYENTER.equals(this);
	}
	
	
	/**
	 * タイムレコーダーで選択するであるか判定する
	 * @return　タイムレコーダーで選択する
	 */
	public boolean isSelectTimeRecorder() {
		return TIMERECORDER.equals(this);
	}
}
