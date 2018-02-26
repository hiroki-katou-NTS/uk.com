/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration;

/**
 * The Enum ManagementDistinction.
 */
// 管理区分
public enum ManagementDistinction {
	
	/** The not manage. */
	// 管理しない
	NOT_MANAGE(0, "Enum_ManageAtr_NotManage"),

	/** The manage. */
	// 管理する
	MANAGE(1, "Enum_ManageAtr_Manage");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static ManagementDistinction[] values = ManagementDistinction.values();

	
	/**
	 * Instantiates a new management distinction.
	 *
	 * @param value the value
	 * @param nameId the name id
	 */
	private ManagementDistinction(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the public holiday period
	 */
	public static ManagementDistinction valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ManagementDistinction val : ManagementDistinction.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
