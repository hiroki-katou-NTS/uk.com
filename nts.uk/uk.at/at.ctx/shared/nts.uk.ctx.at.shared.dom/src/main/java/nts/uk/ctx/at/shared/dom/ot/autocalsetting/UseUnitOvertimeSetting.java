/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.ot.autocalsetting;

/**
 * The Enum UseUnitOvertimeSetting.
 */
public enum UseUnitOvertimeSetting {

	/** The autocaljob. */
	AUTOCALJOB(0, "職位の自動計算設定をする", "Enum_UseUnitOvertimeSetting_AutoCalJob"),

	/** The autocalwkpjob. */
	AUTOCALWKPJOB(1, "職場・職位の自動計算設定を行う", "Enum_UseUnitOvertimeSetting_AutoCalWkpJob"),
	
	/** The autocalworkplace. */
	AUTOCALWORKPLACE(2, "職場の自動計算設定をする", "Enum_UseUnitOvertimeSetting_AutoCalWkp");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The description. */
	public final String description;

	/** The Constant values. */
	private final static UseUnitOvertimeSetting[] values = UseUnitOvertimeSetting.values();

	/**
	 * Instantiates a new use unit overtime setting.
	 *
	 * @param value the value
	 * @param nameId the name id
	 * @param description the description
	 */
	private UseUnitOvertimeSetting(int value, String nameId, String description) {
		this.value = value;
		this.nameId = nameId;
		this.description = description;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the use unit overtime setting
	 */
	public static UseUnitOvertimeSetting valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (UseUnitOvertimeSetting val : UseUnitOvertimeSetting.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
