/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.executionlog;

/**
 * The Enum ExecutionStatus.
 */
// 実行状況
public enum ExecutionStatus {

	/** The not created. */
	// 未作成
	NOT_CREATED(0, "Enum_ExecutionStatus_notCreated", "未作成"),

	/** The created. */
	// 作成済み
	CREATED(1, "Enum_ExecutionStatus_created", "作成済み");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static ExecutionStatus[] values = ExecutionStatus.values();

	/**
	 * Instantiates a new execution status.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private ExecutionStatus(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the execution status
	 */
	public static ExecutionStatus valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ExecutionStatus val : ExecutionStatus.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}

}
