/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly;

/**
 * The Enum ManagementCategory.
 */
public enum ManagementCategory {
	
	/** The Manage. */
	Manage(1),
	
	/** The Not manage. */
	NotManage(0);
	
	/** The value. */
	public int value;
	
	/**
	 * Instantiates a new management category.
	 *
	 * @param value the value
	 */
	private ManagementCategory(int value) {
		this.value = value;
	}
	
	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the management category
	 */
	public static ManagementCategory valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ManagementCategory val : ManagementCategory.values()) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
