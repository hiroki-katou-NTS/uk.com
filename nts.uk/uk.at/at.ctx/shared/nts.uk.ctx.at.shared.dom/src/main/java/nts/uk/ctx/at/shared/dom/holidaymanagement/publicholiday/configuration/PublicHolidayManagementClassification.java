/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration;

/**
 * The Enum PublicHolidayManagementClassification.
 */
// 公休管理区分
public enum PublicHolidayManagementClassification {
	
	/** The  1 month. */
	// 1ヵ月
	_1_MONTH(0, "Enum_HolidayManageAtr_OneMonth"),

	/** The  4 weeks 4 days off. */
	// 4週4休
	_4_WEEKS_4_DAYS_OFF(1, "Enum_HolidayManageAtr_TwoEigntDays");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static PublicHolidayManagementClassification[] values = PublicHolidayManagementClassification.values();

	
	/**
	 * Instantiates a new public holiday management classification.
	 *
	 * @param value the value
	 * @param nameId the name id
	 */
	private PublicHolidayManagementClassification(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the public holiday management classification
	 */
	public static PublicHolidayManagementClassification valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (PublicHolidayManagementClassification val : PublicHolidayManagementClassification.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
