/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.employeeinfo;

/**
 * The Enum WorkScheduleMasterReferenceAtr.
 */
// 勤務予定作成マスタ参照区分
public enum WorkScheduleMasterReferenceAtr {

	/** The work place. */
	// 職場
	WORKPLACE(1, "Enum_WorkScheduleMasterReferenceAtr_Workplace", "職場"),

	/** The classification. */
	// 分類
	CLASSIFICATION(2, "Enum_WorkScheduleMasterReferenceAtr_Classification", "分類"),
	
	//会社
	COMPANY(0, "Enum_WorkScheduleMasterReferenceAtr_Classification", "分類");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static WorkScheduleMasterReferenceAtr[] values = WorkScheduleMasterReferenceAtr.values();

	/**
	 * Instantiates a new work schedule master reference atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private WorkScheduleMasterReferenceAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the work schedule master reference atr
	 */
	public static WorkScheduleMasterReferenceAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (WorkScheduleMasterReferenceAtr val : WorkScheduleMasterReferenceAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
