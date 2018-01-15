/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.dom.schedulemanagementcontrol;

/**
 * The Enum UseAtr.
 */
// するしない区分
public enum UseAtr {	
	/**
	 * Notuse.
	 */
	// しない
	NOTUSE(0, "Enum_UseAtr_notuse", "しない"),

	/** The pattern schedule. */
	// する 
	USE(1, "Enum_UseAtr_use", "する");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static UseAtr[] values = UseAtr.values();

	/**
	 * Instantiates a new implement atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private UseAtr(int value, String nameId, String description) {
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
	public static UseAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (UseAtr val : UseAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}


}
