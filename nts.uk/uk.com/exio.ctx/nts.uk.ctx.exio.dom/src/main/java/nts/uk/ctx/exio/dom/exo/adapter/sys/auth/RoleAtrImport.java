/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.exio.dom.exo.adapter.sys.auth;

/**
 * The Enum RoleAtr.
 */
public enum RoleAtrImport {
	
	/** The incharge. */
	// 担当
	INCHARGE(0, "Enum_RoleAtr_inCharge", "担当"),

	/** The general. */
	// 一般
	GENERAL(1, "Enum_RoleAtr_General", "一般");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static RoleAtrImport[] values = RoleAtrImport.values();

	/**
	 * Instantiates a new role atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
		private RoleAtrImport(int value, String nameId, String description) {
			this.value = value;
			this.nameId = nameId;
			this.description = description;
		}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the role atr
	 */
	public static RoleAtrImport valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (RoleAtrImport val : RoleAtrImport.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
