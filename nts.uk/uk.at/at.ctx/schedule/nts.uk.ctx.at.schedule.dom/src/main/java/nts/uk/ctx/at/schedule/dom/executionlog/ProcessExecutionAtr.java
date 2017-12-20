/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.executionlog;

/**
 * The Enum ProcessExecutionAtr.
 */
// 処理実行区分
public enum ProcessExecutionAtr {

	/** The rebuild. */
	// もう一度作り直す
	REBUILD(0, "Enum_ProcessExecutionAtr_rebuild", "もう一度作り直す"),

	/** The reconfig. */
	// 再設定する
	RECONFIG(1, "Enum_ProcessExecutionAtr_reconfig", "再設定する");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static ProcessExecutionAtr[] values = ProcessExecutionAtr.values();

	/**
	 * Instantiates a new process execution atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private ProcessExecutionAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the process execution atr
	 */
	public static ProcessExecutionAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ProcessExecutionAtr val : ProcessExecutionAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}

}
