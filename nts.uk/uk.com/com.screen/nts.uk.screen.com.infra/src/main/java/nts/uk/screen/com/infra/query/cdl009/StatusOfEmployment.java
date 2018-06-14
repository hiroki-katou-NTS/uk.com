/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.com.infra.query.cdl009;

/**
 * The Enum StatusOfEmployment.
 */
// 在職状態
public enum StatusOfEmployment {

	/** The incumbent. */
	// 在職
	INCUMBENT(1, "Enum_StatusOfEmployment_INCUMBENT", "在職"),

	/** The leave of absence. */
	// 休職
	LEAVE_OF_ABSENCE(2, "Enum_StatusOfEmployment_LEAVE_OF_ABSENCE", "休職"),

	/** The holiday. */
	// 休業
	HOLIDAY(3, "Enum_StatusOfEmployment_HOLIDAY", "休業"),

	/** The before joining. */
	// 入社前
	BEFORE_JOINING(4, "Enum_StatusOfEmployment_BEFORE_JOINING", "入社前"),

	/** The on loan. */
	// 出向中
	ON_LOAN(5, "Enum_StatusOfEmployment_ON_LOAN", "出向中"),

	/** The retirement. */
	// 退職
	RETIREMENT(6, "Enum_StatusOfEmployment_RETIREMENT", "退職");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static StatusOfEmployment[] values = StatusOfEmployment.values();

	/**
	 * Instantiates a new status of employment.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private StatusOfEmployment(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the status of employment
	 */
	public static StatusOfEmployment valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (StatusOfEmployment val : StatusOfEmployment.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
