/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.shortworktime;

/**
 * The Enum ChildCareAtr.
 */
// 育児介護区分
public enum ChildCareAtr {

	/** The child care. */
	// 育児
	CHILD_CARE(0, "Enum_ChildCareAtr_ChildCare", "育児"),

	/** The care. */
	// 介護
	CARE(1, "Enum_ChildCareAtr_Care", "介護");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static ChildCareAtr[] values = ChildCareAtr.values();

	/**
	 * Instantiates a new re create atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private ChildCareAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the re create atr
	 */
	public static ChildCareAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ChildCareAtr val : ChildCareAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
	
	/**
	 * 育児であるか判定する
	 * @return 育児である
	 */
	public boolean isChildCare() {
		return CHILD_CARE.equals(this);
	}
	
	/**
	 * 介護であるか判定する
	 * @return 介護である
	 */
	public boolean isCare() {
		return CARE.equals(this);
	}
}
