/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workingcondition;

/**
 * The Enum UseAtr.
 */
// するしない区分
public enum ManageAtr {	
	
	/** The pattern schedule. */
	// する 
	USE(1, "Enum_ManageAtr_Manage", "管理する"),
	
	/**
	 * Notuse.
	 */
	// しない
	NOTUSE(0, "Enum_ManageAtr_NotManage", "管理しない");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static ManageAtr[] values = ManageAtr.values();

	/**
	 * Instantiates a new implement atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private ManageAtr(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the implement atr
	 */
	public static ManageAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (ManageAtr val : ManageAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}


}
