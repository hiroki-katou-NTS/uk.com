/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.schedule.workschedule;

/**
 * 処理状態
 * @author tutk
 *
 */
public enum ProcessingStatus {

	//次の日へ
	NEXT_DAY(0, "Enum_ProcessingStatus_NEXT_DAY", "次の日へ"),

	//処理終了する
	END_PROCESS(1, "Enum_ProcessingStatus_END_PROCESS", "処理終了する"),
	
	//処理正常
	NORMAL_PROCESS(2, "Enum_ProcessingStatus_NORMAL_PROCESS", "処理正常"),
	
	// 次の日へ（エラーあり）
	NEXT_DAY_WITH_ERROR(3, "Enum_ProcessingStatus_NEXT_DAY_WITH_ERROR", "次の日へ（エラーあり）");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static ProcessingStatus[] values = ProcessingStatus.values();

	/**
	 * Instantiates a new completion status.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private ProcessingStatus(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the completion status
	 */
	public static ProcessingStatus valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ProcessingStatus val : ProcessingStatus.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}

}
