package nts.uk.ctx.at.shared.dom.workingcondition;


/**
 * 勤務予定作成マスタ参照区分
 * The Enum WorkScheduleMasterReferenceAtr.
 */
public enum WorkScheduleMasterReferenceAtr {

	/** 職場 - The work place. */
	WORK_PLACE(1, "Enum_WorkScheduleMasterReferenceAtr_Workplace"),

	/** 分類 - The classification. */
	CLASSIFICATION(2, "Enum_WorkScheduleMasterReferenceAtr_Classification"),
	
	/** 会社 - The company. */
	COMPANY(0, "Enum_WorkScheduleMasterReferenceAtr_Company");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static WorkScheduleMasterReferenceAtr[] values = WorkScheduleMasterReferenceAtr.values();

	/**
	 * Instantiates a new rounding.
	 *
	 * @param value
	 *            the value
	 * @param nameId
	 *            the name id
	 */
	private WorkScheduleMasterReferenceAtr(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the rounding
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
