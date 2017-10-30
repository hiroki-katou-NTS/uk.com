/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.executionlog;

/**
 * The Enum CompletionStatus.
 */
// 完了状態
public enum CompletionStatus {

	/** The incomplete. */
	// 未完了
	INCOMPLETE(0, "Enum_CompletionStatus_incomplete", "未完了"),

	/** The done. */
	// 完了
	DONE(1, "Enum_CompletionStatus_done", "完了"),
	
	/** The completion error. */
	// 完了（エラーあり）
	COMPLETION_ERROR(2, "Enum_CompletionStatus_completionError", "完了（エラーあり）"),
	
	/** The interruption. */
	// 中断
	INTERRUPTION(3, "Enum_CompletionStatus_interruption", "中断");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static CompletionStatus[] values = CompletionStatus.values();

	/**
	 * Instantiates a new completion status.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private CompletionStatus(int value, String nameId, String description) {
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
	public static CompletionStatus valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (CompletionStatus val : CompletionStatus.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}

}
